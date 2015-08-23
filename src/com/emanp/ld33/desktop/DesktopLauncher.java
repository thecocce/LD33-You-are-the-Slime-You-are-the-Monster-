package com.emanp.ld33.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.emanp.ld33.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=1024;
		config.height=576;
		config.foregroundFPS=0;
		config.title="You are the Slime";
		config.allowSoftwareMode=true;
		config.vSyncEnabled=false;
		config.addIcon("res/Icons/Iconx128.png", FileType.Internal);
		config.addIcon("res/Icons/Iconx32.png", FileType.Internal);
		config.addIcon("res/Icons/Iconx16.png", FileType.Internal);
		new LwjglApplication(new Game(), config);
	}
}
