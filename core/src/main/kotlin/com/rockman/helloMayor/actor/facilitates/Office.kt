package com.rockman.helloMayor.actor.facilitates

import com.badlogic.gdx.graphics.Texture
import com.rockman.helloMayor.App
import com.rockman.helloMayor.actor.Facilitate
import ktx.assets.getValue
import ktx.assets.loadOnDemand

class Office(x: Float, y: Float) : Facilitate(Type.OFFICE, x, y) {
    companion object {
        val TEXTURE by App.am.loadOnDemand<Texture>("triangle.png", App.textureParameter)
    }

    init {
        width = 200f
        height = 200f
        internalX = 100f
        internalY = 100f
        drawHeight = 270f
        drawWidth = 270f
        texture = TEXTURE

        parkingPointList.add(ParkingPoint(175f, 175f))
        parkingPointList.add(ParkingPoint(25f, 25f))
        parkingPointList.add(ParkingPoint(175f, 25f))
    }
}