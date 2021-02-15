package net.draycia.conway.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import net.draycia.conway.Conway

object DesktopLauncher {
    /**
     * Application entry point.
     * Creates the LWJGL window and starts the game.
     */
    @JvmStatic
    fun main(arg: Array<String>) {
        // TODO: let user configure target framerate
        // TODO: auto size window based on grid size
        val config = LwjglApplicationConfiguration().apply {
            title = "Conway"
            width = 512 // magic number, cell width * 16
            height = 512 // magic number, cell height * 16
            backgroundFPS = 60
            foregroundFPS = 60
            resizable = false
        }

        LwjglApplication(Conway(), config)
    }
}