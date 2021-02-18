package com.rockman.helloMayor.actor

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.rockman.helloMayor.actor.facilitates.House
import com.rockman.helloMayor.actor.facilitates.Office
import com.rockman.helloMayor.actor.facilitates.Playground
import com.rockman.helloMayor.actor.facilitates.Restaurant

class FacilitateButton(var type: Facilitate.Type) : Actor() {
    lateinit var texture: Texture

    init {
        when (type) {
            Facilitate.Type.OFFICE -> {
                texture = Office.TEXTURE
            }
            Facilitate.Type.PLAYGROUND -> {
                texture = Playground.TEXTURE
            }
            Facilitate.Type.RESTAURANT -> {
                texture = Restaurant.TEXTURE
            }
            Facilitate.Type.HOUSE -> {
                texture = House.TEXTURE
            }
            else -> {
            }
        }
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.draw(texture, x, y, width, height)
    }
}