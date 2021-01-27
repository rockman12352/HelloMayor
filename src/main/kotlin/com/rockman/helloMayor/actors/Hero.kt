package com.rockman.helloMayor.actors

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Actor
import com.rockman.helloMayor.App
import ktx.assets.getValue
import ktx.assets.loadOnDemand

class Hero(var spriteName: String) : Actor() {
    val atlas by App.am.loadOnDemand<TextureAtlas>("human/$spriteName.atlas")
    val animationMap = HashMap<String, Animation<TextureAtlas.AtlasRegion>>()
    var targetX = x
    var targetY = y
    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        var anim: String = if (targetX > x) {
            x += 1
            if (x > targetX) x = targetX
            "run-right"
        } else if (targetX < x) {
            x -= 1
            if (x < targetX) x = targetX
            "run-left"
        } else if (targetY > y) {
            y += 1
            if (y > targetY) y = targetY
            "run-up"
        } else if (targetY < y) {
            y -= 1
            if (y < targetY) y = targetY
            "run-down"
        } else {
            "run-down"
        }
        batch?.draw(getAnimation(anim).getKeyFrame(App.elapsedTime, true), x-32, y)
    }

    fun setTargetXY(tx: Float, ty: Float) {
        println("set target to $tx, $ty")
        println("current xy is  $x, $y")
        targetX = tx
        targetY = ty
    }

    private fun getAnimation(anim: String): Animation<TextureAtlas.AtlasRegion> {
        var animation = animationMap.get(anim)
        if (animation == null) {
            animation = Animation(1 / 15f, atlas.findRegions(anim))
            animationMap.put(anim, animation)
        }
        return animation
    }
}