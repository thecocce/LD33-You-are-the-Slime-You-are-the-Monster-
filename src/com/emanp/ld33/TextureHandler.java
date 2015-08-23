package com.emanp.ld33;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TextureHandler 
{
	public static SpriteBatch batch;
	public static OrthographicCamera camera;
	public static FitViewport viewport;
	public static double screenoriginx,screenoriginy;
	public static Rectangle fov = new Rectangle((float)screenoriginx,(float)screenoriginy,Game.WIDTH,Game.HEIGHT);
	
	public static void loadTex(String texstr)
	{
		Game.manager.load("res/Images/"+texstr+".png", Texture.class);
	}
	public static Texture getTex(String texstr)
	{
		return Game.manager.get("res/Images/"+texstr+".png", Texture.class);
	}
	public static void create()
	{
		camera = new OrthographicCamera();
		camera.setToOrtho(true, Game.WIDTH, Game.HEIGHT);
		viewport = new FitViewport(Game.WIDTH, Game.HEIGHT, camera);
		batch = new SpriteBatch();
		new Slime().loadTex();
		new Plane().loadTex();
		new Tank().loadTex();
		new Stickman().loadTex();
		new Projectile().loadTex();
	}
	public static void drawTex(Texture tex, double x, double y, double originX, double originY, double width, double height, double rotation,  boolean flipX, boolean flipY)

	{
		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();
		batch.draw(tex,(float)x,(float)y,(float)originX,(float)originY,(float)width,(float)height,1,1,(float)rotation,0,0,tex.getWidth(), tex.getHeight(), flipX, !flipY);
		batch.end();
	}
	public static void drawTex(Texture tex,double x, double y,double width,double height,double angle,boolean flipx, boolean flipy)
	{
		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();
		batch.draw(tex,(float)x,(float)y,(float)width/2,(float)height/2,(float)width,(float)height,1,1,(float)angle,0,0,(int)tex.getWidth(),(int)tex.getHeight(),flipx,!flipy);
		batch.end();
	}
	public static void rawdrawTex(Texture tex,double x, double y,double width,double height,double angle,boolean flipx, boolean flipy)
	{
		//batch.setProjectionMatrix(viewport.getCamera().combined);
		//batch.begin();
		batch.draw(tex,(float)x,(float)y,(float)width/2,(float)height/2,(float)width,(float)height,1,1,(float)angle,0,0,(int)tex.getWidth(),(int)tex.getHeight(),flipx,!flipy);
		//batch.end();
	}
	public static void drawTex(Texture tex,double x, double y)
	{
		drawTex(tex, x, y,tex.getWidth(),tex.getHeight(),0,false,false);
	}
	public static void rawdrawTex(Texture tex,double x, double y)
	{
		rawdrawTex(tex, x, y,tex.getWidth(),tex.getHeight(),0,false,false);
	}
	public static void drawTex(Texture tex,double x, double y,double width,double height,double angle,boolean flipx, boolean flipy, double red, double green, double blue,double alpha)
	{
		batch.setShader(null);
		batch.setColor((float)red, (float)green,(float) blue, (float)alpha);
		drawTex(tex, x, y, width, height, angle, flipx, flipy);
		batch.setColor(1, 1, 1, 1);
	}
	public static void rawdrawTex(Texture tex,double x, double y,double width,double height,double angle,boolean flipx, boolean flipy, double red, double green, double blue,double alpha)
	{
		batch.setShader(null);
		batch.setColor((float)red, (float)green,(float) blue, (float)alpha);
		rawdrawTex(tex, x, y, width, height, angle, flipx, flipy);
		batch.setColor(1, 1, 1, 1);
	}
	public static void drawTex(Texture tex,double x, double y,double width,double height,double angle,boolean flipx, boolean flipy, double red, double green, double blue)
	{
		batch.setShader(null);
		batch.setColor((float)red,(float) green,(float) blue, 1);
		drawTex(tex, x, y, width, height, angle, flipx, flipy);
		batch.setColor(1, 1, 1, 1);
	}
	public static void translate(double x, double y)
	{
		camera.translate((float)x, (float)y);
		screenoriginx+=x;
		screenoriginy+=y;
	}
	public static void setcampos(double x, double y)
	{
		setcampos(x, y,false);
	}
	public static void setcampos(double x, double y, boolean centred)
	{
		double dx = screenoriginx-(camera.position.x);
		double dy = screenoriginy-(camera.position.y);
		if(centred)
		{
			camera.position.set((float)x, (float)y, camera.position.z);
			screenoriginx=x+dx;
			screenoriginy=y+dy;
		}else
		{
			camera.position.set((float)x+(Game.WIDTH/2), (float)y+(Game.HEIGHT/2), camera.position.z);
			screenoriginx=x+dx;
			screenoriginy=y+dy;
		}
		updatecamera();
	}
	public static void setcamandoriginpos(double x, double y)
	{
		setcamandoriginpos(x, y, false);
	}
	public static void setcamandoriginpos(double x, double y,boolean centred)
	{
		if(centred)
		{
			camera.position.set((float)x, (float)y, camera.position.z);
			screenoriginx=x+(Game.WIDTH/2);
			screenoriginy=y+(Game.HEIGHT/2);
		}else
		{
			camera.position.set((float)x+(Game.WIDTH/2), (float)y+(Game.HEIGHT/2), camera.position.z);
			screenoriginx=x;
			screenoriginy=y;
		}
		updatecamera();
	}
	public static void updatecamera()
	{
		//camera.update();
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.setProjectionMatrix(viewport.getCamera().combined);

		fov.x = TextureHandler.camera.position.x-(Game.WIDTH*TextureHandler.camera.zoom/2);
		fov.y = TextureHandler.camera.position.y-(Game.HEIGHT*TextureHandler.camera.zoom/2);
		fov.width = TextureHandler.camera.viewportWidth*TextureHandler.camera.zoom;
		fov.height = TextureHandler.camera.viewportHeight*TextureHandler.camera.zoom;
		
		camera.update();
	}
}
