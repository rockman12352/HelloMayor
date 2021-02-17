package com.rockman.helloMayor.stage

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.rockman.helloMayor.actor.Facilitate
import com.rockman.helloMayor.actor.Human
import com.rockman.helloMayor.actor.facilitates.Empty
import com.rockman.helloMayor.entity.GameController
import com.rockman.helloMayor.entity.HumanList
import com.rockman.helloMayor.entity.Map
import com.rockman.helloMayor.entity.State
import com.rockman.helloMayor.util.FrameRate
import kotlin.math.absoluteValue
import kotlin.math.pow

object GameStage : Stage() {
    private val gameController = GameController
    private var active = true
    private val frameRate = FrameRate()
    private var facilitateList: MutableList<Facilitate> = mutableListOf()
    private var humanList = HumanList()
    lateinit var map: Map

    init {
        isDebugAll = true
        map = Map("sz")
        map.points.forEach { addActor(it) }
//        addFacilitate(House(70f, 70f))
//        addFacilitate(Office(300f, 300f))
//        addFacilitate(Restaurant(400f, 0f))

        var tp = -150f
        var count = 100
        while (count-- > 0) {
            addActor(Human(tp, tp))
            tp -= 50f
        }
    }

    fun findNearestFacilityByState(human: Human): Facilitate? {
        return when (human.getCurrentState()) {
            State.EAT -> findNearestFacility(human.x, human.y, Facilitate.Type.RESTAURANT)
            State.PLAY -> findNearestFacility(human.x, human.y, Facilitate.Type.PLAYGROUND)
            State.SLEEP -> findNearestFacility(human.x, human.y, Facilitate.Type.HOUSE)
            State.WORK -> findNearestFacility(human.x, human.y, Facilitate.Type.OFFICE)
        }
    }

    private fun findNearestFacility(x: Float, y: Float, type: Facilitate.Type): Facilitate? {
        val facilitates = facilitateList.filter { it.type == type }
        return if (facilitates.size > 1) {
            facilitates.minByOrNull { (it.x - x).absoluteValue.pow(2) + (it.y - y).absoluteValue.pow(2) }
        } else {
            facilitates.firstOrNull()
        }
    }

    override fun act(delta: Float) {
        if (active) {
            super.act(delta)
            humanList.ejectAll().forEach {
                it.pass(delta)
                humanList.add(it)
            }
        }
        frameRate.update()
        frameRate.render()
    }

    override fun draw() {
        super.draw()
        batch.projectionMatrix = camera.combined
        batch.begin()
        map.draw(batch!!)
        if (map.selectedPoint is Empty) {
            displayFacilitateOption(batch)
        }
        batch.end()
    }

    private fun displayFacilitateOption(batch: Batch) {
        //batch.draw()
    }

    override fun keyDown(keyCode: Int): Boolean {
        if (keyCode == Input.Keys.SPACE) {
            active = !active
        }
        return super.keyDown(keyCode)
    }


//    fun click(screenX: Int, screenY: Int) {
//        val worldPosition = camera.unproject(Vector3(screenX.toFloat(), screenY.toFloat(), 0f))
//        hit(worldPosition.x, worldPosition.y, true)
//    }

}