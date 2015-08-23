package com.emanp.ld33;

import com.badlogic.gdx.math.MathUtils;

public class Projectile extends BoundedObject  implements Drawable
{
	public Projectile(){}
	double angle,sina,cosa;
	public Projectile(double x, double y)
	{
		super(x,y,8,8);
		if(x>1024 || x<0)
		{
			dead=true;
			return;
		}
		//yoffset=MathUtils.random(-10, 10);
		angle=MathUtils.random(135, 180);
		sina=MathUtils.sinDeg((float) angle);
		cosa=MathUtils.cosDeg((float) angle);
		nuitrition=Integer.MAX_VALUE;
		SoundHandler.getSound("Shoot").play(1);
	}
	public void update(double deltaTime)
	{
		x+=cosa*deltaTime*256;
		y+=sina*deltaTime*256;
		if(x>1024 || x<-8 || y>1024)
		{
			dead=true;
			return;
		}
		//x-=deltaTime*256;
		//y=((7*y)-46+Slime.GROUND_LEVEL+yoffset)/8;
		super.update(deltaTime);
	}
	@Override
	public void draw() {
		TextureHandler.rawdrawTex(TextureHandler.getTex("Projectile"),x,y,8,8,0,false,false);
	}

	@Override
	public void loadTex() 
	{
		TextureHandler.loadTex("Projectile");
		SoundHandler.loadSound("Shoot");
	}

	@Override
	public void dispose() {
	}

}
