package com.rockman.helloMayor.listener

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.rockman.helloMayor.App

object InputListener : InputProcessor {
    private var lastDragPosition: Vector2? = null
    private var mousePosition = Vector2()
    override fun keyDown(keycode: Int): Boolean {
        when(keycode){
            Input.Keys.F2->{
                App.stage.isDebugAll = true
                App.stage.setDebugUnderMouse(false)
            }
            Input.Keys.F3->{
                App.stage.isDebugAll = false
                App.stage.setDebugUnderMouse(true)
            }
            else->{
                println("Mouse x: ${mousePosition.x}, y: ${mousePosition.x}")
                var worldPosition = App.camera.unproject(Vector3(mousePosition.x, mousePosition.y, 0f))
                println("World x: ${worldPosition.x}, y: ${worldPosition.x}")
            }
        }
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        return true
    }

    override fun keyTyped(character: Char): Boolean {
        return true
    }


    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        mousePosition = Vector2(screenX.toFloat(), screenY.toFloat())
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        //GameStage.touchDownPosition = Vector2(screenX.toFloat(), screenY.toFloat())
        return true
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        // -1 da 1 xiao
        if (App.camera.zoom < 20f && amountY > 0) {
            App.camera.zoom *= 2f
        } else if (App.camera.zoom > 0.2f && amountY < 0) {
            App.camera.zoom /= 2f
        }
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        if (lastDragPosition != null) {
            App.camera.translate(
                    (lastDragPosition?.x!! - screenX.toFloat()) * App.camera.zoom,
                    (screenY.toFloat() - lastDragPosition?.y!!) * App.camera.zoom,
                    0f)
        }
        lastDragPosition = Vector2(screenX.toFloat(), screenY.toFloat())
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        lastDragPosition = null
        return true
    }
}