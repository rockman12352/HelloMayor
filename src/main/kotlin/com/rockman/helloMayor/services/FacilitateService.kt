package com.rockman.helloMayor.services

import com.rockman.helloMayor.actors.Facilitate

class FacilitateService(
        val facilitateList: MutableList<Facilitate> = mutableListOf()
) {
    init {
        facilitateList.add(Facilitate(Facilitate.Type.HOUSE))
        var office = Facilitate(Facilitate.Type.OFFICE)
        office.x = 100f
        office.y = 100f
        facilitateList.add(office)
    }
}