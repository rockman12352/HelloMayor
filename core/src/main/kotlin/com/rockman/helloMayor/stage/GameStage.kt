package com.rockman.helloMayor.stage

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.rockman.helloMayor.App
import com.rockman.helloMayor.service.GameService

object GameStage : Stage() {
    private val gameService = GameService
    private var touchDownPosition = Vector2()
    private var active = true

    override fun act(delta: Float) {
        if (active) {
            super.act(delta)
            //println("acting")
            gameService.pass(delta)
        }
    }

    override fun draw() {
        super.draw()
        // App.camera.
//        batch.begin();
//        batch.end()
    }

    override fun keyDown(keyCode: Int): Boolean {
        if (keyCode == Input.Keys.SPACE) {
            active = !active
        }
        return super.keyDown(keyCode)
    }

//    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
//        var worldPosition = camera.unproject(Vector3(screenX.toFloat(), screenY.toFloat(), 0f))
//        var hit = hit(worldPosition.x, worldPosition.y, true)
//        return true
//    }

}