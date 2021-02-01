package com.rockman.helloMayor.util

import com.badlogic.gdx.scenes.scene2d.Actor
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sqrt

object ActorUtil {
    fun isCollide(a: Actor, b: Actor): Boolean {
        //https://stackoverflow.com/questions/20925818/algorithm-to-check-if-two-boxes-overlap
        val isProjectInX = if (a.x < b.x) {
            (a.x + a.width) >= b.x
        } else {
            (b.x + b.width) >= a.x
        }
        val isProjectInY = if (a.y < b.y) {
            (a.y + a.height) >= b.y
        } else {
            (b.y + b.height) >= a.y
        }
        return isProjectInX && isProjectInY
    }

    fun distance(a: Actor, b: Actor): Float {
        return sqrt((a.x - b.x).absoluteValue.pow(2) + (a.y - b.y).absoluteValue.pow(2))
    }
}