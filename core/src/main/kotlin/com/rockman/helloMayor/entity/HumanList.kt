package com.rockman.helloMayor.entity

import com.rockman.helloMayor.actor.Facilitate
import com.rockman.helloMayor.actor.Human

class HumanList {
    var consuming: MutableList<Human> = mutableListOf()
    var queueing: MutableList<Human> = mutableListOf()
    var moving: MutableList<Human> = mutableListOf()
    var tempQueuing: MutableList<Human> = mutableListOf()
    fun add(human: Human) {
        when (human.behavior) {
            BEHAVIOR_CONSUMING -> consuming.add(human)
            BEHAVIOR_QUEUING -> queueing.add(human)
            BEHAVIOR_MOVING -> moving.add(human)
        }
    }

    fun ejectAll(): List<Human> {
       // tempQueuing.clear()
       // tempQueuing.addAll(queueing)

        val list = mutableListOf<Human>()
        consuming.forEach { list.add(it) }
        consuming.clear()
        queueing.forEach { list.add(it) }
        queueing.clear()
        moving.forEach { list.add(it) }
        moving.clear()
        return list
    }

    fun notConsuming(): List<Human> {
        return queueing + moving
    }

    fun queuingFor(facilitate: Facilitate): Int {
        return tempQueuing.filter { it.target == facilitate }.size
    }

    companion object {
        const val BEHAVIOR_CONSUMING = 1
        const val BEHAVIOR_QUEUING = 2
        const val BEHAVIOR_MOVING = 3
    }
}