package net.draycia.conway.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import net.draycia.conway.Conway
import net.draycia.conway.ConwaySettings

object DesktopLauncher {
    /**
     * Application entry point.
     * Creates the LWJGL window and starts the game.
     */
    @JvmStatic
    fun main(arg: Array<String>) {
        // TODO: auto generate and load in with configurate
        // TODO: allow loading and saving of cell grids
        val settings = ConwaySettings()

        // TODO: let user configure target framerate
        // TODO: auto size window based on grid size
        val config = LwjglApplicationConfiguration().apply {
            title = "Conway"
            width = settings.width * 16 // magic number, cell width * 16
            height = settings.width * 16 // magic number, cell height * 16
            backgroundFPS = 60
            foregroundFPS = 60
            resizable = false
        }

        LwjglApplication(Conway(settings), config)
    }
}