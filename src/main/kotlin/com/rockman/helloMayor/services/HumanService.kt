package com.rockman.helloMayor.services

import com.rockman.helloMayor.actors.Facilitate
import com.rockman.helloMayor.actors.Human
import com.rockman.helloMayor.entities.State

class HumanService(val humanList: MutableList<Human> = mutableListOf(),
                   val facilitateService: FacilitateService
) {
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

    init {
        humanList.add(Human(humanService = this))
    }
}