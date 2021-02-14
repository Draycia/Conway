package net.draycia.conway

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.ScreenUtils
import com.halfdeadgames.kterminal.KTerminalData
import com.halfdeadgames.kterminal.KTerminalRenderer
import ktx.graphics.use
import net.draycia.conway.math.CubicNoise
import net.draycia.conway.math.Matrix2Df
import kotlin.math.roundToInt
import kotlin.random.Random

class Conway : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var terminalData: KTerminalData
    lateinit var terminalRenderer: KTerminalRenderer

    var width = 32
    var height = 32

    private val seed = Random.nextInt()
    private val noise = CubicNoise(seed, 1)

    private var matrix = Matrix2Df(32, 32)

    override fun create() {
        batch = SpriteBatch()
        // the width and height in characters of the display
        terminalData = KTerminalData(width, height, Color.WHITE.toFloatBits(), Color.BLACK.toFloatBits())
        terminalRenderer = KTerminalRenderer(batch, "fontSheet.png")

        noise.populate(matrix)
    }

    override fun render() {
        // Clear the screen, otherwise you get the repeating effect when you Out Of Bounds in some games
        ScreenUtils.clear(1f, 0f, 0f, 1f)
        // And reset the terminal data
        terminalData.clearAll()
        terminalData.resetCursor()

        // Begin do stuff
        updateCells()
        writeMatrix()
        // Stop do stuff

        // Render the terminal data
        batch.use {
            terminalRenderer.render(0f, 0f, terminalData)
        }
    }

    override fun dispose() {
        batch.dispose()
    }

    /**
     * Translates the given cell's value into a printable character.
     */
    private fun getCellDisplay(x: Int, y: Int, matrix: Matrix2Df): Char {
        return if (matrix[x, y].roundToInt() == 1) {
            '@'
        } else {
            ' '
        }
    }

    /**
     * Performs the (original) Conway's Game of Life rules on the supplied matrix's cells.
     *
     * The rules:
     *
     * 1) Any live cell with two or three live neighbours survives.
     * 2) Any dead cell with three live neighbours becomes a live cell.
     * 3) All other live cells die in the next generation. Similarly, all other dead cells stay dead.
     */
    private fun updateCells() {
        val nextGeneration = Matrix2Df(32, 32)

        for (x in 0 until matrix.nx) {
            for (y in 0 until matrix.ny) {
                val livingNeighbors = getLivingNeighbors(x, y)
                val isCellLiving = matrix[x, y].roundToInt() == 1

                // 1) Any live cell with two or three live neighbours survives.
                if (isCellLiving && (livingNeighbors == 2 || livingNeighbors == 3)) {
                    nextGeneration[x, y] = 1f // It's alive!
                }

                // 2) Any dead cell with three live neighbours becomes a live cell.
                else if (!isCellLiving && livingNeighbors == 3) {
                    nextGeneration[x, y] = 1f // It's alive!
                }

                // 3) All other live cells die in the next generation. Similarly, all other dead cells stay dead.
                else if (isCellLiving) {
                    nextGeneration[x, y] = 0f // Kill it
                }
            }
        }

        matrix = nextGeneration
    }

    /**
     * Calculates the number of living neighbors for the given cell.
     */
    private fun getLivingNeighbors(x: Int, y: Int): Int {
        // black magic https://stackoverflow.com/a/5802694
        val startPosX: Int = if (x - 1 < 0) x else x - 1
        val startPosY: Int = if (y - 1 < 0) y else y - 1
        val endPosX: Int = if (x + 1 > matrix.nx) x else x + 1
        val endPosY: Int = if (y + 1 > matrix.ny) y else y + 1

        var livingNeighbors = 0

        for (rowNum in startPosX until endPosX) {
            for (colNum in startPosY until endPosY) {
                if (matrix[rowNum, colNum].roundToInt() == 1) {
                    livingNeighbors++
                }
            }
        }

        return livingNeighbors
    }

    /**
     * Writes the given matrix's data to the terminal.
     *
     * Automatically handles conversion of the matrix's cell values to printable characters.
     */
    private fun writeMatrix() {
        // Iterate through each cell in the x and y directions
        for (x in 0 until matrix.nx) {
            for (y in 0 until matrix.ny) {
                // Move the cursor to the current iteration's position
                terminalData.setCursor(x, y)
                // Translate the cell's value into a printable char, and write it to the terminal
                terminalData.write(getCellDisplay(x, y, matrix))
            }
        }
    }
}