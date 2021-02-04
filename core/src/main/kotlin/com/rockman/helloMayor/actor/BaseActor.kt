package com.rockman.helloMayor.actor

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor

open class BaseActor : Actor() {
    var internalX = 0f
    var internalY = 0f
    var drawWidth = 0f
    var drawHeight = 0f
    lateinit var texture: Texture
    override fun drawDebug(shapes: ShapeRenderer?) {
        super.drawDebug(shapes)
        shapes?.line(x + internalX - 10, y + internalY, x + internalX + 10, y + internalY)
        shapes?.line(x + internalX, y + internalY - 10, x + internalX, y + internalY + 10)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        batch?.draw(texture, x + internalX - drawWidth / 2, y + internalY - drawHeight / 2, drawWidth, drawHeight)
    }

    fun setInternalPosition(x: Float, y: Float) {
        setPosition(x - internalX, y - internalY)
    }

    fun setInternalPosition(position: Vector2) {
        setPosition(position.x - internalX, position.y - internalY)
    }
}