package com.rockman.helloMayor.entity

import com.rockman.helloMayor.stage.GameStage

object GameController {
    fun resize(width: Int, height: Int) {
        stage.resize(width, height)
    }

    private val stage = GameStage
}