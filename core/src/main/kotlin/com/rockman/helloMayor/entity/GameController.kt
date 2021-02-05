package com.rockman.helloMayor.entity

import com.rockman.helloMayor.actor.Facilitate
import com.rockman.helloMayor.actor.Human
import com.rockman.helloMayor.actor.facilitates.House
import com.rockman.helloMayor.actor.facilitates.Office
import com.rockman.helloMayor.actor.facilitates.Restaurant
import com.rockman.helloMayor.stage.GameStage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.pow

object GameController {
    private val stage = GameStage
    private var facilitateList: MutableList<Facilitate> = mutableListOf()
    private var humanList = HumanList()

    private fun findNearestFacility(x: Float, y: Float, type: Facilitate.Type): Facilitate? {
        val facilitates = facilitateList.filter { it.type == type }
        return if (facilitates.size > 1) {
            facilitates.minByOrNull { (it.x - x).absoluteValue.pow(2) + (it.y - y).absoluteValue.pow(2) }
        } else {
            facilitates.firstOrNull()
        }
    }

    init {
        addFacilitate(House(70f, 70f))
        addFacilitate(Office(300f, 300f))
        addFacilitate(Restaurant(400f, 0f))

        addHuman(Human(-50f, -50f))
        GlobalScope.launch {
            var count = 100
            while (count-- > 0) {
                delay(300)
                addHuman(Human(-150f, -150f))
            }
        }
    }

    private fun addFacilitate(facilitate: Facilitate) {
        facilitateList.add(facilitate)
        stage.addActor(facilitate)
    }

    private fun addHuman(human: Human) {
        humanList.add(human)
        stage.addActor(human)
    }

    fun findNearestFacilityByState(human: Human): Facilitate? {
        return when (human.getCurrentState()) {
            State.EAT -> findNearestFacility(human.x, human.y, Facilitate.Type.RESTAURANT)
            State.PLAY -> findNearestFacility(human.x, human.y, Facilitate.Type.PLAYGROUND)
            State.SLEEP -> findNearestFacility(human.x, human.y, Facilitate.Type.HOUSE)
            State.WORK -> findNearestFacility(human.x, human.y, Facilitate.Type.OFFICE)
        }
    }

    fun pass(second: Float) {
        humanList.ejectAll().forEach {
            it.pass(second)
            humanList.add(it)
        }
    }
}