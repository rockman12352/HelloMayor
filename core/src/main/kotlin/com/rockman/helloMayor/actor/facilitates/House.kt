package com.rockman.helloMayor.actor.facilitates

import com.badlogic.gdx.graphics.g2d.Batch
import com.rockman.helloMayor.actor.Facilitate

class House(x: Float, y: Float) : Facilitate(Type.HOUSE, x, y) {
    init {
        this.x -= width / 2
        this.y -= height / 2
        parkingPointList.add(ParkingPoint(25f, 175f))
        parkingPointList.add(ParkingPoint(175f, 175f))
        parkingPointList.add(ParkingPoint(25f, 25f))
        parkingPointList.add(ParkingPoint(175f, 25f))
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch?.draw(SQUARE, x, y)
    }
}