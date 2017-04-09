package net.mikegraf.level

import java.util.HashMap

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion

class AnimationIndex() {

    private var index: HashMap<String, Animation<TextureRegion>> = HashMap()
    private var currentAnimationName: String = DEFAULT_ANIMATION

    fun add(name: String, animation: Animation<TextureRegion>) {
        index.put(name, animation)
        currentAnimationName = name
    }

    fun getKeyFrame(totalTime: Float): TextureRegion {
        return index[currentAnimationName]?.getKeyFrame(totalTime, true)
                ?: throw Exception("Animation $currentAnimationName doesn't exist")
    }

    fun setCurrentAnimation(name: String) {
        currentAnimationName = name
    }

    companion object {
        val DEFAULT_ANIMATION = "default"
    }
}
