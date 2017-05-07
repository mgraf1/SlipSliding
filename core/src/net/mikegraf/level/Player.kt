package net.mikegraf.level

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import net.mikegraf.IRenderable
import net.mikegraf.IUpdatable
import net.mikegraf.PhysicsModel
import net.mikegraf.level.controller.LevelInput

class Player(
        private val animationIndex: AnimationIndex, private val physModel: PhysicsModel): IUpdatable, IRenderable {
    val position: Vector2
        get() = physModel.position

    val movementVector = Vector2()


    override fun update(deltaTime: Float) {
        updateMovementVector()
        physModel.applyForce(movementVector)
    }

    override fun render(batch: SpriteBatch, deltaTime: Float) {
        if (physModel.isActive) {
            val region = animationIndex.getKeyFrame(deltaTime)
            batch.begin()
            batch.draw(region, physModel.position.x, physModel.position.y)
            batch.end()
        }
    }

    private fun updateMovementVector() {
        movementVector.set(0f, 0f)
        if (LevelInput.isDown(LevelInput.WALK_UP)) movementVector.y++
        if (LevelInput.isDown(LevelInput.WALK_DOWN)) movementVector.y--
        if (LevelInput.isDown(LevelInput.WALK_LEFT)) movementVector.x--
        if (LevelInput.isDown(LevelInput.WALK_RIGHT)) movementVector.x++

        movementVector.nor()
    }

    companion object {
        val WALK_UP_ANIMATION = "walk up"
        val WALK_DOWN_ANIMATION = "walk down"
        val WALK_LEFT_ANIMATION = "walk left"
        val WALK_RIGHT_ANIMATION = "walk right"
    }
}
