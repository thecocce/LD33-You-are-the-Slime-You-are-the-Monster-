package com.emanp.ld33;

import org.lwjgl.input.Keyboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputHandler implements InputProcessor
{
	public static boolean istextrequested = false;
	public static boolean isleftclicked = false;
	public static int eventKey;
	public static int count = 0;
	public static double scrolllerp;

	private static int getMouseX()
	{
		return Gdx.input.getX();
	}
	private static int getMouseY()
	{
		return Gdx.input.getY();
	}
	public static void lastupdate()
	{
		isleftclicked=false;
	}
	public static Vector2 getRealMousePos(Vector2 vec2)
	{
		Vector3 vec3 = TextureHandler.camera.unproject(new Vector3(vec2.x,vec2.y,0));
		return new Vector2(vec3.x,vec3.y);
	}
	public static int getRealMouseX()
	{
		Vector2 vec2 = getRealMousePos(new Vector2(getMouseX(),getMouseY()));
		return (int)vec2.x;
	}
	public static int getRealMouseY()
	{
		Vector2 vec2 = getRealMousePos(new Vector2(getMouseX(),getMouseY()));
		return (int)vec2.y;
	}
	public static void keyboardcheck()
	{
		if(Gdx.input.isKeyJustPressed(Keys.EQUALS))
		{
			//SoundHandler.basevolume=(float) Math.min(SoundHandler.basevolume+0.1f, 1f);
		}
		if(Gdx.input.isKeyJustPressed(Keys.MINUS))
		{
			//SoundHandler.basevolume=(float) Math.max(SoundHandler.basevolume-0.1f, 0f);
		}
	}
	@Override
	public boolean keyDown(int keycode)
	{

		return true;
	}
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}
	@Override
	public boolean keyTyped(char character) 
	{
		int i = Keyboard.getEventKey();
		if(i == Keyboard.KEY_F11)
		{
			if(!Gdx.graphics.isFullscreen())
			{
				DisplayMode d = Gdx.graphics.getDesktopDisplayMode();
				Gdx.graphics.setDisplayMode(d);
			}else
			{
				Gdx.graphics.setDisplayMode(Game.WIDTH, Game.HEIGHT, false);
			}
			//Gdx.graphics.set

			TextureHandler.camera.setToOrtho(true, Game.WIDTH, Game.HEIGHT);
			//TextureHandler.setDisplayMode(1280, 720, !Gdx.graphics.isFullscreen());
			return true;
		}
		if(i == Keyboard.KEY_ESCAPE)
		{
			Game.exit();
		}
		if(istextrequested)
		{
			eventKey=i;
		}
		return true;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.LEFT)
		{
			isleftclicked=true;
			return true;     
		}
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	@Override
	public boolean scrolled(int amount) 
	{
		double scroll = (double)(amount)/8;
		scrolllerp+=scroll;
		
		return false;
	}
}
