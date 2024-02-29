package view
import kotlin.test.*
import view.*
import service.*
import entity.*
class EndSceneTest {
    @Test
    fun makeItEasyTest(){
        val rootService = RootService()
        val gameService = rootService.shiftPokerGameService
        val reus = Player("reus")
        val suarez = Player("suarez")
        val pique = Player("pique")
        val messi = Player("messi")

        val playerList = mutableListOf(reus, suarez, pique, messi)
        gameService.startGame(3, playerList)
        val ranking = gameService.evaluateGame()
        val endScene = EndScene(rootService)
        val realRanking = endScene.makeItEasy(ranking)
        assertNotNull(realRanking[0])
        assertNotNull(realRanking[1])
        assertNotNull(realRanking[2])
        assertNotNull(realRanking[3])
        println(realRanking)


    }
}