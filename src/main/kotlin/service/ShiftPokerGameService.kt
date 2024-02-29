package service

import entity.*

/**
 * Service layer class that provides the logic for actions not directly
 * related to a single player.
 */
class ShiftPokerGameService(val rootService: RootService) : AbstractRefreshingService() {
    /**
     * Starts a new game (overwriting a currently active one, if it exists)
     *
     * @param roundCount which is the number of rounds in the game and must be between 2 and 7
     * @param listOfPlayers contains all the players, which will play in the current game and must
     * be between 2 and 4
     */
    fun startGame(roundCount: Int, listOfPlayers: MutableList<Player>) {
        require(roundCount in 2..7) {
            "The number of rounds must be between 2 and 7"
        }
        require(listOfPlayers.size in 2..4) {
            "The number of players must be between 2 and 4"
        }

        //listOfPlayers.shuffle()
        val game = ShiftPokerGame(roundCount, listOfPlayers)
        rootService.game = game
        dealCards(game)
        onAllRefreshables { refreshAfterStartGame() }
    }

    var counterOfRounds = 0
    /**
     * gives the turn to next player if the number of rounds is smaller or equal 7
     */
    fun nextPlayer() {
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        // hide the cards of the active player
        rootService.cardService.hide()

        // give the turn to the next
        if (game.roundCount > 0) {
            if (counterOfRounds <= game.playerList.size - 1) {
                game.playerList.add(game.playerList.removeAt(0))
                counterOfRounds ++
                rootService.playerService.shifted = false
                rootService.playerService.swapped = false
                if(counterOfRounds == game.playerList.size ){
                    game.roundCount --
                    if (game.roundCount == 0){
                        rootService.playerService.shifted = false
                        rootService.playerService.swapped = false
                        onAllRefreshables { refreshAfterEndGame(evaluateGame()) }
                    }
                }
            } else {
                //game.roundCount --
                counterOfRounds = 1
                game.playerList.add(game.playerList.removeAt(0))
                rootService.playerService.shifted = false
                rootService.playerService.swapped = false
            }
        }


        onAllRefreshables { refreshAfterNextPlayer() }
    }

    /**
     * ends the game by showing the rankings and setting the currentgame auf null
     */
    fun endGame() {
        val rankList = evaluateGame()
        rootService.playerService.shifted = false
        rootService.playerService.swapped = false
        onAllRefreshables { refreshAfterEndGame(rankList) }
        rootService.game = null
    }

    /**
     * evaluates the hands of the players and returns a list of a list of players, when 2 players or
     * more have the same power of hand.
     * @return results which will contain the final results of the game.
     */
    fun evaluateGame(): MutableList<Pair<List<Player>, String>> {
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        val playerHandRank: MutableList<Int> = mutableListOf()
        for (player in game.playerList) {
            playerHandRank.add(evaluateHand(player))
        }
        val mapPlayerToRank = mutableMapOf(game.playerList[0] to playerHandRank[0])
        for (i in 1 until playerHandRank.size) {
            mapPlayerToRank += Pair(game.playerList[i], playerHandRank[i])
        }
        val sorted = mapPlayerToRank.entries.sortedBy { it.value } // sort the map by the hand rank
        // group the map by the hand rank and then take the players. They will be sorted.
        val groupedPlayers = sorted.groupBy({ it.value }, { it.key })
        val mappedNumbersToRank = groupedPlayers.mapKeys { (key, _) -> mapKeyToRank(key) }
        val swappedMap = mappedNumbersToRank.entries.associate { (key, value) -> value to key }.toList()
        return swappedMap.toMutableList()
    }
    private fun mapKeyToRank(key: Int): String {
        return when (key) {
            1 -> "Royal Flush"
            2 -> "Straight Flush"
            3 -> "Four of a kind"
            4 -> "Full House"
            5 -> "Flush"
            6 -> "Straight"
            7 -> "Three of a Kind"
            8 -> "Two Pair"
            9 -> "One Pair"
            else -> "High Card"
        }
    }

    /**
     * evaluates the rank of the hand of a given Player
     * @param player
     */
    private fun evaluateHand(player: Player): Int{
        var hand = player.hiddenCards + player.revealedCards
        require(hand.size == 5) { "Hand must contain exactly 5 cards." }

        // Group the cards by their value
        val groups = hand.groupBy { it.value }.mapValues { it.value.size }

        // Check whether a hand is  four of a kind, full house, three of a kind, two pair or one pair
        val fourOfAKind = groups.any { it.value == 4 }
        val threeOfAKind = groups.any { it.value == 3 }
        val pairs = groups.count { it.value == 2 }

        // Sort the hand by the value of each card in descending order
        hand = hand.sortedByDescending { it.value.valToInt() }

        // Check for a flush, all card should have the same cardColour
        val isFlush = hand.map { it.cardColour }.distinct().size == 1

        // Check for consecutive values
        val isStraight = hand.windowed(2, 1).all { (a, b) ->
            (a.value.valToInt() - b.value.valToInt() == 1)
                    || (a.value == CardValue.ACE && hand.last().value == CardValue.TWO)
        }

        // Check for special case where Ace is used as a low card in a straight (A, 2, 3, 4, 5)
        val isSpecial = hand.map { it.value.valToInt() }.sorted() == listOf(2, 3, 4, 5, 14)

        // Evaluate the hand based on the checks
        return when {
            isFlush && isStraight && hand.first().value == CardValue.ACE -> 1 // Royal Flush
            isFlush && (isStraight || isSpecial) -> 2                         // Straight Flush
            fourOfAKind -> 3                                                  // Four of a kind
            threeOfAKind && pairs == 1 -> 4                                   // Full House
            isFlush -> 5                                                      // Flush
            isStraight || isSpecial -> 6                                      // Straight
            threeOfAKind -> 7                                                 // Three of a Kind
            pairs == 2 -> 8                                                   // Two Pair
            pairs == 1 -> 9                                                   // One Pair
            else -> 10                                                        // High Card
        }
    }

    /**
     * distributes the cards on the table of the current game
     * @param game
     */
    private fun dealCards(game: ShiftPokerGame?) {
        checkNotNull(game) { "No game currently running." }
        game.drawPile = rootService.cardService.createShuffleDeck()
        game.shiftDeck.generateShiftDeck()
        for (player in game.playerList) {
            player.hiddenCards.generateHiddenCards()
            player.revealedCards.generateRevealedCards()
        }
    }

    /**
     * distributes the top 3 cards of the drawPile to the shiftDeck.
     * after shuffeling the cards in startGame the top 3 Cards of drawPile will
     * be the 3 cards of the shiftDeck
     */
    private fun MutableList<Card>.generateShiftDeck() {
        repeat(3) {
            val newCard = drawCard()
            newCard.hidden = false
            this.add(newCard)
        }
    }

    /**
     * distributes the top 2 cards of the drawPile to a player, and they will be his hidden cards
     */
    private fun MutableList<Card>.generateHiddenCards() {
        repeat(2) {
            this.add(drawCard())
        }
    }

    /**
     * distributes the top 3 cards of the drawPile to a player, and they will be his revealed cards
     */
    private fun MutableList<Card>.generateRevealedCards() {
        repeat(3) {
            val newCard = drawCard()
            newCard.hidden = false
            this.add(newCard)
        }
    }

    /**
     * takes the top card from the draw pile, save it in drawnCard, and remove it from the draw Pile
     * @return drawnCard
     */
    fun drawCard(): Card {
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        val drawPile = game.drawPile
        val drawnCard = drawPile[0]
        drawPile.removeFirst()
        return drawnCard
    }
}