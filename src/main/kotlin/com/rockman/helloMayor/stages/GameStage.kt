package com.rockman.helloMayor.stages

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.rockman.helloMayor.App
import ktx.assets.getValue
import ktx.assets.loadOnDemand


class GameStage(var app: App): Stage() {
    val rockman by app.am.loadOnDemand<TextureAtlas>("rockman.atlas")
    var animation = Animation(1 / 15f, rockman.findRegions("zero-run"))
    var elapsedTime = 0f
    init {

    }

    override fun draw() {
        super.draw()
        batch.begin();
        //sprite.draw(batch)
        elapsedTime += Gdx.graphics.getDeltaTime()
        batch.draw(animation.getKeyFrame(elapsedTime, true), 0f, 0f)
        batch.end()
    }
}