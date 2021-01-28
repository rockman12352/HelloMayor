package com.rockman.helloMayor.stages

import com.badlogic.gdx.scenes.scene2d.Stage
import com.rockman.helloMayor.services.FacilitateService
import com.rockman.helloMayor.services.HumanService

class GameStage(
        val facilitateService: FacilitateService = FacilitateService(),
        val humanService: HumanService = HumanService()
) : Stage() {
    init {
        facilitateService.facilitateList.forEach { addActor(it) }
        humanService.humanList.forEach { addActor(it) }
    }

    override fun act(delta: Float) {
        super.act(delta)
    }

    override fun draw() {
        super.draw()
//        batch.begin();
//        batch.end()
    }
}