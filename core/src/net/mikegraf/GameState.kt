package net.mikegraf

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable

/**
 * Created by Graf on 4/2/2017.
 */
abstract class GameState(protected val assetManager: AssetManager): Disposable, IUpdatable, IRenderable {

    override fun dispose() {
        assetManager.dispose()
    }

    abstract fun loadAssets()

    abstract fun createAssets()

    override abstract fun render(batch: SpriteBatch, deltaTime: Float)

    override abstract fun update(deltaTime: Float)

}
