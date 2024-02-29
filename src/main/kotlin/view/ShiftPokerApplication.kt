package view

import entity.Player
import service.RootService
import tools.aqua.bgw.core.BoardGameApplication



/**
 * Implementation of the BGW [BoardGameApplication] for the example card game "ShiftPoker".
 */

class ShiftPokerApplication : BoardGameApplication("ShiftPoker Game"), Refreshable {

    private val rootService = RootService()
    private val gameScene = GameScene(rootService)
    private val player1 = Player("player 1")
    private val player2 = Player("player 2")

    private val startMenuScene = StartMenuScene(rootService, this).apply {
        exitButton.onMouseClicked = { exit() }
    }
    private val lobbyScene = LobbyScene(rootService).apply {
        returnButton.onMouseClicked = {
            this@ShiftPokerApplication.showMenuScene(startMenuScene)
        }
    }

    private val endScene = EndScene(rootService).apply {
        replayButton.onMouseClicked = {
            rootService.shiftPokerGameService.startGame(2, mutableListOf(player1, player2))
            this@ShiftPokerApplication.showGameScene(gameScene)
            this@ShiftPokerApplication.showMenuScene(startMenuScene)
        }
        goodbyeButton.onMouseClicked = {
            exit()
        }

    }

    init {
        rootService.shiftPokerGameService.startGame(2, mutableListOf(player1, player2))
        this.showGameScene(gameScene)
        this.showMenuScene(startMenuScene)
    }
    /**
     * perform refreshes that are necessary after clicking the startButton
     */
    fun refreshAfterMenuScene() {
        this.showMenuScene(lobbyScene)
    }

    override fun refreshAfterStartGame() {
        this.hideMenuScene()
    }

    override fun refreshAfterEndGame(ranking: MutableList<Pair<List<Player>, String>>) {
        this.showMenuScene(endScene)
    }

    init {
        rootService.addRefreshables(
            gameScene,
            lobbyScene,
            startMenuScene,
            endScene,
            this
        )
    }
}

