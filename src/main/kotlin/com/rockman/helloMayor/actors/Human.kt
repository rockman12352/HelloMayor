package com.rockman.helloMayor.actors

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.rockman.helloMayor.App
import com.rockman.helloMayor.entities.StateSequence
import ktx.assets.getValue
import ktx.assets.loadOnDemand

class Human(
        val state: StateSequence = StateSequence()
) : Actor() {
    var endurance: Int = 10
    val texture by App.am.loadOnDemand<Texture>("human.png")

    constructor(endurance: Int) : this() {
        this.endurance = endurance
    }

    init {
        x = -100f
        y = -100f
    }


    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        batch?.draw(texture, x, y)
    }

    fun pass(delta: Float) {
        state.consume(delta.toInt())
    }
}