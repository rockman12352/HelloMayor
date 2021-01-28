package com.rockman.helloMayor.services

import com.rockman.helloMayor.actors.Human

class HumanService(val humanList: MutableList<Human> = mutableListOf()
) {
    init {
        humanList.add(Human())
    }
}