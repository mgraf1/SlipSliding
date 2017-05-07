package net.mikegraf.level.controller

class LevelInput {
    companion object {
        val NUM_KEYS = 5
        val WALK_LEFT = 0
        val WALK_UP = 1
        val WALK_RIGHT = 2
        val WALK_DOWN = 3

        private val keys = BooleanArray(NUM_KEYS)
        private val prevKeys = BooleanArray(NUM_KEYS)

        fun update() {
            for (i in 0..NUM_KEYS - 1) {
                prevKeys[i] = keys[i]
            }
        }

        fun setKey(i: Int, b: Boolean) {
            keys[i] = b
        }

        fun isDown(i: Int): Boolean {
            return keys[i]
        }

        fun isPressed(i: Int): Boolean {
            return keys[i] && !prevKeys[i]
        }
    }
}
