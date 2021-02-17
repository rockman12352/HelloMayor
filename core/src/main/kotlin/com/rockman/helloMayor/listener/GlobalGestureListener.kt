package com.rockman.helloMayor.listener

import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import com.rockman.helloMayor.App

object GlobalGestureListener : GestureDetector.GestureListener {
    private var lastDragPosition: Vector2? = null
    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return true
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        return true
    }

    override fun longPress(x: Float, y: Float): Boolean {
        return true
    }

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        return true
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        if (lastDragPosition != null) {
            App.camera.translate(
                    (lastDragPosition?.x!! - x.toFloat()) * App.camera.zoom,
                    (y.toFloat() - lastDragPosition?.y!!) * App.camera.zoom,
                    0f)
        }
        lastDragPosition = Vector2(x.toFloat(), y.toFloat())
        return true
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        lastDragPosition = null
        return true
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean {
        return true
    }

    override fun pinch(initialPointer1: Vector2?, initialPointer2: Vector2?, pointer1: Vector2?, pointer2: Vector2?): Boolean {
        return true
    }

    override fun pinchStop() {
    }
}