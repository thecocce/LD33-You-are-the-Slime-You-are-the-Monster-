package com.emanp.ld33;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import org.lwjgl.openal.AL;

public class Game extends ApplicationAdapter {
	public static final int HEIGHT = 576;
	public static final int WIDTH = 1024;
	public static AssetManager manager;
	public static double highscoretot;
	public static double highscoremax;

	public static Array<BoundedObject> entities = new Array<BoundedObject>();
	@Override
	public void create () {
		manager = new AssetManager();
		entities.add(new Slime(0,576-256,9));
		TextureHandler.create();
		Gdx.input.setInputProcessor(new InputHandler());
		TextureHandler.loadTex("Background");
	}

	@Override
	public void render () 
	{
		if(Gdx.input.isKeyPressed(Keys.ESCAPE))
		{
			//exit();
		}
		Gdx.gl.glClearColor(0, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		double deltaTime = Gdx.graphics.getDeltaTime();
		if(!manager.update())
		{
			return;
		}
		TextureHandler.drawTex(TextureHandler.getTex("Background"),0,0);

		TextureHandler.batch.setProjectionMatrix(TextureHandler.camera.combined);
		TextureHandler.batch.begin();
		//Array<BoundedObject> deadies = new Array<BoundedObject>();
		int w=0;
		double tempmass = 0;
		double maxmass = Integer.MIN_VALUE;
		double minmass = Integer.MAX_VALUE;
		for (int i = 0; i < entities.size; i++) 
		{
			if(!entities.get(i).dead)
			{
				entities.get(i).update(deltaTime);
				entities.get(i).draw();
				if(entities.get(i) instanceof Slime)
				{
					maxmass=Math.max(maxmass,((Slime)entities.get(i)).size);
					minmass=Math.min(maxmass,((Slime)entities.get(i)).size);
					w+=1;
					tempmass+=((Slime)entities.get(i)).size;
				}
			}
			if(entities.get(i).dead)
			{
				//deadies.add(entities.get(i));
				entities.removeIndex(i);
			}
		}
		highscoremax=Math.max(highscoremax, maxmass);
		Slime.totalslimesizes=tempmass;
		if(w==0)
		{
			Gdx.graphics.setTitle("You are the Slime: Game Over.");
		}else
		{
			Gdx.graphics.setTitle("You are the Slime: FPS:"+((int)(1/deltaTime))+",Total Slimes:"+w+", Total Mass:"+(int)Slime.totalslimesizes+", Biggest Mass:"+(int)maxmass+", Smallest Mass:"+(int)minmass+", High Score:"+(int)highscoremax);
		}
		//entities.removeAll(deadies, true);
		
		if(MathUtils.randomBoolean((float) deltaTime*3))
		{
			entities.add(new Stickman(MathUtils.randomTriangular(0, 1024, 512), -100));
		}
		if(MathUtils.randomBoolean((float) deltaTime*0.125f))
		{
			entities.add(new Tank(MathUtils.randomTriangular(1024, 1200, 1024), 40));
			entities.add(new Plane(MathUtils.randomTriangular(1024, 1200, 1024), MathUtils.randomTriangular(80,120,100)));
		}
		TextureHandler.batch.end();
		TextureHandler.updatecamera();
		TextureHandler.camera.zoom*=1+(InputHandler.scrolllerp/4);
		InputHandler.scrolllerp=InputHandler.scrolllerp*3/4;
		TextureHandler.camera.zoom = Math.min(Math.max(TextureHandler.camera.zoom, (float)(1f/4f)),1f);
		TextureHandler.updatecamera();
	}
	static void exit() 
	{
		disposeRes();
		AL.destroy();
		System.exit(0);
	}

	public static void disposeRes ()
	{
		manager.dispose();
	}
}
