package com.rockman.helloMayor.service

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.JsonReader
import com.rockman.helloMayor.actor.facilitates.Empty
import com.rockman.helloMayor.stage.GameStage

object MapService {
    lateinit var texture: Texture
    var multiplier = 1
    var x = 0f
    var y = 0f
    var width = 1000f
    var height = 1000f

    fun initStage(name: String, stage: GameStage) {
        texture = Texture(Gdx.files.internal("map/${name}.png"))
        val data = Gdx.files.internal("map/${name}.data").readString()
        val json = JsonReader().parse(data)
        multiplier = json.getInt("multiplier")
        width *= multiplier
        height *= multiplier
        val jsonPoints = json.get("points")
        var pointSize = jsonPoints.size + 1
        while (pointSize-- >= 0) {
            val p = jsonPoints.get(pointSize - 1)
            stage.addFacilitate(Empty(p.getInt(0).toFloat() * multiplier - width / 2, (height - p.getInt(1).toFloat()) * multiplier - height / 2))
        }
    }

    fun draw(batch: Batch) {
        batch.draw(texture, x - width / 2, y - height / 2, width, height)
//        points.forEach {
//            batch.draw(Human.ENDURANCE_TEXTURE, it.x, it.y, 10f, 10f)
//        }
    }
}