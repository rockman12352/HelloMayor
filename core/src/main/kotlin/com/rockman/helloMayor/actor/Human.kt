package com.rockman.helloMayor.actor

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import com.rockman.helloMayor.App
import com.rockman.helloMayor.entity.GameController
import com.rockman.helloMayor.entity.HumanList.Companion
import com.rockman.helloMayor.entity.HumanList.Companion.BEHAVIOR_CONSUMING
import com.rockman.helloMayor.entity.HumanList.Companion.BEHAVIOR_MOVING
import com.rockman.helloMayor.entity.HumanList.Companion.BEHAVIOR_QUEUING
import com.rockman.helloMayor.entity.State
import com.rockman.helloMayor.entity.StateSequence
import com.rockman.helloMayor.service.HumanFacilitateService
import com.rockman.helloMayor.util.ActorUtil
import ktx.assets.getValue
import ktx.assets.loadOnDemand

class Human(
        private val state: StateSequence = StateSequence(),
        private val speed: Float = 150f,
        private val endurance: Int = 10
) : BaseActor() {
    var parkingPoint: Facilitate.ParkingPoint? = null
    var behavior = BEHAVIOR_MOVING

    companion object {
        val TEXTURE by App.am.loadOnDemand<Texture>("human.png", App.textureParameter)
    }

    constructor(x: Float, y: Float) : this() {
        this.x = x
        this.y = y
    }

    private val humanFacilitateService = HumanFacilitateService
    private val gameController = GameController
    var target: Facilitate? = null
        set(value) {
            field = value
            if (value != null) {
                moveToTarget(value)
            }
        }

    init {
        width = 100f
        height = 100f
        internalX = 50f
        internalY = 50f
        drawHeight = 100f
        drawWidth = 100f
        texture = TEXTURE
    }

    /**
     * return priority for next round
     */
    fun pass(second: Float): Int {
        var priority = 0

        if (target == null) {
            target = gameController.findNearestFacilityByState(this)
        }
        if (target != null) {
            if (parkingPoint == null) {
                if (ActorUtil.isCollide(this, target!!)) {
                    clearActions()
                    if (parkToTarget()) {
                        priority = 1
                    } else {
                        behavior = BEHAVIOR_QUEUING
                    }
                }
            } else {
                if (!state.consume((second * 1000).toInt())) {
                    unpark()
                }
            }

        }
        return priority
    }

    fun unpark() {
        parkingPoint?.unpark()
        parkingPoint = null
        target = null
    }

    fun getCurrentState(): State {
        return state.getCurrentState()
    }

    //align by internal XY
    private fun moveToTarget(target: BaseActor) {
        var action = MoveToAction()
        action.setPosition(target.x + target.internalX - internalX, target.y + target.internalY - internalY)
        action.duration = ActorUtil.distance(this, target) / speed
        addAction(action)
        behavior = BEHAVIOR_MOVING
    }

    private fun parkToTarget(): Boolean {
        val pp = target!!.availableParkingPoint()
        if (pp == null) {
            return false
        } else {
            pp.park(this)
            parkingPoint = pp
            setInternalPosition(target!!.x + pp.x, target!!.y + pp.y)
            behavior = BEHAVIOR_CONSUMING
            return true
        }
    }
}