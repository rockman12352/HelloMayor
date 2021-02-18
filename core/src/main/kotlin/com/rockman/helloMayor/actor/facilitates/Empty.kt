package com.rockman.helloMayor.actor.facilitates

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.rockman.helloMayor.actor.Facilitate
import com.rockman.helloMayor.stage.GameStage

class Empty(x: Float, y: Float) : Facilitate(Type.EMPTY, x, y) {
    companion object {
        val TEXTURE = Texture(Pixmap(1, 1, Pixmap.Format.RGB888))
    }

    init {
        width = 100f
        height = 100f
        internalX = 50f
        internalY = 50f
        drawHeight = 10f
        drawWidth = 10f
        texture = TEXTURE
        setInternalPosition(x, y)
        addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                onClick()
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }
        })
    }

    fun onClick() {
        (stage as GameStage).selectedPoint = this
    }
}