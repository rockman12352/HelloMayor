package com.rockman.helloMayor.actor

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import com.rockman.helloMayor.App
import com.rockman.helloMayor.entity.StateSequence
import com.rockman.helloMayor.service.GameService
import com.rockman.helloMayor.util.ActorUtil
import ktx.assets.getValue
import ktx.assets.loadOnDemand

class Human(
        private val state: StateSequence = StateSequence(),
        private val speed: Float = 80f,
        private val endurance: Int = 10
) : Actor() {
    private val gameService = GameService
    var target: Facilitate? = null
    val texture by App.am.loadOnDemand<Texture>("human.png", App.textureParameter)
    var isMoving = false

    init {
        x = -100f
        y = -100f
        width = 50f
        height = 50f
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        batch?.draw(texture, x, y)
    }

    fun pass(second: Float) {
        if (target == null) {
            target = gameService.findNearestFacilityByState(this, state.getCurrentState())
        } else {
            if (ActorUtil.isCollide(this, target!!)) {
                setPosition(target!!.x, target!!.y)
                if (isMoving) {
                    clearActions()
                    isMoving = false
                }
                if (!state.consume((second * 1000).toInt())) {
                    target = null
                }
            } else {
                if (!isMoving) {
                    moveToTarget(target!!)
                }
            }
        }
    }

    private fun moveToTarget(target: Actor) {
        var action = MoveToAction()
        action.setPosition(target.x, target.y)
        action.duration = ActorUtil.distance(this, target) / speed
        addAction(action)
        isMoving = true
    }
}