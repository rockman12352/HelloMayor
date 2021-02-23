package com.rockman.helloMayor.actor


abstract class Facilitate(
        val type: Type
) : BaseActor() {
    val parkingPointList = mutableListOf<ParkingPoint>()

    constructor(type: Type, x: Float, y: Float) : this(type) {
        this.x = x
        this.y = y
    }

    enum class Type {
        HOUSE, RESTAURANT, PLAYGROUND, OFFICE, EMPTY
    }

    fun getCapacity(): Int {
        return parkingPointList.size
    }

    override fun toString(): String {
        return type.name
    }

    fun getAvailableParkingPoint(): ParkingPoint? {
        return parkingPointList.firstOrNull { it.occupant == null }
    }

    class ParkingPoint(val x: Float, val y: Float) {
        var occupant: Human? = null
        fun unpark() {
            occupant = null
        }

        fun park(human: Human) {
            occupant = human
        }
    }
}