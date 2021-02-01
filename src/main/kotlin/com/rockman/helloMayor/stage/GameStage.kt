package com.rockman.helloMayor.stage

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.rockman.helloMayor.App
import com.rockman.helloMayor.service.GameService

object GameStage : Stage() {
    private val gameService = GameService
    private var touchDownPosition = Vector2()
    private var lastDragPosition: Vector2? = null

    override fun act(delta: Float) {
        super.act(delta)
        gameService.pass(delta)
    }

    override fun draw() {
        super.draw()
//        batch.begin();
//        batch.end()
    }

//    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
//        var worldPosition = camera.unproject(Vector3(screenX.toFloat(), screenY.toFloat(), 0f))
//        var hit = hit(worldPosition.x, worldPosition.y, true)
//        return true
//    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        touchDownPosition = Vector2(screenX.toFloat(), screenY.toFloat())
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        if (lastDragPosition != null) {
            camera.translate(lastDragPosition?.x!! - screenX.toFloat(), screenY.toFloat() - lastDragPosition?.y!!, 0f)
        }
        lastDragPosition = Vector2(screenX.toFloat(), screenY.toFloat())
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        lastDragPosition = null
        return true
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        // -1 da 1 xiao
        if (amountY > 0) {
            App.camera.zoom += 1
        } else if (App.camera.zoom > 1f && amountY < 0) {
            App.camera.zoom -= 1
        }
        return true
    }

}