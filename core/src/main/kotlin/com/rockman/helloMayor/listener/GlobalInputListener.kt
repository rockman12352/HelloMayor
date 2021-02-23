package com.rockman.helloMayor.listener

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.rockman.helloMayor.App
import com.rockman.helloMayor.stage.GameStage

object GlobalInputListener : InputProcessor {
    private var lastDragPosition: Vector2? = null
    private var startDragPosition: Vector2? = null
    private var mousePosition = Vector2()
    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.F2 -> {
                App.stage.isDebugAll = true
                App.stage.setDebugUnderMouse(false)
            }
            Input.Keys.F3 -> {
                App.stage.isDebugAll = false
                App.stage.setDebugUnderMouse(true)
            }
            Input.Keys.F5 -> {
                (App.stage as GameStage).addHuman(mousePosition.x, mousePosition.y)
            }
            else -> {
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
        if (button == Input.Buttons.LEFT) {
            GameStage.click(screenX, screenY)
        }
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        var isDraging = lastDragPosition != null && startDragPosition != null
        startDragPosition = null
        lastDragPosition = null
        return isDraging
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        if (lastDragPosition == null && startDragPosition == null) {
            startDragPosition = Vector2(screenX.toFloat(), screenY.toFloat())
        } else if (lastDragPosition != null && startDragPosition != null) {
            App.camera.translate(
                    (lastDragPosition?.x!! - screenX.toFloat()) * App.camera.zoom,
                    (screenY.toFloat() - lastDragPosition?.y!!) * App.camera.zoom,
                    0f)
            lastDragPosition = Vector2(screenX.toFloat(), screenY.toFloat())
        } else if (lastDragPosition == null && dragOverDistance(screenX, screenY)) {
            lastDragPosition = Vector2(screenX.toFloat(), screenY.toFloat())
        }
        return true
    }

    private fun dragOverDistance(screenX: Int, screenY: Int): Boolean {
        return Vector2.dst(screenX.toFloat(), screenY.toFloat(), startDragPosition?.x!!, startDragPosition?.y!!) > App.config.dragSensitive
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        // -1 bigger 1 smaller
        if (App.camera.zoom < 20f && amountY > 0) {
            App.camera.zoom *= 2f
        } else if (App.camera.zoom > 0.2f && amountY < 0) {
            App.camera.zoom /= 2f
        }
        return true
    }
}