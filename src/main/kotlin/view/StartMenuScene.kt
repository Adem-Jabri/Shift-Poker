package view

import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.*
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

/**
 * It is displayed directly at program start or reached.
 * when "return" is clicked in [LobbyScene] or when "replay" is clicked in [EndScene].
 * [startButton] can be pressed to go to the [LobbyScene]. There is also a [exitButton] to end the program.
 */

class StartMenuScene(private val rootService: RootService, private val shiftPokerApplication: ShiftPokerApplication) :
    MenuScene(width = 300, height = 500, background = ColorVisual(Color.WHITE)), Refreshable {
//    private val continueGameButton: Button = Button(
//        height = 80,
//        width = 200,
//        posX = 50,
//        posY = 110,
//        text = "Continue",
//        font = Font(color = Color.WHITE, fontStyle = Font.FontStyle.ITALIC),
//        visual = ColorVisual(136, 221, 136)
//    )
    //private val shiftPokerApplication = ShiftPokerApplication()
    private val startButton = Button(
        width = 200, height = 80,
        posX = 50, posY = 180,
        text = "Start Game"
    ).apply {
        //this.isDisabled
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
            shiftPokerApplication.refreshAfterMenuScene()
        }
    }


    val exitButton: Button = Button(
        height = 80,
        width = 200,
        posX = 50,
        posY = 330,
        text = "Exit",
        font = Font(color = Color.BLACK),
        visual = ColorVisual(136, 221, 136)
    )

    private val menuLabel: Label = Label(
        height = 100,
        width = 200,
        posX = 50,
        posY = 0,
        text = "Shift Poker Game",
        font = Font(fontWeight = Font.FontWeight.BOLD, size = 20)
    )
    private val devLabel: Label = Label(
        height = 100,
        width = 200,
        posX = 50,
        posY = 430,
        text = "by Adem Jabri ;)",
        font = Font(fontWeight = Font.FontWeight.BOLD)
    )


    init {
        opacity = .5
        addComponents(
            menuLabel,
            //continueGameButton,
            startButton,
            exitButton,
            devLabel
        )
    }
}
