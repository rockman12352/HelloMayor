package com.rockman.helloMayor.actor

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.rockman.helloMayor.App
import ktx.assets.getValue
import ktx.assets.loadOnDemand


open class Facilitate(
        val type: Type
) : BaseActor() {
    val queue = mutableListOf<Human>()
    val parkingPointList = mutableListOf<ParkingPoint>()

    constructor(type: Type, x: Float, y: Float) : this(type) {
        this.x = x
        this.y = y
    }

    enum class Type {
        HOUSE, RESTAURANT, PLAYGROUND, OFFICE
    }

    fun getCapacity(): Int {
        return parkingPointList.size
    }

    override fun toString(): String {
        return type.name
    }

    companion object {
        lateinit var RECTANGLE: Texture
        val SQUARE by App.am.loadOnDemand<Texture>("square.png", App.textureParameter)
        val STAR by App.am.loadOnDemand<Texture>("star.png", App.textureParameter)
        val TRIANGLE by App.am.loadOnDemand<Texture>("triangle.png", App.textureParameter)

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
        width = 100f
        height = 100f
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        when (type) {
            Type.HOUSE -> batch?.draw(SQUARE, x, y)
            Type.OFFICE -> batch?.draw(TRIANGLE, x, y)
            Type.PLAYGROUND -> batch?.draw(STAR, x, y)
            else -> batch?.draw(STAR, x, y)
        }
    }

    class ParkingPoint(val x: Float, val y: Float) {
        var available = true
    }
}