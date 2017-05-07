package net.mikegraf.desktop.controller

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
import net.mikegraf.level.controller.LevelInput

class KeyboardInputHandler : InputAdapter() {

    override fun keyDown(keycode: Int): Boolean {
        mapKeyToLevelInput(keycode, true)
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        mapKeyToLevelInput(keycode, false)
        return true
    }

    private fun mapKeyToLevelInput(keycode: Int, isDown: Boolean) {
        when (keycode) {
            Keys.LEFT -> LevelInput.setKey(LevelInput.WALK_LEFT, isDown)
            Keys.UP -> LevelInput.setKey(LevelInput.WALK_UP, isDown)
            Keys.RIGHT -> LevelInput.setKey(LevelInput.WALK_RIGHT, isDown)
            Keys.DOWN -> LevelInput.setKey(LevelInput.WALK_DOWN, isDown)
        }
    }
}
