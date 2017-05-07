package net.mikegraf

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body

/**
 * Created by Graf on 4/9/2017.
 */
class PhysicsModel(private val body: Body, private val renderWidth: Float, private val renderHeight: Float) {
    private val _position: Vector2 = Vector2()

    val position: Vector2
        get() {
            SlipSliding.getRenderCoords(_position, body.position, renderWidth, renderHeight)
            return _position
        }
    val isActive: Boolean
        get() = body.isActive

    fun applyForce(force: Vector2) {
        body.applyForceToCenter(force, true)
    }
}

