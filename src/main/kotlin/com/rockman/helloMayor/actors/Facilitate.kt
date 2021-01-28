package com.rockman.helloMayor.actors

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.rockman.helloMayor.App
import ktx.assets.getValue
import ktx.assets.loadOnDemand


class Facilitate(
        val type: Type
) : Actor() {
    enum class Type {
        HOUSE, RESTAURANT, PLAYGROUND, OFFICE
    }

    companion object {
        lateinit var RECTANGLE: Texture
        val SQUARE by App.am.loadOnDemand<Texture>("square.png")
        init {

//            val pixmap = Pixmap(50, 50, Pixmap.Format.RGBA8888)
//            pixmap.setColor(Color.BLACK)
//            pixmap.drawline
//            pixmap.fillRectangle(0, 0, 100, 100)
//            ShapeRenderer
//            RECTANGLE = Texture(pixmap)
//            pixmap.dispose()
        }
    }

    init {
        x = 0f
        y = 0f
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        batch?.draw(SQUARE, x, y)
    }


}