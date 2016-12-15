package com.mygdx.arkaxoid.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.arkaxoid.Arkaxoid;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Dungeonoid";
		config.width = 480;
		config.height = 780;
		new LwjglApplication(new Arkaxoid(), config);
	}
}
