// https://stackoverflow.com/questions/42337003/multidimensional-3d-matrix-in-kotlin
package net.draycia.conway.math

/**
 * 1 Dimensional Integer Matrix
 */
class Matrix1Di(
    private val v: Array<Int>,
    private val nx: Int,
    private val offset: Int,
    private val xstride: Int) {
    // TODO: Check that the nx,offset,strides etc are valid

    constructor(nx: Int): this(Array(nx) { 0 }, nx, 0, 1)

    fun offsetof(ix: Int): Int {
        return offset + ix * xstride
    }

    operator fun get(ix: Int): Int {
        return v[offsetof(ix)]
    }

    operator fun set(ix: Int, v: Int) {
        this.v[offsetof(ix)] = v
    }

    fun reverse(): Matrix1Di {
        return Matrix1Di(v, nx, offsetof(nx-1), -xstride)
    }

    fun submatrix(startx: Int, newNX: Int): Matrix1Di {
        return Matrix1Di(v, newNX, offsetof(startx), xstride)
    }

    fun transform(body: (Int, Int) -> Int) {
        for (ix in 0 until nx) {
            this[ix] = body(this[ix], ix)
        }
    }

    fun bake() : Matrix1Di {
        val rv = Matrix1Di(nx)

        for (ix in 0 until nx) {
            rv[ix] = this[ix]
        }

        return rv
    }
}