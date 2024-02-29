package view

import service.RootService
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.visual.ImageVisual

import service.CardImageLoader
import entity.*
import tools.aqua.bgw.components.container.CardStack
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.util.Font
import java.awt.Color

/**
 * This is the main thing for the ShiftPoker game..
 */

class GameScene(private val rootService: RootService) :
    BoardGameScene(1200, 800, background = ImageVisual("Screenshot 2024-02-24 130236.png")), Refreshable {

    private var deckIndex = 0
    private var revealedIndex = 0
    private val cardImageLoader = CardImageLoader()
    private var chosenCardDeck = false
    private var chosenCardActive = false
    private var onlyOneClickedShiftDeck = false
    private var onlyOneClickedActivePlayer = false
    private var isSwapped = false
    private var isClickedSdCard1 = false
    private var isClickedSdCard2 = false
    private var isClickedSdCard3 = false
    private var isClickedActivePlayerCard1 = false
    private var isClickedActivePlayerCard2 = false
    private var isClickedActivePlayerCard3 = false

    val game = rootService.game
    private var shiftButton: Button = Button(
        posY = 630,
        posX = 1050,
        width = 150,
        height = 25,
        text = "Shift Cards"
    ).apply {
        onMouseClicked = {
            this.isDisabled = true
            shiftLeftButton.isDisabled = false
            shiftRightButton.isDisabled = false
        }
    }

    private var shiftLeftButton = Label(
        posY = 350,
        posX = 350,
        width = 100,
        height = 90,
        visual = ImageVisual("arrow_left.png")
    ).apply {
        this.isDisabled = true
        onMouseClicked = {
            this.isDisabled = true
            shiftRightButton.isDisabled = true
            swapOneButton.isDisabled = false
            swapAllButton.isDisabled = false
            passButton.isDisabled = false
            rootService.playerService.shiftCards(-1)
            enableCards()
        }
    }

    private var shiftRightButton: Label = Label(
        posY = 350,
        posX = 770,
        width = 120,
        height = 80,
        visual = ImageVisual("arrow_right.png")
    ).apply {
        this.isDisabled = true
        onMouseClicked = {
            this.isDisabled = true
            shiftLeftButton.isDisabled = true
            swapOneButton.isDisabled = false
            swapAllButton.isDisabled = false
            passButton.isDisabled = false
            rootService.playerService.shiftCards(1)
            enableCards()
        }
    }

    private var swapOneButton: Button = Button(
        posY = 660,
        posX = 1050,
        width = 150,
        height = 25,
        text = "Swap One"
    ).apply {
        isDisabled = true
        onMouseClicked = {
            if (chosenCardDeck && chosenCardActive) {
                rootService.playerService.swapOne(deckIndex, revealedIndex)
                disableCards()
                this.isDisabled = true
                swapAllButton.isDisabled = true
                isSwapped = true
            }
        }
    }

    private var swapAllButton: Button = Button(
        posY = 690,
        posX = 1050,
        width = 150,
        height = 25,
        text = "Swap All"
    ).apply {
        isDisabled = true
        onMouseClicked = {
            if (!chosenCardDeck && !chosenCardActive) {
                rootService.playerService.swapAll()
                disableCards()
                swapOneButton.isDisabled = true
                this.isDisabled = true
                isSwapped = true
            }
        }
    }

    private var passButton: Button = Button(
        posY = 720,
        posX = 1050,
        width = 150,
        height = 25,
        text = "Pass"
    ).apply {
        isDisabled = true
        onMouseClicked = {
            disableCards()
            shiftButton.isDisabled = false // for the next player
            swapOneButton.isDisabled = true
            swapAllButton.isDisabled = true
            rootService.shiftPokerGameService.nextPlayer()
            this.isDisabled = true
            isSwapped = false
            isSwapped = false
        }
    }

    private var quitButton = Button(
        posY = 750,
        posX = 1050,
        width = 150,
        height = 25,
        text = "End Game"
    ).apply {
        isDisabled = false
        onMouseClicked = {
            shiftButton.isDisabled = false
            swapAllButton.isDisabled = true
            swapOneButton.isDisabled = true
            passButton.isDisabled = true
            rootService.shiftPokerGameService.endGame()
        }
    }

    private val sDCard1 = CardView(
        posX = 410,
        posY = 270,
        front = ImageVisual(cardImageLoader.backImage)
    ).apply {
        this.scale(0.6)
        this.showFront()
        this.onMouseClicked = {
            if (isClickedSdCard1) {
                this.scale(0.6)
                chosenCardDeck = false
                isClickedSdCard1 = false
            } else {
                deckIndex = 0
                this.scale(0.7)
                sDCard2.apply { scale(0.6) }
                sDCard3.apply { scale(0.6) }
                chosenCardDeck = true
                isClickedSdCard1 = true
                isClickedSdCard2 = false
                isClickedSdCard3 = false
            }
        }
    }

    private val sDCard2: CardView = CardView(
        posX = 540,
        posY = 270,
        front = ImageVisual(cardImageLoader.frontImageFor(CardColour.DIAMONDS, CardValue.ACE))
    ).apply {
        this.scale(0.6)
        this.showFront()
        onMouseClicked =
            {
                if (isClickedSdCard2) {
                    this.scale(0.6)
                    chosenCardDeck = false
                    isClickedSdCard2 = false
                } else {
                    this.scale(0.7)
                    deckIndex = 1
                    sDCard1.apply { scale(0.6) }
                    sDCard3.apply { scale(0.6) }
                    chosenCardDeck = true
                    isClickedSdCard2 = true
                    isClickedSdCard1 = false
                    isClickedSdCard3 = false
                }
            }
    }

    private val sDCard3: CardView = CardView(
        posX = 660,
        posY = 270,
        front = ImageVisual(cardImageLoader.backImage)
    ).apply {
        this.showFront()
        this.scale(0.6)
        onMouseClicked =
            {
                if (isClickedSdCard3) {
                    this.scale(0.6)
                    chosenCardDeck = false
                    isClickedSdCard3 = false
                } else {
                    this.scale(0.7)
                    deckIndex = 2
                    sDCard1.apply { scale(0.6) }
                    sDCard2.apply { scale(0.6) }
                    chosenCardDeck = true
                    isClickedSdCard3 = true
                    isClickedSdCard1 = false
                    isClickedSdCard2 = false
                }
            }
    }

    private val card4 = CardView(
        posX = 650,
        posY = 250,
        front = ImageVisual(cardImageLoader.backImage)
    ).apply {
        this.scale(0.6)
        this.showBack()
    }

    private val roundCountLabel: Label = Label(
        height = 200,
        width = 300,
        posX = -10,
        posY = -25,
        text = "test:",
        font = Font(size = 25, color = Color.orange, fontWeight = Font.FontWeight.BOLD)
    )

    private val leftPlayerLabel: Label = Label(
        height = 200,
        width = 300,
        posX = 0,
        posY = 120,
        text = "Active Player:",
        font = Font(size = 25, color = Color.PINK, fontWeight = Font.FontWeight.BOLD)
    )

    private val leftPlayerCard1 = CardView(
        posX = 120,
        posY = 185,
        front = ImageVisual(cardImageLoader.frontImageFor(CardColour.HEARTS, CardValue.KING))
    ).apply {
        this.showFront()
        this.scale(0.55)
        this.rotation = 90.0
    }

    private val leftPlayerCard2 = CardView(
        posX = 120,
        posY = 275,
        front = ImageVisual(cardImageLoader.frontImageFor(CardColour.HEARTS, CardValue.KING))
    ).apply {
        this.showFront()
        this.scale(0.55)
        this.rotation = 90.0
    }

    private val leftPlayerCard3 = CardView(
        posX = 120,
        posY = 365,
        front = ImageVisual(cardImageLoader.frontImageFor(CardColour.HEARTS, CardValue.KING))
    ).apply {
        this.showFront()
        this.scale(0.55)
        this.rotation = 90.0
    }

    private val rightPlayerLabel: Label = Label(
        height = 200,
        width = 300,
        posX = 920,
        posY = 120,
        text = "Active Player:",
        font = Font(size = 25, color = Color.pink, fontWeight = Font.FontWeight.BOLD)
    )

    private val rightPlayerCard1 = CardView(
        posX = 950,
        posY = 185,
        front = ImageVisual(cardImageLoader.frontImageFor(CardColour.HEARTS, CardValue.KING))
    ).apply {
        this.showFront()
        this.scale(0.55)
        this.rotation = 90.0
    }

    private val rightPlayerCard2 = CardView(
        posX = 950,
        posY = 275,
        front = ImageVisual(cardImageLoader.frontImageFor(CardColour.HEARTS, CardValue.KING))
    ).apply {
        this.showFront()
        this.scale(0.55)
        this.rotation = 90.0
    }

    private val rightPlayerCard3 = CardView(
        posX = 950,
        posY = 365,
        front = ImageVisual(cardImageLoader.frontImageFor(CardColour.HEARTS, CardValue.KING))
    ).apply {
        this.showFront()
        this.scale(0.55)
        this.rotation = 90.0
    }

    private val topPlayerLabel: Label = Label(
        height = 200,
        width = 300,
        posX = 280,
        posY = -20,
        text = "Active Player:",
        font = Font(size = 25, color = Color.pink, fontWeight = Font.FontWeight.BOLD)
    )

    private val topPlayerCard3 = CardView(
        posX = 450,
        posY = 70,
        front = ImageVisual(cardImageLoader.frontImageFor(CardColour.HEARTS, CardValue.KING))
    ).apply {
        this.showFront()
        this.scale(0.55)

    }

    private val topPlayerCard2 = CardView(
        posX = 540,
        posY = 70,
        front = ImageVisual(cardImageLoader.frontImageFor(CardColour.HEARTS, CardValue.KING))
    ).apply {
        this.showFront()
        this.scale(0.55)
    }

    private val topPlayerCard1 = CardView(
        posX = 630,
        posY = 70,
        front = ImageVisual(cardImageLoader.frontImageFor(CardColour.HEARTS, CardValue.KING))

    ).apply {
        this.scale(0.55)
        this.showFront()
    }

    private val activePlayerLabel = Label(
        height = 200,
        width = 200,
        posX = 250,
        posY = 600,
        text = "Active Player:",
        font = Font(size = 25, color = Color.pink, fontWeight = Font.FontWeight.BOLD)

    )

    private val activePlayerNameLabel = Label(
        height = 200,
        width = 200,
        posX = 250,
        posY = 650,
        text = "Active Player",
        font = Font(size = 25, color = Color.pink, fontWeight = Font.FontWeight.BOLD)
    )

    private val activePlayerCard1 = CardView(
        posX = 450,
        posY = 465,
        front = ImageVisual(cardImageLoader.backImage)
    ).apply {
        this.scale(0.55)
        this.showFront()
        onMouseClicked = {
            if (isClickedActivePlayerCard1) {
                this.scale(0.55)
                chosenCardActive = false
                isClickedActivePlayerCard1 = false
            } else {
                revealedIndex = 0
                this.scale(0.65)
                activePlayerCard2.apply { scale(0.55) }
                activePlayerCard3.apply { scale(0.55) }
                chosenCardActive = true
                isClickedActivePlayerCard1 = true
                isClickedActivePlayerCard2 = false
                isClickedActivePlayerCard3 = false
            }
        }
    }

    private val activePlayerCard2: CardView = CardView(
        posX = 540,
        posY = 465,
        front = ImageVisual(cardImageLoader.frontImageFor(CardColour.HEARTS, CardValue.KING))
    ).apply {
        this.scale(0.55)
        this.showFront()
        onMouseClicked = {
            if (isClickedActivePlayerCard2) {
                this.scale(0.55)
                chosenCardActive = false
                isClickedActivePlayerCard2 = false
            } else {
                revealedIndex = 1
                this.scale(0.65)
                activePlayerCard1.apply { scale(0.55) }
                activePlayerCard3.apply { scale(0.55) }
                chosenCardActive = true
                isClickedActivePlayerCard2 = true
                isClickedActivePlayerCard1 = false
                isClickedActivePlayerCard3 = false
            }
        }
    }

    private val activePlayerCard3: CardView = CardView(
        posX = 630,
        posY = 465,
        front = ImageVisual(cardImageLoader.frontImageFor(CardColour.HEARTS, CardValue.KING))
    ).apply {
        this.scale(0.55)
        this.showFront()
        onMouseClicked = {
            if (isClickedActivePlayerCard3) {
                this.scale(0.55)
                chosenCardActive = false
                isClickedActivePlayerCard3 = false
            } else {
                revealedIndex = 2
                this.scale(0.65)
                activePlayerCard1.apply { scale(0.55) }
                activePlayerCard2.apply { scale(0.55) }
                chosenCardActive = true
                isClickedActivePlayerCard3 = true
                isClickedActivePlayerCard1 = false
                isClickedActivePlayerCard2 = false
            }
        }
    }

    private val privateActivePlayerCard1 = CardView(
        posX = 490,
        posY = 615,
        front = ImageVisual(cardImageLoader.backImage)
    ).apply {
        this.scale(0.55)
        this.showBack()
        onMousePressed = {
            this.showFront()
        }
        onMouseReleased = {
            this.showBack()
        }
    }

    private val privateActivePlayerCard2 = CardView(
        posX = 590,
        posY = 615,
        front = ImageVisual(cardImageLoader.backImage)
    ).apply {
        this.scale(0.55)
        this.showBack()
        onMousePressed = {
            this.showFront()
        }
        onMouseReleased = {
            this.showBack()
        }
    }

    private val drawStack: CardStack<CardView> = CardStack<CardView>(
        height = 200,
        width = 130,
        posX = 800,
        posY = 70,
    ).apply {

        this.push(card4)
    }

    private val linearLayoutTopPlayer = LinearLayout<CardView>(
        posX = 500,
        posY = -50,
        spacing = 0,
        width = 200,
        height = 200
    )

    private val linearLayoutLeftPlayer = LinearLayout<CardView>(
        posX = -80,
        posY = 250,
        spacing = 0,
        width = 200,
        height = 250,
        //visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        this.rotation = 90.0
    }

    private val linearLayoutRightPlayer = LinearLayout<CardView>(
        posX = 1030,
        posY = 250,
        spacing = 0,
        width = 200,
        height = 250
    ).apply { this.rotation = 90.0 }

    private val dicardPileCard = CardView(
        height = 200,
        width = 130,
        posX = 270,
        posY = 465,
        front = ImageVisual(cardImageLoader.blankImage)
    ).apply {
        this.scale(0.5)
        this.showFront()
    }

    private fun refresh() {
        val game = rootService.game
        checkNotNull(game) { "No game is currently running" }
        chosenCardDeck = false
        chosenCardActive = false
        isClickedSdCard1 = false
        isClickedSdCard2 = false
        isClickedSdCard3 = false
        onlyOneClickedShiftDeck = false
        isClickedActivePlayerCard1 = false
        isClickedActivePlayerCard2 = false
        isClickedActivePlayerCard3 = false
        onlyOneClickedActivePlayer = false

        activePlayerNameLabel.apply {
            text = game.playerList[game.activePlayer].name
        }

        roundCountLabel.apply {
            text = "Remaining rounds: ${game.roundCount}"
        }

        sDCard1.apply {
            this.scale(0.6)
            frontVisual = ImageVisual(
                cardImageLoader.frontImageFor(
                    game.shiftDeck[0].cardColour,
                    game.shiftDeck[0].value
                )
            )
            this.showFront()
        }

        sDCard2.apply {
            this.scale(0.6)
            frontVisual = ImageVisual(
                cardImageLoader.frontImageFor(
                    game.shiftDeck[1].cardColour,
                    game.shiftDeck[1].value
                )
            )
            this.showFront()
        }

        sDCard3.apply {
            this.scale(0.6)
            frontVisual = ImageVisual(
                cardImageLoader.frontImageFor(
                    game.shiftDeck[2].cardColour,
                    game.shiftDeck[2].value
                )
            )
            this.showFront()
        }

        // initialize the Cards of the active Player
        activePlayerCard1.apply {
            this.scale(0.55)
            frontVisual = ImageVisual(
                cardImageLoader.frontImageFor(
                    game.playerList[game.activePlayer].revealedCards[0].cardColour,
                    game.playerList[game.activePlayer].revealedCards[0].value
                )
            )
            this.showFront()
        }

        activePlayerCard2.apply {
            this.scale(0.55)

            frontVisual = ImageVisual(
                cardImageLoader.frontImageFor(
                    game.playerList[game.activePlayer].revealedCards[1].cardColour,
                    game.playerList[game.activePlayer].revealedCards[1].value
                )
            )
            this.showFront()
        }

        activePlayerCard3.apply {
            this.scale(0.55)
            frontVisual = ImageVisual(
                cardImageLoader.frontImageFor(
                    game.playerList[game.activePlayer].revealedCards[2].cardColour,
                    game.playerList[game.activePlayer].revealedCards[2].value
                )
            )
            this.showFront()
        }

        privateActivePlayerCard1.apply {
            frontVisual = ImageVisual(
                cardImageLoader.frontImageFor(
                    game.playerList[game.activePlayer].hiddenCards[0].cardColour,
                    game.playerList[game.activePlayer].hiddenCards[0].value
                )
            )
        }

        privateActivePlayerCard2.apply {
            frontVisual = ImageVisual(
                cardImageLoader.frontImageFor(
                    game.playerList[game.activePlayer].hiddenCards[1].cardColour,
                    game.playerList[game.activePlayer].hiddenCards[1].value
                )
            )
        }

        when (game.playerList.size) {
            2 -> {
                topPlayerCard1.apply {
                    frontVisual = ImageVisual(
                        cardImageLoader.frontImageFor(
                            game.playerList[game.activePlayer + 1].revealedCards[0].cardColour,
                            game.playerList[game.activePlayer + 1].revealedCards[0].value
                        )
                    )
                    this.showFront()
                }

                topPlayerCard2.apply {
                    frontVisual = ImageVisual(
                        cardImageLoader.frontImageFor(
                            game.playerList[game.activePlayer + 1].revealedCards[1].cardColour,
                            game.playerList[game.activePlayer + 1].revealedCards[1].value
                        )
                    )
                    this.showFront()
                }

                topPlayerCard3.apply {
                    frontVisual = ImageVisual(
                        cardImageLoader.frontImageFor(
                            game.playerList[game.activePlayer + 1].revealedCards[2].cardColour,
                            game.playerList[game.activePlayer + 1].revealedCards[2].value
                        )
                    )
                    this.showFront()
                }

                topPlayerLabel.apply {
                    text = game.playerList[game.activePlayer + 1].name
                }
            }

            3 -> {
                topPlayerCard1.apply {
                    frontVisual = ImageVisual(
                        cardImageLoader.frontImageFor(
                            game.playerList[game.activePlayer + 2].revealedCards[0].cardColour,
                            game.playerList[game.activePlayer + 2].revealedCards[0].value
                        )
                    )
                    this.showFront()
                }

                topPlayerCard2.apply {
                    frontVisual = ImageVisual(
                        cardImageLoader.frontImageFor(
                            game.playerList[game.activePlayer + 2].revealedCards[1].cardColour,
                            game.playerList[game.activePlayer + 2].revealedCards[1].value
                        )
                    )
                    this.showFront()
                }

                topPlayerCard3.apply {
                    frontVisual = ImageVisual(
                        cardImageLoader.frontImageFor(
                            game.playerList[game.activePlayer + 2].revealedCards[2].cardColour,
                            game.playerList[game.activePlayer + 2].revealedCards[2].value
                        )
                    )
                    this.showFront()
                }

                leftPlayerCard1.apply {
                    frontVisual = ImageVisual(
                        cardImageLoader.frontImageFor(
                            game.playerList[game.activePlayer + 1].revealedCards[0].cardColour,
                            game.playerList[game.activePlayer + 1].revealedCards[0].value
                        )
                    )
                    this.showFront()
                }

                leftPlayerCard2.apply {
                    frontVisual = ImageVisual(
                        cardImageLoader.frontImageFor(
                            game.playerList[game.activePlayer + 1].revealedCards[1].cardColour,
                            game.playerList[game.activePlayer + 1].revealedCards[1].value
                        )
                    )
                    this.showFront()
                }

                leftPlayerCard3.apply {
                    frontVisual = ImageVisual(
                        cardImageLoader.frontImageFor(
                            game.playerList[game.activePlayer + 1].revealedCards[2].cardColour,
                            game.playerList[game.activePlayer + 1].revealedCards[2].value
                        )
                    )
                    this.showFront()
                }

                topPlayerLabel.apply {
                    text = game.playerList[game.activePlayer + 2].name
                }
                leftPlayerLabel.apply {
                    text = game.playerList[game.activePlayer + 1].name
                }
            }

            4 -> {
                topPlayerCard1.apply {
                    frontVisual = ImageVisual(
                        cardImageLoader.frontImageFor(
                            game.playerList[game.activePlayer + 2].revealedCards[0].cardColour,
                            game.playerList[game.activePlayer + 2].revealedCards[0].value
                        )
                    )
                    this.showFront()
                }

                topPlayerCard2.apply {
                    frontVisual = ImageVisual(
                        cardImageLoader.frontImageFor(
                            game.playerList[game.activePlayer + 2].revealedCards[1].cardColour,
                            game.playerList[game.activePlayer + 2].revealedCards[1].value
                        )
                    )
                    this.showFront()
                }

                topPlayerCard3.apply {
                    frontVisual = ImageVisual(
                        cardImageLoader.frontImageFor(
                            game.playerList[game.activePlayer + 2].revealedCards[2].cardColour,
                            game.playerList[game.activePlayer + 2].revealedCards[2].value
                        )
                    )
                    this.showFront()
                }

                leftPlayerCard1.apply {
                    frontVisual = ImageVisual(
                        cardImageLoader.frontImageFor(
                            game.playerList[game.activePlayer + 1].revealedCards[0].cardColour,
                            game.playerList[game.activePlayer + 1].revealedCards[0].value
                        )
                    )
                    this.showFront()
                }

                leftPlayerCard2.apply {
                    frontVisual = ImageVisual(
                        cardImageLoader.frontImageFor(
                            game.playerList[game.activePlayer + 1].revealedCards[1].cardColour,
                            game.playerList[game.activePlayer + 1].revealedCards[1].value
                        )
                    )
                    this.showFront()
                }

                leftPlayerCard3.apply {
                    frontVisual = ImageVisual(
                        cardImageLoader.frontImageFor(
                            game.playerList[game.activePlayer + 1].revealedCards[2].cardColour,
                            game.playerList[game.activePlayer + 1].revealedCards[2].value
                        )
                    )
                    this.showFront()
                }

                rightPlayerCard1.apply {
                    frontVisual = ImageVisual(
                        cardImageLoader.frontImageFor(
                            game.playerList[game.activePlayer + 3].revealedCards[2].cardColour,
                            game.playerList[game.activePlayer + 3].revealedCards[2].value
                        )
                    )
                    this.showFront()
                }

                rightPlayerCard2.apply {
                    frontVisual = ImageVisual(
                        cardImageLoader.frontImageFor(
                            game.playerList[game.activePlayer + 3].revealedCards[1].cardColour,
                            game.playerList[game.activePlayer + 3].revealedCards[1].value
                        )
                    )
                    this.showFront()
                }

                rightPlayerCard3.apply {
                    frontVisual = ImageVisual(
                        cardImageLoader.frontImageFor(
                            game.playerList[game.activePlayer + 3].revealedCards[0].cardColour,
                            game.playerList[game.activePlayer + 3].revealedCards[0].value
                        )
                    )
                    this.showFront()
                }

                topPlayerLabel.apply {
                    text = game.playerList[game.activePlayer + 2].name
                }

                leftPlayerLabel.apply {
                    text = game.playerList[game.activePlayer + 1].name
                }

                rightPlayerLabel.apply {
                    text = game.playerList[game.activePlayer + 3].name
                }
            }
        }


    }

    override fun refreshAfterStartGame() {
        val game = rootService.game
        checkNotNull(game) { "No game is running" }
        refresh()
        disableCards()

        when (game.playerList.size) {
            3 -> {
                removeComponents(
                    leftPlayerCard1, leftPlayerCard2, leftPlayerCard3, linearLayoutLeftPlayer,
                    rightPlayerCard1, rightPlayerCard2, rightPlayerCard3, linearLayoutRightPlayer,
                    activePlayerLabel, leftPlayerLabel, rightPlayerLabel, topPlayerLabel, activePlayerNameLabel
                )
                addComponents(
                    leftPlayerCard1,
                    leftPlayerCard2,
                    leftPlayerCard3,
                    linearLayoutLeftPlayer, activePlayerLabel, leftPlayerLabel, topPlayerLabel, activePlayerNameLabel
                )
            }

            4 -> {

                removeComponents(
                    leftPlayerCard1, leftPlayerCard2, leftPlayerCard3, linearLayoutLeftPlayer,
                    rightPlayerCard1, rightPlayerCard2, rightPlayerCard3, linearLayoutRightPlayer,
                    activePlayerLabel, leftPlayerLabel, rightPlayerLabel, topPlayerLabel, activePlayerNameLabel
                )
                addComponents(
                    rightPlayerCard1,
                    rightPlayerCard2,
                    rightPlayerCard3,
                    linearLayoutRightPlayer,
                    leftPlayerCard1,
                    leftPlayerCard2,
                    leftPlayerCard3,
                    linearLayoutLeftPlayer,
                    activePlayerLabel,
                    leftPlayerLabel,
                    rightPlayerLabel,
                    topPlayerLabel,
                    activePlayerNameLabel
                )
            }

            else -> {
                removeComponents(
                    topPlayerCard1, topPlayerCard2,
                    topPlayerCard3, activePlayerLabel,
                    leftPlayerLabel, rightPlayerLabel,
                    topPlayerLabel,  activePlayerCard1,
                    activePlayerCard2,  activePlayerCard3,
                    privateActivePlayerCard1, privateActivePlayerCard2,
                    linearLayoutTopPlayer, leftPlayerCard1,
                    leftPlayerCard2, leftPlayerCard3,
                    linearLayoutLeftPlayer, rightPlayerCard1,
                    rightPlayerCard2, rightPlayerCard3,
                    linearLayoutRightPlayer, activePlayerNameLabel
                )
                addComponents(
                    topPlayerCard1,
                    topPlayerCard2,
                    topPlayerCard3, activePlayerLabel, topPlayerLabel,
                    activePlayerCard1,
                    activePlayerCard2,
                    activePlayerCard3,
                    privateActivePlayerCard1,
                    privateActivePlayerCard2,
                    linearLayoutTopPlayer, activePlayerNameLabel
                )
            }
        }
    }

    private fun disableCards() {
        sDCard1.isDisabled = true
        sDCard2.isDisabled = true
        sDCard3.isDisabled = true
        activePlayerCard1.isDisabled = true
        activePlayerCard2.isDisabled = true
        activePlayerCard3.isDisabled = true
    }

    private fun enableCards() {
        sDCard1.isDisabled = false
        sDCard2.isDisabled = false
        sDCard3.isDisabled = false
        activePlayerCard1.isDisabled = false
        activePlayerCard2.isDisabled = false
        activePlayerCard3.isDisabled = false
    }

    override fun refreshAfterSwap(swapAll: Boolean) {
        //refreshActivePlayerUndShiftDeck
        val game = rootService.game
        checkNotNull(game) { "No game is currently running" }
        refresh()
    }

    override fun refreshAfterShift(direction: Int) {
        val game = rootService.game
        checkNotNull(game) { "No game is currently running" }
        dicardPileCard.apply {
            frontVisual = ImageVisual(
                cardImageLoader.frontImageFor(
                    game.discardPile[game.discardPile.size - 1].cardColour,
                    game.discardPile[game.discardPile.size - 1].value
                )
            )
            this.showFront()
        }
        refresh()
    }

    override fun refreshAfterNextPlayer() {
        refresh()
    }

    init {

        linearLayoutRightPlayer.addAll(
            CardView(front = ImageVisual(cardImageLoader.backImage)).apply { scale(0.4) },
            CardView(front = ImageVisual(cardImageLoader.backImage)).apply { scale(0.4) }
        )
        linearLayoutLeftPlayer.addAll(
            CardView(front = ImageVisual(cardImageLoader.backImage)).apply { scale(0.4) },
            CardView(front = ImageVisual(cardImageLoader.backImage)).apply { scale(0.4) }
        )
        linearLayoutTopPlayer.addAll(
            CardView(front = ImageVisual(cardImageLoader.backImage)).apply { scale(0.4) },
            CardView(front = ImageVisual(cardImageLoader.backImage)).apply { scale(0.4) }
        )

        addComponents(
            roundCountLabel,
            shiftButton,
            shiftLeftButton,
            shiftRightButton,
            swapOneButton,
            swapAllButton,
            passButton,
            quitButton,
            activePlayerLabel,
            topPlayerCard1,
            topPlayerCard2,
            topPlayerCard3,
            activePlayerCard1,
            activePlayerCard2,
            activePlayerCard3,
            privateActivePlayerCard1,
            privateActivePlayerCard2,
            linearLayoutTopPlayer,
            sDCard2, sDCard1, sDCard3,
            drawStack, dicardPileCard
        )
    }
}