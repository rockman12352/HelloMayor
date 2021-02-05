package com.rockman.helloMayor.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.JsonReader
import com.rockman.helloMayor.App
import com.rockman.helloMayor.actor.Human
import ktx.assets.getValue
import ktx.assets.loadOnDemand
import java.io.File

class Map(name: String) {
    private val texture by App.am.loadOnDemand<Texture>("map/${name}.png", App.textureParameter)
    var multiplier = 1
    var points = mutableListOf<Vector2>()
    var x = 0f
    var y = 0f
    var width = 1000f
    var height = 1000f

    init {
        val data = Gdx.files.internal("map/${name}.data").readString()
        val json = JsonReader().parse(data)
        multiplier = json.getInt("multiplier")
        width *= multiplier
        height *= multiplier
        val jsonPoints = json.get("points")
        var pointSize = jsonPoints.size + 1
        while (pointSize-- >= 0) {
            points.add(Vector2(jsonPoints.get(pointSize - 1).getInt(0).toFloat() * multiplier - width / 2, (1000f - jsonPoints.get(pointSize - 1).getInt(1).toFloat()) * multiplier - height / 2))
        }
    }

    fun draw(batch: Batch) {
        batch.draw(texture, x - width / 2, y - height / 2, width, height)
        points.forEach {
            batch.draw(Human.ENDURANCE_TEXTURE, it.x, it.y, 10f, 10f)
        }
    }
}