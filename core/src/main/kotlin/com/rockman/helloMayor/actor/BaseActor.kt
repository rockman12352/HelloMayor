package com.rockman.helloMayor.actor

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor

open class BaseActor : Actor() {
    var internalX = 50f
    var internalY = 50f
    override fun drawDebug(shapes: ShapeRenderer?) {
        super.drawDebug(shapes)
        shapes?.line(x + internalX - 10, y + internalY, x + internalX + 10, y + internalY)
        shapes?.line(x + internalX, y + internalY - 10, x + internalX, y + internalY + 10)
    }
}