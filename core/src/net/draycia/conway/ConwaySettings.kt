package net.draycia.conway

import com.badlogic.gdx.graphics.Color

data class ConwaySettings(val width: Int = 32,
                          val height: Int = 32,
                          val fgColour: Color = Color.WHITE,
                          val bgColour: Color = Color.BLACK,
                          val ticksPerSecond: Long = 1L,
                          val livingCellChar: Char = '@',
                          val deadCellChar: Char = ' ')
