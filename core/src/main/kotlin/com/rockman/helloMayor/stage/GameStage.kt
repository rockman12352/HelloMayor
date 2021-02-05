package com.rockman.helloMayor.stage

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
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
            root.children.forEachIndexed { idx, actor ->
                if (actor == null) {
                    println("act null idx = ${idx}")
                }
            }
            super.act(delta)
            //println("acting")
            gameController.pass(delta)
        }
        frameRate.update()
        frameRate.render()
    }

    override fun draw() {
        root.children.forEachIndexed { idx, actor ->
            if (actor == null) {
                println("draw null idx = ${idx}")
            }
        }
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

//    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
//        var worldPosition = camera.unproject(Vector3(screenX.toFloat(), screenY.toFloat(), 0f))
//        var hit = hit(worldPosition.x, worldPosition.y, true)
//        return true
//    }

}