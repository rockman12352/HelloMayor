package com.rockman.helloMayor.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.rockman.helloMayor.entities.State

class Human : Actor() {
    val state = State()
    fun pass(delta:Float)
    {
        state.consume(delta.toInt())
    }
}