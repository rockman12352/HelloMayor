package com.rockman.helloMayor.stage

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import com.rockman.helloMayor.actor.Facilitate
import com.rockman.helloMayor.actor.FacilitateButton
import com.rockman.helloMayor.actor.Human
import com.rockman.helloMayor.actor.facilitates.Empty
import com.rockman.helloMayor.entity.*
import com.rockman.helloMayor.service.MapService
import com.rockman.helloMayor.util.FrameRate
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.math.pow

object GameStage : Stage() {
    private val gameController = GameController
    private var active = true
    private val frameRate = FrameRate()
    private var facilitateList: MutableList<Facilitate> = mutableListOf()
    private var humanList = HumanList()
    private var facilitateButtonList: MutableList<FacilitateButton> = mutableListOf()
    private var menuBatch = SpriteBatch()
    var selectedPoint: Facilitate? = null

    init {
        initFacilitateButtons()
        isDebugAll = true
        MapService.initStage("sz", this)
//        addFacilitate(House(70f, 70f))
//        addFacilitate(Office(300f, 300f))
//        addFacilitate(Restaurant(400f, 0f))
        resize(Gdx.graphics.width, Gdx.graphics.height)
        var tp = -150f
        var count = 10
        while (count-- > 0) {
            addHuman(Human(tp, tp))
            tp -= 50f
        }
    }

    private fun addHuman(human: Human) {
        humanList.add(human)
        addActor(human)
    }

    fun addFacilitate(facilitate: Facilitate) {
        facilitateList.add(facilitate)
        addActor(facilitate)
        humanList.notConsuming().filter { it.parkingPoint == null }.forEach { it.target = null }
    }

    private fun replaceFacilitate(o: Facilitate, n: Facilitate) {
        var internalPosition = o.getInternalPosition()
        n.setInternalPosition(internalPosition.x, internalPosition.y)

        o.remove()
        addFacilitate(n)
    }

    private fun initFacilitateButtons() {
        facilitateButtonList.add(FacilitateButton(Facilitate.Type.RESTAURANT))
        facilitateButtonList.add(FacilitateButton(Facilitate.Type.PLAYGROUND))
        facilitateButtonList.add(FacilitateButton(Facilitate.Type.HOUSE))
        facilitateButtonList.add(FacilitateButton(Facilitate.Type.OFFICE))
        facilitateButtonList.forEach { it.isVisible = true }
    }

    fun findNearestFacilityByState(human: Human): Facilitate? {
        return when (human.getCurrentState()) {
            State.EAT -> findNearestFacility(human.x, human.y, Facilitate.Type.RESTAURANT)
            State.PLAY -> findNearestFacility(human.x, human.y, Facilitate.Type.PLAYGROUND)
            State.SLEEP -> findNearestFacility(human.x, human.y, Facilitate.Type.HOUSE)
            State.WORK -> findNearestFacility(human.x, human.y, Facilitate.Type.OFFICE)
        }
    }

    private fun findNearestFacility(x: Float, y: Float, type: Facilitate.Type): Facilitate? {
        val facilitates = facilitateList.filter { it.type == type }
        return if (facilitates.size > 1) {
            facilitates.minByOrNull { (it.x - x).absoluteValue.pow(2) + (it.y - y).absoluteValue.pow(2) }
        } else {
            facilitates.firstOrNull()
        }
    }

    override fun act(delta: Float) {
        if (active) {
            super.act(delta)
            humanList.ejectAll().forEach {
                it.pass(delta)
                humanList.add(it)
            }
        }
        frameRate.update()
        frameRate.render()
    }

    override fun draw() {
        super.draw()
        batch.projectionMatrix = camera.combined
        batch.begin()
        MapService.draw(batch!!)
        batch.end()

        if (selectedPoint is Empty) {
            menuBatch.begin()
            drawMenu(menuBatch)
            menuBatch.end()
        }

    }

    private fun drawMenu(menuBatch: SpriteBatch) {
        facilitateButtonList.forEach { it.draw(menuBatch, 0f) }
    }

    override fun keyDown(keyCode: Int): Boolean {
        if (keyCode == Input.Keys.SPACE) {
            active = !active
        }
        return super.keyDown(keyCode)
    }

    fun resize(width: Int, height: Int) {
        var buttonHeight = height.toFloat() / 5f
        var buttonWidth = width.toFloat() * 0.8f / facilitateButtonList.size
        var buttonSize = min(buttonHeight, buttonWidth)
        facilitateButtonList.forEachIndexed { idx, btn ->
            btn.width = buttonSize
            btn.height = buttonSize
            btn.x = buttonSize * idx + buttonSize / 10f
            btn.y = height.toFloat() / 10f
        }
    }

    override fun dispose() {
        super.dispose()
        menuBatch.dispose()
    }

    fun click(screenX: Int, screenY: Int) {
        if (selectedPoint != null) {
            var clicked = facilitateButtonList.firstOrNull { it.click(Vector3(screenX.toFloat(), Gdx.graphics.height - screenY.toFloat(), 0f)) }
            if (clicked != null) {
                replaceFacilitate(selectedPoint!!, clicked.constructFunction.call(0f, 0f))
                selectedPoint = null
            }

        }
    }


//    fun click(screenX: Int, screenY: Int) {
//        val worldPosition = camera.unproject(Vector3(screenX.toFloat(), screenY.toFloat(), 0f))
//        hit(worldPosition.x, worldPosition.y, true)
//    }

}