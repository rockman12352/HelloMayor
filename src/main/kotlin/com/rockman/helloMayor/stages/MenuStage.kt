package com.rockman.helloMayor.stages

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import ktx.scene2d.Scene2DSkin

class MenuStage(assetManager: AssetManager, onStart: EventListener): Stage() {
    init {
        var t = Table(Scene2DSkin.defaultSkin)
        t.setFillParent(true)
        t.background("white")
        var b = TextButton("Start", Scene2DSkin.defaultSkin)
        b.addListener(onStart)
        t.add(b)
        t.add(Label("Hello Leo", Scene2DSkin.defaultSkin))
        this.addActor(t)
    }
}