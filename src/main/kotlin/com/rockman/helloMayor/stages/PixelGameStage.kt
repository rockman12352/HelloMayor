package com.rockman.helloMayor.stages

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.rockman.helloMayor.App
import com.rockman.helloMayor.actors.Hero
import ktx.assets.getValue
import ktx.assets.loadOnDemand


class PixelGameStage : Stage() {
    val rockman by App.am.loadOnDemand<TextureAtlas>("human/human1.atlas")
    var animation = Animation(1 / 15f, rockman.findRegions("run-left"))
    var elapsedTime = 0f
    var human = Hero("human1")

    init {
        addActor(human)
    }

    override fun draw() {
        super.draw()
        batch.begin();
        //sprite.draw(batch)
        elapsedTime += Gdx.graphics.getDeltaTime()
        batch.draw(animation.getKeyFrame(elapsedTime, true), 0f, 0f)
        batch.end()
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        var vex = screenToStageCoordinates(Vector2(screenX.toFloat(), screenY.toFloat()))
        human.setTargetXY(vex.x, vex.y)
        return super.touchDown(screenX, screenY, pointer, button)
    }
}