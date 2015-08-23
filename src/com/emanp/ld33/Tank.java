package com.emanp.ld33;

import com.badlogic.gdx.math.MathUtils;

public class Tank extends BoundedObject  implements Drawable
{
	public Tank(){}
	public Tank(double x, double y)
	{
		super(x,y,64,64);
		nuitrition=64;
	}
	public void update(double deltaTime)
	{
		x-=deltaTime*64;
		y=((7*y)-64+Slime.GROUND_LEVEL)/8;
		if(MathUtils.randomBoolean((float) (0.1*deltaTime)))
		{
			Game.entities.add(new Projectile(x, y));
		}
		if(x<-64)
		{
			dead=true;
			return;
		}
		super.update(deltaTime);
	}
	@Override
	public void draw() {
		TextureHandler.rawdrawTex(TextureHandler.getTex("Tank"),x,y,64,64,0,false,false);
	}

	@Override
	public void loadTex() {
		TextureHandler.loadTex("Tank");
	}

	@Override
	public void dispose() {
	}

}
