package com.rockman.helloMayor.actor.facilitates

import com.badlogic.gdx.graphics.Texture
import com.rockman.helloMayor.App
import com.rockman.helloMayor.actor.Facilitate
import ktx.assets.getValue
import ktx.assets.loadOnDemand

class House(x: Float, y: Float) : Facilitate(Type.HOUSE, x, y) {
    companion object {
        val TEXTURE by App.am.loadOnDemand<Texture>("square.png", App.textureParameter)
    }

    init {
        width = 200f
        height = 200f
        internalX = 100f
        internalY = 100f
        drawHeight = 270f
        drawWidth = 270f
        texture = TEXTURE

        parkingPointList.add(ParkingPoint(50f, 150f))
        parkingPointList.add(ParkingPoint(150f, 150f))
        parkingPointList.add(ParkingPoint(50f, 50f))
        parkingPointList.add(ParkingPoint(150f, 50f))
    }
}