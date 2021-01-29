package com.rockman.helloMayor.stages

import com.badlogic.gdx.scenes.scene2d.Stage
import com.rockman.helloMayor.services.FacilitateService
import com.rockman.helloMayor.services.GameService
import com.rockman.helloMayor.services.HumanService

object GameStage : Stage() {
    private val gameService = GameService

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

}