package net.draycia.conway.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import net.draycia.conway.Conway

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration().apply {
            title = "Conway"
            width = 512
            height = 512
            backgroundFPS = 2
            foregroundFPS = 2
            resizable = false
        }

        LwjglApplication(Conway(), config)
    }
}