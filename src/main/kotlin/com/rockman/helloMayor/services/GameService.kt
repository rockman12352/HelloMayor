package com.rockman.helloMayor.services

import com.rockman.helloMayor.actors.Facilitate
import com.rockman.helloMayor.actors.Human
import com.rockman.helloMayor.actors.facilitates.House
import com.rockman.helloMayor.entities.State
import com.rockman.helloMayor.stages.GameStage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.pow

object GameService {
    private val stage = GameStage
    private val facilitateList: MutableList<Facilitate> = mutableListOf()
    private val humanList: MutableList<Human> = mutableListOf()

    private fun findNearestFacility(x: Float, y: Float, type: Facilitate.Type): Facilitate? {
        val facilitates = facilitateList.filter { it.type == type }
        return if (facilitates.size > 1) {
            facilitates.minBy { (it.x - x).absoluteValue.pow(2) + (it.y - y).absoluteValue.pow(2) }
        } else {
            facilitates.firstOrNull()
        }
    }

    init {
        addFacilitate(Facilitate(Facilitate.Type.HOUSE))
        addFacilitate(House(70f, 70f))
        addFacilitate(Facilitate(Facilitate.Type.OFFICE, 100f, 70f))
        addFacilitate(Facilitate(Facilitate.Type.RESTAURANT, 150f, 70f))



        addHuman(Human())
        GlobalScope.launch {
            delay(500)
            addHuman(Human())
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


    fun findNearestFacilityByState(human: Human, currentState: State): Facilitate? {
        return when (currentState) {
            State.EAT -> findNearestFacility(human.x, human.y, Facilitate.Type.RESTAURANT)
            State.PLAY -> findNearestFacility(human.x, human.y, Facilitate.Type.PLAYGROUND)
            State.SLEEP -> findNearestFacility(human.x, human.y, Facilitate.Type.HOUSE)
            State.WORK -> findNearestFacility(human.x, human.y, Facilitate.Type.OFFICE)
        }
    }

    fun pass(second: Float) {
        humanList.forEach { it.pass(second) }
    }
}