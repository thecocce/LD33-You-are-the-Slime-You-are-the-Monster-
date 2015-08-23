package com.emanp.ld33;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Slime extends BoundedObject  implements Drawable, Cloneable
{
	public double xvel,yvel,xaccel,yaccel,size,intertia;
	//public boolean dead = false;
	public static final double GROUND_LEVEL =500;
	public static final Color topcolor = new Color(1,1,1,0.5f);
	public static final Color bottomcolor = new Color(1,1,1,0.9f);
	public boolean onground=false;
	public double groundtime = 0;
	public static int slimecount =0;
	public static double totalslimesizes =  Integer.MIN_VALUE;
	public double fattogain=0;
	public double joincooldown = 0;
	public Slime(){}
	public Slime(double x, double y,double size)
	{
		super(x,y,size,size);
		this.x=x;
		this.y=y;
		intertia = Math.log(Math.pow(0.99,60));
		this.size=size;
		nuitrition=size;
		if(totalslimesizes==Integer.MIN_VALUE)
		{
			totalslimesizes=size;
		}
	}
	@Override
	public void draw()
	{
		//drawSlimeMesh();
		TextureHandler.rawdrawTex(TextureHandler.getTex("Slime"), x,y,size,size,0,xvel<0,false,1f,1f,1f,0.5f);
	}
	public void drawSlimeMesh()
	{
		int i=0;
		float[] verts = new float[20];
		//float t = (float)(1-(1/(1+groundtime)));
		float ystretch = 1;//MathUtils.lerp(1f, 1.04f, t);

		if(size<20)
		{
			ystretch=1;
		}
		//(float) (1f+(1f/(groundtime+1f)));
		boolean flipped = xvel<0;
		verts[i++] = (float)(flipped?x+size:x); // x1
		verts[i++] = (float)(y)*ystretch; // y1
		verts[i++] = topcolor.toFloatBits();//color
		verts[i++] = 0f; // u1
		verts[i++] = 0f; // v1

		verts[i++] =(float) (flipped?x:x+size); // x2
		verts[i++] = (float)(y)*ystretch; // y2
		verts[i++] = topcolor.toFloatBits();//color
		verts[i++] = 1f; // u2
		verts[i++] = 0f; // v2

		verts[i++] =(float) (flipped?x:x+size); // x3
		verts[i++] =(float) ((y+size)); // y3
		verts[i++] = bottomcolor.toFloatBits();//color
		verts[i++] = 1f; // u3
		verts[i++] = 1f; // v3

		verts[i++] =(float)(flipped?x+size:x); // x4
		verts[i++] =(float) ((y+size)); // y4
		verts[i++] = bottomcolor.toFloatBits();//color
		verts[i++] = 0f; // u4
		verts[i++] = 1f; // v4
		TextureHandler.batch.draw(TextureHandler.getTex("Slime"), verts, 0, 20);
	}
	public void update(double deltaTime)
	{
		double acceleration = 512*deltaTime*60;
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			xaccel=acceleration*Math.abs((y-GROUND_LEVEL)*8/128)*128/size;
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN))
		{
			yaccel+=acceleration;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			xaccel=-acceleration*Math.abs((y-GROUND_LEVEL)*8/128)*128/size;
		}
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			if(onground)
			{
				yaccel-=acceleration*1024/size;
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.SPACE))
		{
			split();
		}
		xvel+=xaccel*deltaTime;
		yvel+=yaccel*deltaTime;
		x+=xvel*deltaTime;
		y+=yvel*deltaTime;
		xvel*=Math.pow(Math.E, deltaTime * -42.191);
		yvel*=Math.pow(Math.E, deltaTime * intertia);
		xaccel*=Math.pow(Math.E, deltaTime * intertia);
		yaccel*=Math.pow(Math.E, deltaTime * intertia);
		if(y+size<GROUND_LEVEL)
		{
			yaccel+=98*deltaTime*60;
			yaccel*=Math.pow(Math.E, deltaTime *-30);
		}
		onground=false;
		if(y+(size*0.9)>GROUND_LEVEL)
		{
			onground=true;
			y=GROUND_LEVEL-(size*0.9);
			yvel=-Math.abs(yvel)*0.5;
			groundtime+=deltaTime;
		}else
		{
			groundtime=Math.max(groundtime-deltaTime, 0);
		}
		if(y<0)
		{
			y=0;
			yvel=Math.abs(yvel)*0.5;	
		}
		if(x<0)
		{
			x=0;
			xvel=Math.abs(xvel);
			xaccel=Math.abs(xaccel);
		}
		if(x+size>1024)
		{
			x=1024-size;
			xvel=-Math.abs(xvel);
			xaccel=-Math.abs(xaccel);
		}
		if(size<=4)
		{
			if(size<0)
			{
				totalslimesizes-=size;
				size=0;

				totalslimesizes+=size;
			}
			dead=true;
			totalslimesizes-=size;
		}
		for (int i = 0; i < Game.entities.size; i++)
		{
			if(!(Game.entities.get(i) instanceof Slime))
			{
				if(Game.entities.get(i) instanceof Projectile)
				{
					if(bounds.overlaps(Game.entities.get(i).bounds))			
					{
						size*=0.5;
						size-=10;
						Game.entities.get(i).dead=true;
						split();
					}
					continue;
				}
				if(bounds.overlaps(Game.entities.get(i).bounds) && nuitrition>Game.entities.get(i).nuitrition)			
				{
					Game.entities.get(i).dead=true;					
					fattogain+=Game.entities.get(i).nuitrition*64/size;
					SoundHandler.getSound("Eat").play(0.1f,0.7f,0.5f);
				}else
				{
					if(bounds.overlaps(Game.entities.get(i).bounds) && nuitrition<Game.entities.get(i).nuitrition)			
					{
						totalslimesizes-=size;
						size-=deltaTime*5;
						totalslimesizes+=size;
					}
				}
			}else
			{
				if(!this.equals(Game.entities.get(i)))
				if(nuitrition>4*Game.entities.get(i).nuitrition)			
				{
					if(joincooldown<=0 && ((Slime)Game.entities.get(i)).joincooldown<=0)
					if(bounds.overlaps(Game.entities.get(i).bounds))
					{
						//Game.entities.get(i).dead=true;
						double d =size*deltaTime/10;
						size+=d;
						((Slime)Game.entities.get(i)).size-=d;	
						//Game.entities.get(i).nuitrition=0;
					}
				}else if(nuitrition<Game.entities.get(i).nuitrition)
				{
					if(bounds.overlaps(Game.entities.get(i).bounds))
					{
						double d =size*deltaTime/30;
						size-=d;
						((Slime)Game.entities.get(i)).size+=d;	
					}
				}
			}
		}
		joincooldown-=deltaTime;
		totalslimesizes-=size;
		size-=deltaTime*(size/100);
		size+=fattogain*deltaTime;
		fattogain-=fattogain*deltaTime;
		nuitrition=size;
		totalslimesizes+=size;
		w=size;
		h=size;
		super.update(deltaTime);
	}
	public void split()
	{
		if(size<9)
		{
			return;
		}
		if(slimecount>256)
		{
			return;
		}
		try 
		{
			joincooldown=5;
			Slime s;
			totalslimesizes-=size;
			size/=2;
			totalslimesizes+=size;
			nuitrition=size;
			s = (Slime) this.clone();
			//totalslimesizes+=s.size;
			s.xaccel+=MathUtils.random((float)-5120, (float) 5120);
			s.yaccel+=MathUtils.random((float)-256, (float) 256);
			s.fattogain=0;
			s.bounds = new Rectangle(bounds);
			SoundHandler.getSound("Eat").play(0.1f,(float)(size/totalslimesizes),0.5f);
			Game.entities.add(s);

		} catch (CloneNotSupportedException e) 
		{
			e.printStackTrace();
		}

	}
	@Override
	public void loadTex() {
		TextureHandler.loadTex("Slime");
		SoundHandler.loadSound("Eat");
		SoundHandler.loadSound("Hit_Hurt");
	}

	@Override
	public void dispose() {
	}

}
