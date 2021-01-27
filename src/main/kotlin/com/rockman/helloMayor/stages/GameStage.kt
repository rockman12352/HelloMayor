package com.rockman.helloMayor.stages

import com.badlogic.gdx.scenes.scene2d.Stage
import com.rockman.helloMayor.actors.Human

class GameStage() : Stage() {
    val m = Human()

    init {

    }

    override fun act(delta: Float) {
        super.act(delta)
        m.pass(delta*1000)
    }
}