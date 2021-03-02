package com.rockman.helloMayor.actor

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.rockman.helloMayor.App
import com.rockman.helloMayor.entity.HumanList.Companion.BEHAVIOR_CONSUMING
import com.rockman.helloMayor.entity.HumanList.Companion.BEHAVIOR_MOVING
import com.rockman.helloMayor.entity.HumanList.Companion.BEHAVIOR_QUEUING
import com.rockman.helloMayor.entity.State
import com.rockman.helloMayor.entity.StateSequence
import com.rockman.helloMayor.stage.GameStage
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

    var target: Facilitate? = null
        set(value) {
            field = value
            clearActions()
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
    fun pass(second: Float) {
        if (target == null) {
            target = (stage as GameStage).findNearestFacilityByState(this)
        }
        if (target != null) {
            if (parkingPoint == null) {
                if (ActorUtil.isCollide(this, target!!)) {
                    clearActions()
                    if (!parkToTarget()) {
                        setBehavior(BEHAVIOR_QUEUING, (second * 1000).toInt())
                    }
                }
            } else {
                if (behavior == BEHAVIOR_CONSUMING && state.consumeAndIsChanged((second * 1000).toInt())) {
                    unpark()
                }
            }
        }
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
        moveTo(target.x + target.internalX - internalX, target.y + target.internalY - internalY, null)
    }

    //align by internal XY
    private fun moveToTarget(target: BaseActor, cb: () -> Unit) {
        moveTo(target.x + target.internalX - internalX, target.y + target.internalY - internalY, cb)
    }

    private fun moveTo(x: Float, y: Float) {
        moveTo(x, y, null)
    }

    private fun moveTo(x: Float, y: Float, onDone: (() -> Unit)?) {
        val action = MoveToAction()
        action.setPosition(x, y)
        action.duration = Vector2.dst(x, y, this.x, this.y) / speed
        if (onDone != null) {
            val onDoneAction = RunnableAction()
            onDoneAction.setRunnable { onDone() }
            addActions(action, onDoneAction)
        } else {
            addAction(action)
        }
        setBehavior(BEHAVIOR_MOVING, 0)
    }

    private fun addActions(vararg actions: Action) {
        when (actions.size) {
            1 -> addAction(SequenceAction(actions[0]))
            2 -> addAction(SequenceAction(actions[0], actions[1]))
            3 -> addAction(SequenceAction(actions[0], actions[1], actions[2]))
            else -> throw RuntimeException("too many actions")
        }
    }


    private fun parkToTarget(): Boolean {
        var canPark = false
        val pp = target!!.getAvailableParkingPoint()
        if (pp != null) {
            pp.park(this)
            parkingPoint = pp
            var position = worldToInternalPosition(target!!.x + pp.x, target!!.y + pp.y)
            moveTo(position.x, position.y) {
                setBehavior(BEHAVIOR_CONSUMING, 0)
            }
            canPark = true
        }
        return canPark
    }

    private fun setBehavior(behavior: Int, ms: Int) {
        this.behavior = behavior
        if (behavior != BEHAVIOR_CONSUMING) {
            endurance -= ms
            if (endurance <= 0) {
                //println("boom! ${this.toString()}")
                endurance = 0
            }
        } else {
            endurance = ENDURANCE
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        if (endurance < 1000) {
            batch?.draw(ENDURANCE_TEXTURE, x + internalX - drawWidth / 2, y + internalY - drawHeight, drawWidth * (1 - endurance.toFloat() / 1000f), 20f)
        }
    }
}