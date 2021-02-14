// https://github.com/jobtalle/CubicNoise/blob/master/java/CubicNoise.java
package net.draycia.conway.math

import kotlin.math.floor

class CubicNoise(
    private val seed: Int,
    private val octave: Int,
    private val periodx: Int = Integer.MAX_VALUE,
    private val periody: Int = Integer.MAX_VALUE
) {

    private val randA = 134775813
    private val randB = 1103515245

    fun populate(matrix: Matrix2Df) {
        for (x in 0 until matrix.nx) {
            for (y in 0 until matrix.ny) {
                matrix[x, y] = sample(x.toFloat(), y.toFloat())
            }
        }
    }

    fun sample(x: Float): Float {
        val xi = floor((x / octave).toDouble()).toInt()
        val lerp = x / octave - xi
        return interpolate(
            randomize(seed, tile(xi - 1, periodx), 0),
            randomize(seed, tile(xi, periodx), 0),
            randomize(seed, tile(xi + 1, periodx), 0),
            randomize(seed, tile(xi + 2, periodx), 0),
            lerp
        ) * 0.666666f + 0.166666f
    }

    fun sample(x: Float, y: Float): Float {
        val xi = floor((x / octave).toDouble()).toInt()
        val lerpx = x / octave - xi
        val yi = floor((y / octave).toDouble()).toInt()
        val lerpy = y / octave - yi
        val xSamples = FloatArray(4)
        for (i in 0..3) xSamples[i] = interpolate(
            randomize(seed, tile(xi - 1, periodx), tile(yi - 1 + i, periody)),
            randomize(seed, tile(xi, periodx), tile(yi - 1 + i, periody)),
            randomize(seed, tile(xi + 1, periodx), tile(yi - 1 + i, periody)),
            randomize(seed, tile(xi + 2, periodx), tile(yi - 1 + i, periody)),
            lerpx
        )
        return interpolate(xSamples[0], xSamples[1], xSamples[2], xSamples[3], lerpy) * 0.5f + 0.25f
    }

    private fun randomize(seed: Int, x: Int, y: Int): Float {
        return (((x xor y) * randA xor seed + x) * (randB * x shl 16 xor randB * y - randA)).toFloat() / Int.MAX_VALUE
    }

    private fun tile(coordinate: Int, period: Int): Int {
        return coordinate % period
    }

    private fun interpolate(a: Float, b: Float, c: Float, d: Float, x: Float): Float {
        val p = (d - c) - (a - b)

        return x * (x * (x * p + ((a - b) - p)) + (c - a)) + b
    }

}