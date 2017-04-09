package net.mikegraf

import com.badlogic.gdx.graphics.g2d.SpriteBatch

/**
 * Created by Graf on 4/9/2017.
 */
interface IRenderable {
    fun render(batch: SpriteBatch, deltaTime: Float)
}
