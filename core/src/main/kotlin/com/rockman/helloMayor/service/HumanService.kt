package com.rockman.helloMayor.service

import com.rockman.helloMayor.actor.Facilitate
import com.rockman.helloMayor.actor.Human
import com.rockman.helloMayor.entity.State
import com.rockman.helloMayor.stage.GameStage

object HumanService {
    private val stage = GameStage
    private val humanList: MutableList<Human> = mutableListOf()
    private val facilitateService = FacilitateService
    fun findNearestFacilityByState(human: Human, currentState: State): Facilitate? {
        return when (currentState) {
            State.EAT -> facilitateService.findNearestFacility(human.x, human.y, Facilitate.Type.RESTAURANT)
            State.PLAY -> facilitateService.findNearestFacility(human.x, human.y, Facilitate.Type.PLAYGROUND)
            State.SLEEP -> facilitateService.findNearestFacility(human.x, human.y, Facilitate.Type.HOUSE)
            State.WORK -> facilitateService.findNearestFacility(human.x, human.y, Facilitate.Type.OFFICE)
        }
    }

    fun pass(second: Float) {
        humanList.forEach { it.pass(second) }
    }

    private fun addHuman(human: Human) {
        humanList.add(human)
        stage.addActor(human)
    }

//    init {
//        addHuman(Human(humanService = this))
//        val s = this
//        GlobalScope.launch {
//            delay(500)
//            addHuman(Human(humanService = s))
//        }
//    }
}