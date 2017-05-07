package net.mikegraf.desktop;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.mikegraf.SlipSliding;
import net.mikegraf.desktop.controller.KeyboardInputHandler;

public class DesktopLauncher {
	public static void main (String[] arg) {
		InputAdapter inputProcessor = new KeyboardInputHandler();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new SlipSliding(inputProcessor), config);
	}
}
