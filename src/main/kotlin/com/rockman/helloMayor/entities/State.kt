package com.rockman.helloMayor.entities

import com.rockman.helloMayor.utils.Constants

class State {
    private var index = 0
    private var remain = 0
    private val sequence = listOf(
            Entry(Constants.States.SLEEP, 1000),
            Entry(Constants.States.EAT, 500))

    init {
        remain = sequence[index].duration
    }

    fun getCurrentState(): Entry {
        return sequence[index]
    }

    fun consume(time: Int) {
        remain -= time
        if (remain <= 0) {
            index = (index + 1) % sequence.size
            remain = getCurrentState().duration
            println("pass" + time + " changed to " + getCurrentState().state.name)
        }
    }
}

class Entry(val state: Constants.States, val duration: Int)
