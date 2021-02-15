package net.draycia.conway.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import net.draycia.conway.Conway

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        // TODO: let user configure target framerate
        // TODO: auto size window based on grid size
        val config = LwjglApplicationConfiguration().apply {
            title = "Conway"
            width = 512
            height = 512
            backgroundFPS = 60
            foregroundFPS = 60
            resizable = false
        }

        LwjglApplication(Conway(), config)
    }
}