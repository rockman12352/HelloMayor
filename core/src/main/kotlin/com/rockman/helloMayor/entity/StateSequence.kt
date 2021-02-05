package com.rockman.helloMayor.entity

class StateSequence(
        private var index: Int = 0,
        private val sequence: List<Entry> = listOf(Entry(State.SLEEP, 1000), Entry(State.EAT, 500), Entry(State.WORK, 1500)),
        private var remain: Int = 0
) {
    init {
        remain = sequence[index].duration
    }

    fun getCurrentState(): State {
        return getCurrentEntry().state
    }

    private fun getCurrentEntry(): Entry {
        return sequence[index]
    }

    fun consumeAndIsChanged(ms: Int): Boolean {
        remain -= ms
        if (remain <= 0) {
            index = (index + 1) % sequence.size
            remain += getCurrentEntry().duration
            return true
        }
        return false
    }

    class Entry(val state: State, val duration: Int)
}