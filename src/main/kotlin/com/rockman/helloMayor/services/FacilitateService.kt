package com.rockman.helloMayor.services

import com.rockman.helloMayor.actors.Facilitate
import com.rockman.helloMayor.actors.facilitates.House
import com.rockman.helloMayor.stages.GameStage
import kotlin.math.absoluteValue
import kotlin.math.pow

object FacilitateService {
    private val stage = GameStage
    private val facilitateList: MutableList<Facilitate> = mutableListOf()

    fun findNearestFacility(x: Float, y: Float, type: Facilitate.Type): Facilitate? {
        val facilitates = facilitateList.filter { it.type == type }
        return if (facilitates.size > 1) {
            facilitates.minBy { (it.x - x).absoluteValue.pow(2) + (it.y - y).absoluteValue.pow(2) }
        } else {
            facilitates.firstOrNull()
        }
    }

    private fun addFacilitate(facilitate: Facilitate) {
        facilitateList.add(facilitate)
        stage.addActor(facilitate)
    }

    init {
        addFacilitate(Facilitate(Facilitate.Type.HOUSE))
        addFacilitate(House(70f,70f))
        addFacilitate(Facilitate(Facilitate.Type.OFFICE, 100f, 70f))
        addFacilitate(Facilitate(Facilitate.Type.RESTAURANT, 150f, 70f))
    }
}