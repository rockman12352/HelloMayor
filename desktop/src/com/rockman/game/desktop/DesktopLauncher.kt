package com.rockman.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.rockman.helloMayor.App
import com.rockman.helloMayor.util.Constants

object DesktopLauncher {
    @JvmStatic
    fun main(args: Array<String>) {
        var config = LwjglApplicationConfiguration()
        config.width = 200
        config.height = 200
        config.title = Constants.GAME_TITLE
        var app = LwjglApplication(App, config)
    }
}