package com.rockman.helloMayor.stages

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import ktx.scene2d.Scene2DSkin

class MenuStage(assetManager: AssetManager): Stage() {
    init {
        var t = Table(Scene2DSkin.defaultSkin)
        t.setFillParent(true)
        t.background("white")
        t.add(Label("Hello Leo", Scene2DSkin.defaultSkin))
        this.addActor(t)
    }
}