package net.mikegraf.level

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import net.mikegraf.IRenderable
import net.mikegraf.IUpdatable
import net.mikegraf.PhysicsModel

/**
 * Created by Graf on 4/5/2017.
 */
class Player(private val animationIndex: AnimationIndex, private val physModel: PhysicsModel): IUpdatable, IRenderable {
    val position: Vector2
        get() = physModel.position


    override fun update(deltaTime: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun render(batch: SpriteBatch, deltaTime: Float) {
        if (physModel.isActive) {
            val region = animationIndex.getKeyFrame(deltaTime)
            batch.begin()
            batch.draw(region, physModel.position.x, physModel.position.y)
            batch.end()
        }
    }

    companion object {
        val WALK_UP_ANIMATION = "walk up"
        val WALK_DOWN_ANIMATION = "walk down"
        val WALK_LEFT_ANIMATION = "walk left"
        val WALK_RIGHT_ANIMATION = "walk right"
    }
}
