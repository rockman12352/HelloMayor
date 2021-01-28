package com.rockman.helloMayor.entities

import com.rockman.helloMayor.utils.Constants

class StateSequence(
        private var index: Int = 0,
        private val sequence: List<Entry> = listOf(Entry(Constants.States.SLEEP, 1000), Entry(Constants.States.EAT, 500)),
        private var remain: Int = 0
) {
    init {
        remain = sequence[index].duration
    }

    private fun getCurrentState(): Entry {
        return sequence[index]
    }

    fun consume(time: Int) {
        remain -= time
        if (remain <= 0) {
            index = (index + 1) % sequence.size
            remain = getCurrentState().duration
        }
    }

    class Entry(val state: Constants.States, val duration: Int)
}