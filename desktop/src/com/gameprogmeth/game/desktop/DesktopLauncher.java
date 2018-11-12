package com.gameprogmeth.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gameprogmeth.game.GameProgMeth;

public class DesktopLauncher {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 60;
		config.width = GameProgMeth.WIDTH;
		config.height = GameProgMeth.HEIGHT;
		config.fullscreen = true;
		config.forceExit = true;
		new LwjglApplication(new GameProgMeth(), config);
	}
}
