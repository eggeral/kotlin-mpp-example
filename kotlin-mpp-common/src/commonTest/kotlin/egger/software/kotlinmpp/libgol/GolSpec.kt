package egger.software.kotlinmpp.libgol

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GolSpec {
    @Test
    fun aDeadCellWithExactlyThreeLivingNeighboursShouldBeAliveInTheNextGeneration() {
        //given
        val cell = Cell(false)
        cell.livingNeighbours = 3

        //when
        cell.calculateNextGeneration()

        //then
        assertTrue(cell.alive)
    }

    @Test
    fun aLivingCellWithLessThanTwoNeighboursShouldBeDeadInTheNextGeneration() {
        //given
        val cell = Cell(true)
        cell.livingNeighbours = 1

        //when
        cell.calculateNextGeneration()

        //then
        assertFalse(cell.alive)

    }

    @Test
    fun aLivingCellWithTwoNeighboursShouldBeAliveInTheNextGeneration() {
        //given
        val cell = Cell(true)
        cell.livingNeighbours = 2

        //when
        cell.calculateNextGeneration()

        //then
        assertTrue(cell.alive)
    }

    @Test
    fun aLivingCellWithThreeNeighboursShouldBeAliveInTheNextGeneration() {
        //given
        val cell = Cell(true)
        cell.livingNeighbours = 3

        //when
        cell.calculateNextGeneration()

        //then
        assertTrue(cell.alive)
    }

    @Test
    fun aLivingCellWithMoreThanThreeNeighboursShouldBeDeadInTheNextGeneration() {
        //given
        val cell = Cell(true)
        cell.livingNeighbours = 4

        //when
        cell.calculateNextGeneration()

        //then
        assertFalse(cell.alive)

    }

    @Test
    fun aDeadCellWithLessThanThreeLivingNeighboursShouldBeDead() {
        //given
        val cell = Cell(false)
        cell.livingNeighbours = 2

        //when
        cell.calculateNextGeneration()

        //then
        assertFalse(cell.alive)
    }

    @Test
    fun aGameCanCalculateTheNextGeneration() {
        //given
        val board = Board(rows = 3, columns = 3)
        board.setCells(
                """
                _*_
                _*_
                _*_
                """.trimIndent().cells())

        //when
        board.calculateNextGeneration()

        //then
        assertFalse(board.cellAt(0, 0).alive)
        assertFalse(board.cellAt(1, 0).alive)
        assertFalse(board.cellAt(2, 0).alive)
        assertTrue(board.cellAt(0, 1).alive)
        assertTrue(board.cellAt(1, 1).alive)
        assertTrue(board.cellAt(2, 1).alive)
        assertFalse(board.cellAt(0, 2).alive)
        assertFalse(board.cellAt(1, 2).alive)
        assertFalse(board.cellAt(2, 2).alive)
    }

    @Test
    fun theNumberOfLivingNeighboursOfACellCanBeCounted() {
        //given
        val board = Board(3, 3)
        board.setCells(
                """
                _*_
                _*_
                _*_
                """.trimIndent().cells())

        // when / then
        assertEquals(2, board.countLivingNeighbours(0, 0))
        assertEquals(1, board.countLivingNeighbours(1, 0))
        assertEquals(2, board.countLivingNeighbours(2, 0))

        assertEquals(3, board.countLivingNeighbours(0, 1))
        assertEquals(2, board.countLivingNeighbours(1, 1))
        assertEquals(3, board.countLivingNeighbours(2, 1))

        assertEquals(2, board.countLivingNeighbours(0, 2))
        assertEquals(1, board.countLivingNeighbours(1, 2))
        assertEquals(2, board.countLivingNeighbours(2, 2))

        //given
        board.setCells(
                """
                ***
                ***
                ***
                """.trimIndent().cells())


        // when / then
        assertEquals(8, board.countLivingNeighbours(1, 1))

        //given

        board.setCells(
                """
                ___
                ___
                ___
                """.trimIndent().cells())

        // when / then
        assertEquals(0, board.countLivingNeighbours(1, 1))

        //given
        board.setCells(
                """
                *_*
                ___
                *_*
                """.trimIndent().cells())

        // when / then
        assertEquals(4, board.countLivingNeighbours(1, 1))

    }

    @Test
    fun `Cells and positions can be parsed from a string`() {
        assertEquals(mapOf(
                Position2d(0, 0) to false,
                Position2d(1, 0) to true,
                Position2d(2, 0) to false,
                Position2d(0, 1) to true,
                Position2d(1, 1) to true,
                Position2d(2, 1) to false
        ),
                """
                _*_
                **o
                """.trimIndent().cells())

    }

    @Test
    fun `Cell definitions can be shifted to a position`() {
        assertEquals(mapOf(
                Position2d(5, 3) to false,
                Position2d(6, 3) to true,
                Position2d(7, 3) to false,
                Position2d(5, 4) to true,
                Position2d(6, 4) to true,
                Position2d(7, 4) to false
        ), mapOf(
                Position2d(0, 0) to false,
                Position2d(1, 0) to true,
                Position2d(2, 0) to false,
                Position2d(0, 1) to true,
                Position2d(1, 1) to true,
                Position2d(2, 1) to false
        ).translatedTo(column = 5, row = 3))

    }
}

