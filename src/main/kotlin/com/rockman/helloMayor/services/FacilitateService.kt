package com.rockman.helloMayor.services

import com.rockman.helloMayor.actors.Facilitate

class FacilitateService(
        val facilitateList: MutableList<Facilitate> = mutableListOf()
) {
    init {
        facilitateList.add(Facilitate(Facilitate.Type.HOUSE))
    }
}