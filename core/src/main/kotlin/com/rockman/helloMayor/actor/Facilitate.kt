package com.rockman.helloMayor.actor

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.rockman.helloMayor.App
import ktx.assets.getValue
import ktx.assets.loadOnDemand


abstract class Facilitate(
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
    }

    fun availableParkingPoint(): ParkingPoint? {
        return parkingPointList.firstOrNull { it.occupant == null }
    }

    class ParkingPoint(val x: Float, val y: Float) {
        var occupant: Human? = null
        fun unpark() {
            occupant = null
        }

        fun park(human: Human) {
            occupant = human
        }
    }
}