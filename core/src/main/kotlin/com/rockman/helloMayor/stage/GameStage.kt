package com.rockman.helloMayor.stage

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import com.rockman.helloMayor.entity.GameController
import com.rockman.helloMayor.util.FrameRate

object GameStage : Stage() {
    private val gameController = GameController
    private var touchDownPosition = Vector2()
    private var active = true
    private val frameRate = FrameRate()

    init {
        isDebugAll = true
    }

    override fun act(delta: Float) {
        if (active) {
            super.act(delta)
            //println("acting")
            gameController.pass(delta)
        }
        frameRate.update()
        frameRate.render()
    }

    override fun draw() {
        super.draw()
        batch.projectionMatrix = camera.combined
        batch.begin()
        gameController.drawMap(batch)
        batch.end()
    }

    override fun keyDown(keyCode: Int): Boolean {
        if (keyCode == Input.Keys.SPACE) {
            active = !active
        }
        return super.keyDown(keyCode)
    }

//    fun click(screenX: Int, screenY: Int) {
//        val worldPosition = camera.unproject(Vector3(screenX.toFloat(), screenY.toFloat(), 0f))
//        hit(worldPosition.x, worldPosition.y, true)
//    }

}