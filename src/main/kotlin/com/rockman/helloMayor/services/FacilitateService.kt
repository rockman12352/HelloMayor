package com.rockman.helloMayor.services

import com.rockman.helloMayor.actors.Facilitate
import kotlin.math.absoluteValue
import kotlin.math.pow

class FacilitateService(
        val facilitateList: MutableList<Facilitate> = mutableListOf()
) {
    fun findNearestFacility(x: Float, y: Float, type: Facilitate.Type): Facilitate? {
        val facilitates = facilitateList.filter { it.type == type }
        return if (facilitates.size > 1) {
            facilitates.minBy { (it.x - x).absoluteValue.pow(2) + (it.y - y).absoluteValue.pow(2) }
        } else {
            facilitates.firstOrNull()
        }
    }

    init {
        facilitateList.add(Facilitate(Facilitate.Type.HOUSE))
        var office = Facilitate(Facilitate.Type.OFFICE)
        office.x = 100f
        office.y = 100f
        facilitateList.add(office)

        var restaurant = Facilitate(Facilitate.Type.RESTAURANT)
        restaurant.x = 150f
        restaurant.y = 150f
        facilitateList.add(restaurant)
    }
}