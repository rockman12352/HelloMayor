package com.rockman.helloMayor.actor.facilitates

import com.badlogic.gdx.graphics.Texture
import com.rockman.helloMayor.App
import com.rockman.helloMayor.actor.Facilitate
import ktx.assets.getValue
import ktx.assets.loadOnDemand

class Playground(x: Float, y: Float) : Facilitate(Type.PLAYGROUND, x, y) {
    companion object {
        val TEXTURE by App.am.loadOnDemand<Texture>("star.png", App.textureParameter)
    }

    init {
        width = 200f
        height = 200f
        internalX = 100f
        internalY = 100f
        drawHeight = 270f
        drawWidth = 270f
        texture = TEXTURE

        parkingPointList.add(ParkingPoint(25f, 175f))
    }
}