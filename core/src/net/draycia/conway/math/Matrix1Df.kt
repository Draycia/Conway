// https://stackoverflow.com/questions/42337003/multidimensional-3d-matrix-in-kotlin
package net.draycia.conway.math

/**
 * 1 Dimensional Integer Matrix
 */
class Matrix1Df(
    private val v: Array<Float>,
    private val nx: Int,
    private val offset: Int,
    private val xstride: Int) {
    // TODO: Check that the nx,offset,strides etc are valid

    constructor(nx: Int): this(Array(nx) { 0f }, nx, 0, 1)

    fun offsetof(ix: Int): Int {
        return offset + ix * xstride
    }

    operator fun get(ix: Int): Float {
        return v[offsetof(ix)]
    }

    operator fun set(ix: Int, v: Float) {
        this.v[offsetof(ix)] = v
    }

    fun reverse(): Matrix1Df {
        return Matrix1Df(v, nx, offsetof(nx-1), -xstride)
    }

    fun submatrix(startx: Int, newNX: Int): Matrix1Df {
        return Matrix1Df(v, newNX, offsetof(startx), xstride)
    }

    fun transform(body: (Float, Int) -> Float) {
        for (ix in 0 until nx) {
            this[ix] = body(this[ix], ix)
        }
    }

    fun bake() : Matrix1Df {
        val rv = Matrix1Df(nx)

        for (ix in 0 until nx) {
            rv[ix] = this[ix]
        }

        return rv
    }
}