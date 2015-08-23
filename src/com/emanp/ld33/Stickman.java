package com.emanp.ld33;

import com.badlogic.gdx.math.MathUtils;

public class Stickman extends BoundedObject implements Drawable
{
	public double speed = MathUtils.random(-3f, 3f);
	public boolean onground = false;
	public Stickman(){}
	public Stickman(double x, double y)
	{
		super(x,y,16,16);
	}
	public void update(double deltaTime)
	{
		if(y+16<Slime.GROUND_LEVEL)
		{
			y+=deltaTime*256;
		}else
		{
			if(y+16>=Slime.GROUND_LEVEL)
			{
				y=Slime.GROUND_LEVEL-16;
				onground=true;
			}
			else
			{
				onground=false;
			}
			x+=deltaTime*16*speed;
			if(Math.abs(speed)<1)
			speed*=1.01;
		}
		if(x<0 || x>1024)
		{
			dead=true;
		}
		super.update(deltaTime);
	}
	@Override
	public void draw() {
		String texstr = onground? "StickMan":"Child";
		double newy = onground? y:y-16;
		double scale= onground? 1:2;
		TextureHandler.rawdrawTex(TextureHandler.getTex(texstr),x,newy,16,16*scale,0,false,false);
	}

	@Override
	public void loadTex() {
		TextureHandler.loadTex("Child");//Muhahahaha Github... You thought it was a parachuter!! You are the Monster after all.
		TextureHandler.loadTex("StickMan");
	}

	@Override
	public void dispose() {
	}

}
