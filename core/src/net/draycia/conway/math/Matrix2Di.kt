// https://stackoverflow.com/questions/42337003/multidimensional-3d-matrix-in-kotlin

package net.draycia.conway.math

/**
 * 2 Dimensional Integer Matrix
 */
class Matrix2Di(
    private val v: Array<Int>,
    val nx: Int,
    val ny: Int,
    val offset: Int,
    val xstride: Int,
    val ystride: Int) {
    // TODO: Check that the nx,ny,offset,strides etc are valid

    constructor(nx: Int, ny: Int): this(Array(nx*ny) { 0 }, nx, ny, 0, 1, nx)

    fun offsetof(ix: Int, iy: Int): Int {
        return offset + (ix * xstride) + (iy * ystride)
    }

    operator fun get(ix: Int, iy: Int): Int {
        return v[offsetof(ix,iy)]
    }

    operator fun set(ix: Int, iy: Int, v: Int) {
        this.v[offsetof(ix,iy)] = v
    }

    operator fun get(ix: Int): Matrix1Di {
        return Matrix1Di(v, ny, offsetof(ix,0), ystride)
    }

    fun transpose(): Matrix2Di {
        return Matrix2Di(v, ny, nx, offset, ystride, xstride)
    }

    fun submatrix(startx: Int, starty: Int, newNX: Int, newNY: Int): Matrix2Di {
        return Matrix2Di(v, newNX, newNY, offsetof(startx, starty), xstride, ystride)
    }

    fun transform(body: (Int, Int, Int) -> Int) {
        for (iy in 0 until ny) {
            for (ix in 0 until nx) {
                this[ix, iy] = body(this[ix, iy], ix, iy)
            }
        }
    }

    fun bake(): Matrix2Di {
        val rv = Matrix2Di(nx,ny)

        for (ix in 0 until nx) {
            for (iy in 0 until ny) {
                rv[ix, iy] = this[ix, iy]
            }
        }

        return rv
    }
}