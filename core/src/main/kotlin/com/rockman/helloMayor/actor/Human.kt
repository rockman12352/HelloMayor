package com.rockman.helloMayor.actor

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
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
    private var target: Facilitate? = null
        set(value) {
            field = value
            if (value != null) {
                moveToTarget(value)
            }
        }

    companion object {
        val TEXTURE by App.am.loadOnDemand<Texture>("human.png", App.textureParameter)
        val ENDURANCE_TEXTURE = Texture(Pixmap(1, 1, Pixmap.Format.RGB888))
        const val ENDURANCE = 2000
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

    private fun unpark() {
        parkingPoint?.unpark()
        parkingPoint = null
        target = null
    }

    fun getCurrentState(): State {
        return state.getCurrentState()
    }

    //align by internal XY
    private fun moveToTarget(target: BaseActor) {
        val action = MoveToAction()
        action.setPosition(target.x + target.internalX - internalX, target.y + target.internalY - internalY)
        action.duration = ActorUtil.distance(this, target) / speed
        addAction(action)
        setBehavior(BEHAVIOR_MOVING, 0)
    }

    private fun parkToTarget(): Boolean {
        var parked = false
        val pp = target!!.getAvailableParkingPoint()
        if (pp != null) {
            pp.park(this)
            parkingPoint = pp
            setInternalPosition(target!!.x + pp.x, target!!.y + pp.y)
            setBehavior(BEHAVIOR_CONSUMING, 0)
            parked = true
        }
        return parked
    }

    private fun setBehavior(behavior: Int, ms: Int) {
        this.behavior = behavior
        if (behavior == BEHAVIOR_QUEUING) {
            endurance -= ms
            if (endurance <= 0) {
                println("boom! ${this.toString()}")
                endurance = 0
            }
        } else {
            endurance = ENDURANCE
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        if (endurance < 1000) {
            batch?.draw(ENDURANCE_TEXTURE, x + internalX - drawWidth / 2, y + internalY - drawHeight, drawWidth * endurance.toFloat() / 1000f, 20f)
        }
    }
}