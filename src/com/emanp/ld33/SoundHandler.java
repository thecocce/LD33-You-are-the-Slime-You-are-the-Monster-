package com.emanp.ld33;

import com.badlogic.gdx.audio.Sound;

public class SoundHandler 
{
	public static void loadSound(String sound)
	{
		Game.manager.load("res/Sounds/"+sound+".wav", Sound.class);
	}
	public static Sound getSound(String sound)
	{
		return Game.manager.get("res/Sounds/"+sound+".wav", Sound.class);
	}
}
