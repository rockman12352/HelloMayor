package com.rockman.helloMayor.actor

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Actor
import com.rockman.helloMayor.actor.facilitates.House
import com.rockman.helloMayor.actor.facilitates.Office
import com.rockman.helloMayor.actor.facilitates.Playground
import com.rockman.helloMayor.actor.facilitates.Restaurant
import kotlin.reflect.KFunction

class FacilitateButton(var type: Facilitate.Type) : Actor() {
    lateinit var texture: Texture
    lateinit var constructFunction: KFunction<Facilitate>

    init {
        when (type) {
            Facilitate.Type.OFFICE -> {
                texture = Office.TEXTURE
                constructFunction = Office::class.constructors.first()
            }
            Facilitate.Type.PLAYGROUND -> {
                texture = Playground.TEXTURE
                constructFunction = Playground::class.constructors.first()
            }
            Facilitate.Type.RESTAURANT -> {
                texture = Restaurant.TEXTURE
                constructFunction = Restaurant::class.constructors.first()
            }
            Facilitate.Type.HOUSE -> {
                texture = House.TEXTURE
                constructFunction = House::class.constructors.first()
            }
            else -> {
            }
        }
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.draw(texture, x, y, width, height)
    }

    fun click(position: Vector3): Boolean {
        return position.x > x && position.y > y && position.x < (x + width) && position.y < (y + height)
    }
}