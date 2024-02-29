package view

import entity.Player
import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

/**
 * [MenuScene] that is displayed when the game is finished. It shows the score of the players.
 * Also there are two buttons: one for starting a new game and one for
 * quitting the program.
 */

class EndScene(private val rootService: RootService) :
    MenuScene(width = 1200, height = 800, background = ColorVisual(Color.WHITE)), Refreshable {
    private val gameOverLabel = Label(
        posY = 50,
        posX = 50,
        width = 800,
        height = 100,
        text = "Game Over, hope you enjoyed it!",
        font = Font(size = 50, color = Color.BLACK)
    )
    private val winner1Label = Label(
        posY = 150,
        posX = 0,
        width = 1200,
        height = 100,
        text = "Test",
        font = Font(size = 30, color = Color.DARK_GRAY)
    )
    private val winner2Label = Label(
        posY = 300,
        posX = 50,
        width = 1000,
        height = 100,
        text = "Test",
        font = Font(size = 25, color = Color.darkGray)
    )
    private val winner3Label = Label(
        posY = 450,
        posX = 50,
        width = 1000,
        height = 100,
        text = "Test",
        font = Font(size = 20, color = Color.darkGray)
    )
    private val winner4Label = Label(
        posY = 600,
        posX = 50,
        width = 1000,
        height = 100,
        text = "Test",
        font = Font(size = 20, color = Color.darkGray)
    )

    val replayButton = Button(
        width = 150,
        height = 50,
        posX = 1000,
        posY = 650,
        text = "replay",
        font = Font(size = 20, color = Color.DARK_GRAY),
        visual = ColorVisual(color = Color.green)
    )
    val goodbyeButton = Button(
        width = 150,
        height = 50,
        posX = 1000,
        posY = 730,
        text = "Goodbye",
        font = Font(size = 20, color = Color.DARK_GRAY),
        visual = ColorVisual(color = Color.green)
    )


    fun makeItEasy(ranking: MutableList<Pair<List<Player>, String>>): MutableList<Pair<Int, Pair<Player, String>>?> {

        val game = rootService.game
        checkNotNull(game) { "No game is currently running" }

        // Using flatMapIndexed to transform and flatten the ranking list
        val realRanking: MutableList<Pair<Int, Pair<Player, String>>?> = ranking.flatMapIndexed { index, pair ->
            pair.first.map { player ->
                Pair(index + 1, Pair(player, pair.second))
            }
        }.toMutableList()

        // Ensuring the size matches the game.playerList.size, filling with nulls if necessary
        while (realRanking.size < game.playerList.size) {
            realRanking.add(null)
        }

        return realRanking
    }

    /**
     * the players will be sorted based on their points
     * and based on the players number the labels will be shown
     */
    override fun refreshAfterEndGame(ranking: MutableList<Pair<List<Player>, String>>) {
        val game = rootService.game
        checkNotNull(game) { "No game is currently running" }
        val realRanking = makeItEasy(ranking)
        when (game.playerList.size) {
            3 -> {
                removeComponents(replayButton, goodbyeButton, winner4Label, winner3Label, winner2Label, winner1Label, gameOverLabel)
                winner1Label.text =
                    "${realRanking[0]!!.first} :  " + realRanking[0]!!.second.first + " : " + realRanking[0]!!.second.second
                winner2Label.text =
                    "${realRanking[1]!!.first} :  " + realRanking[1]!!.second.first + " : " + realRanking[1]!!.second.second
                winner3Label.text =
                    "${realRanking[2]!!.first} :  " + realRanking[2]!!.second.first + " : " + realRanking[2]!!.second.second
                addComponents(winner1Label, winner2Label, winner3Label, gameOverLabel, replayButton, goodbyeButton)
            }

            4 -> {
                removeComponents(replayButton,goodbyeButton, winner4Label, winner3Label, winner2Label, winner1Label, gameOverLabel)
                winner1Label.text =
                    "${realRanking[0]!!.first} :  " + realRanking[0]!!.second.first + " : " + realRanking[0]!!.second.second
                winner2Label.text =
                    "${realRanking[1]!!.first} :  " + realRanking[1]!!.second.first + " : " + realRanking[1]!!.second.second
                winner3Label.text =
                    "${realRanking[2]!!.first} :  " + realRanking[2]!!.second.first + " : " + realRanking[2]!!.second.second
                println(realRanking[3])
                winner4Label.text =
                    "${realRanking[3]!!.first} :  " + realRanking[3]!!.second.first + " : " + realRanking[3]!!.second.second

                addComponents(winner1Label, winner2Label, winner3Label, winner4Label, gameOverLabel, replayButton, goodbyeButton)
            }

            else -> {
                removeComponents(replayButton,goodbyeButton, winner4Label, winner3Label, winner2Label, winner1Label, gameOverLabel)
                winner1Label.text =
                    "${realRanking[0]!!.first} :  " + realRanking[0]!!.second.first + " : " + realRanking[0]!!.second.second
                winner2Label.text =
                    "${realRanking[1]!!.first} :  " + realRanking[1]!!.second.first + " : " + realRanking[1]!!.second.second
                addComponents(winner1Label, winner2Label, gameOverLabel, replayButton, goodbyeButton)
            }

        }

    }


}