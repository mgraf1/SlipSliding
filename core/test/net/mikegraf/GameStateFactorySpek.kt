package net.mikegraf

import com.badlogic.gdx.assets.AssetManager
import com.natpryce.hamkrest.assertion.assertThat
import com.nhaarman.mockito_kotlin.isA
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import com.nhaarman.mockito_kotlin.mock
import org.junit.gen5.api.Assertions

/**
 * Created by Graf on 4/2/2017.
 */
class GameStateFactorySpek: Spek({
    var factory = GameStateFactory()
    var assetManager: AssetManager = mock()

    describe("the game state factory") {

        it("should create a demo state") {
            val state = factory.createState(StateType.DEMO, assetManager)
            Assertions.assertTrue(state is DemoState)
        }
    }
})

