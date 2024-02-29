package view

import service.RootService
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color
import entity.*
import tools.aqua.bgw.components.uicomponents.ComboBox

/**
 * It is reached when "start game" is clicked in [StartMenuScene].
 * After providing the names of the players, the number of rounds
 * [letsGoButton] can be pressed. There is also a [returnButton] to come back to [StartMenuScene].
 */

class LobbyScene(private val rootService: RootService) :
    MenuScene(width = 300, height = 500, background = ColorVisual(Color.WHITE)), Refreshable {
    private val headlineLabel = Label(
        width = 300, height = 50, posX = 10, posY = 0,
        text = "New Game",
        font = Font(size = 22)
    )

    private val roundCount = Label(
        width = 100, height = 35,
        posX = 0, posY = 80,
        text = "Rounds: "
    )

    private val rounds: ComboBox<Int> =
        ComboBox(posX = 80, posY = 80, width = 150, height = 35,
            prompt = "2 rounds or more :)", items = mutableListOf(2, 3, 4, 5, 6, 7)).apply { selectedItem = 2  }

    private val p1Label = Label(
        width = 100, height = 35,
        posX = 0, posY = 125,
        text = "Player 1:"
    )

    private val p1Input: TextField = TextField(
        width = 200, height = 35,
        posX = 80, posY = 125,
        text = "Messi"
    ).apply {
        onKeyTyped = {
            letsGoButton.isDisabled = this.text.isBlank() || p2Input.text.isBlank() ||
                    this.text.length > 10 || p2Input .text.length > 10
        }
    }

    private val p2Label = Label(
        width = 100, height = 35,
        posX = 0, posY = 170,
        text = "Player 2:"
    )

    private val p2Input: TextField = TextField(
        width = 200, height = 35,
        posX = 80, posY = 170,
        text = "Pique"
    ).apply {
        onKeyTyped = {
            letsGoButton.isDisabled = p1Input.text.isBlank() || this.text.isBlank() ||
                    this.text.length > 10 || p1Input .text.length > 10
                    || this.text.uppercase() == p1Input.text.uppercase()
        }
    }

    private val p3Label = Label(
        width = 100, height = 35,
        posX = 0, posY = 215,
        text = "Player 3:"
    )

    private val p3Input: TextField = TextField(
        width = 200, height = 35,
        posX = 80, posY = 215,
        text = "Reus"
    ).apply {
        onKeyTyped = {
            letsGoButton.isDisabled = this.text.isBlank() || p2Input.text.isBlank() || !components.contains(this)
                    || this.text.length > 10 || this.text.uppercase() == p2Input.text.uppercase()
                    || this.text.uppercase() == p1Input.text.uppercase()
        }
    }

    private val p4Label = Label(
        width = 100, height = 35,
        posX = 0, posY = 260,
        text = "Player 4:"
    )

    private val p4Input: TextField = TextField(
        width = 200, height = 35,
        posX = 80, posY = 260,
        text = "Lewa"
    ).apply {
        onKeyTyped = {
            letsGoButton.isDisabled = this.text.isBlank() || p2Input.text.isBlank() || !components.contains(this)
                    || this.text.length > 10 || this.text.uppercase() == p3Input.text.uppercase()
                    || this.text.uppercase() == p2Input.text.uppercase()
                    || this.text.uppercase() == p1Input.text.uppercase()
        }
    }

    val returnButton = Button(
        width = 140, height = 35,
        posX = 7, posY = 400,
        text = "Return"
    ).apply {
        visual = ColorVisual(221, 136, 136)
    }

    private val letsGoButton = Button(
        width = 140, height = 35,
        posX = 154, posY = 400,
        text = "Let´s go"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
                rootService.shiftPokerGameService.counterOfRounds = 0
                val player1 = Player(p1Input.text)
                val player2 = Player(p2Input.text)
                val playerList = mutableListOf(player1, player2)
                if (components.contains(p3Input)) {
                    val player3 = Player(p3Input.text)
                    playerList.add(player3)
                }
                if (components.contains(p4Input)) {
                    val player4 = Player(p4Input.text)
                    playerList.add(player4)
                    print(1)
                }
                if (rounds.selectedItemProperty.equals(other = null)) {
                    rootService.shiftPokerGameService.startGame(2, playerList)
                } else
                    rootService.shiftPokerGameService.startGame(rounds.selectedItem!!.toInt(), playerList)
        }
    }

    private val addPlayer: Button = Button(
        width = 287, height = 25,
        posX = 7, posY = 310,
        text = "add Player"
    ).apply {
        visual = ColorVisual(150, 150, 150)
        onMouseClicked = {
            if (!components.contains(p3Label)) {
                addComponents(
                    p3Input.apply { text = "Lewa" },
                    p3Label
                )
                removePlayer.isDisabled = false
            } else {
                addComponents(
                    p4Input.apply { text = "Reus" },
                    p4Label
                )
                this.isDisabled = true
            }
        }
    }

    private val removePlayer: Button = Button(
        width = 287, height = 25,
        posX = 7, posY = 350,
        text = "remove Player"
    ).apply {
        visual = ColorVisual(150, 150, 150)
        isDisabled = true
        onMouseClicked = {
            if (components.contains(p4Label)) {
                removeComponents(
                    p4Input,
                    p4Label
                )
                addPlayer.isDisabled = false
                if (p3Input.text.isNotEmpty()){
                    letsGoButton.isDisabled = false
                }
            } else {
                removeComponents(
                    p3Input,
                    p3Label
                )
                letsGoButton.isDisabled = false
                this.isDisabled = true
            }
        }
    }

    init {
        opacity = .5
        addComponents(
            headlineLabel,
            roundCount,
            p1Label, p1Input,
            p2Label, p2Input,
            addPlayer, removePlayer,
            letsGoButton, returnButton, rounds
        )
    }
}

