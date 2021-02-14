// https://stackoverflow.com/questions/42337003/multidimensional-3d-matrix-in-kotlin

package net.draycia.conway.math

import kotlin.math.roundToInt

/**
 * 2 Dimensional Integer Matrix
 */
class Matrix2Df(
    private val v: Array<Float>,
    val nx: Int,
    val ny: Int,
    val offset: Int,
    val xstride: Int,
    val ystride: Int) {
    // TODO: Check that the nx,ny,offset,strides etc are valid

    constructor(nx: Int, ny: Int): this(Array(nx*ny) { 0f }, nx, ny, 0, 1, nx)

    fun offsetof(ix: Int, iy: Int): Int {
        return offset + (ix * xstride) + (iy * ystride)
    }

    operator fun get(ix: Int, iy: Int): Float {
        return v[offsetof(ix,iy)]
    }

    operator fun set(ix: Int, iy: Int, v: Float) {
        this.v[offsetof(ix,iy)] = v
    }

    operator fun get(ix: Int): Matrix1Df {
        return Matrix1Df(v, ny, offsetof(ix,0), ystride)
    }

    fun transpose(): Matrix2Df {
        return Matrix2Df(v, ny, nx, offset, ystride, xstride)
    }

    fun submatrix(startx: Int, starty: Int, newNX: Int, newNY: Int): Matrix2Df {
        return Matrix2Df(v, newNX, newNY, offsetof(startx, starty), xstride, ystride)
    }

    fun transform(body: (Float, Int, Int) -> Float) {
        for (iy in 0 until ny) {
            for (ix in 0 until nx) {
                this[ix, iy] = body(this[ix, iy], ix, iy)
            }
        }
    }

    fun bake(): Matrix2Df {
        val rv = Matrix2Df(nx,ny)

        for (ix in 0 until nx) {
            for (iy in 0 until ny) {
                rv[ix, iy] = this[ix, iy]
            }
        }

        return rv
    }

    override fun toString(): String {
        val builder = StringBuilder()

        // Print the populated matrix
        for (x in 0 until nx) {
            val line = StringBuilder()

            for (y in 0 until ny) {
                line.append(this[x, y].roundToInt())
            }

            builder.append(line.append("\n"))
        }

        return builder.toString()
    }
}