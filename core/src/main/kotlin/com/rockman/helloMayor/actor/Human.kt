package com.rockman.helloMayor.actor

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import com.rockman.helloMayor.App
import com.rockman.helloMayor.entity.GameController
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
        private var endurance: Int = ENDURANCE
) : BaseActor() {
    var parkingPoint: Facilitate.ParkingPoint? = null
    var behavior = BEHAVIOR_MOVING

    private val humanFacilitateService = HumanFacilitateService
    private val gameController = GameController
    var target: Facilitate? = null
        set(value) {
            field = value
            if (value != null) {
                moveToTarget(value)
            }
        }

    companion object {
        val TEXTURE by App.am.loadOnDemand<Texture>("human.png", App.textureParameter)
        const val ENDURANCE = 1000
    }

    constructor(x: Float, y: Float) : this() {
        this.x = x
        this.y = y
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
                        setBehavior(BEHAVIOR_QUEUING, (second * 1000).toInt())
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
        setBehavior(BEHAVIOR_MOVING, 0)
    }

    private fun parkToTarget(): Boolean {
        val pp = target!!.availableParkingPoint()
        if (pp == null) {
            return false
        } else {
            pp.park(this)
            parkingPoint = pp
            setInternalPosition(target!!.x + pp.x, target!!.y + pp.y)
            setBehavior(BEHAVIOR_CONSUMING, 0)
            return true
        }
    }

    private fun setBehavior(behavior: Int, ms: Int) {
        this.behavior = behavior
        if (behavior == BEHAVIOR_QUEUING) {
            endurance -= ms
            if (endurance <= 0) {
                println("boom! ${this.toString()}")
            }
        } else {
            endurance = ENDURANCE
        }
    }
}