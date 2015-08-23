package com.emanp.ld33;

import com.badlogic.gdx.math.MathUtils;

public class Plane extends BoundedObject  implements Drawable
{
	public Plane(){}
	public Plane(double x, double y)
	{
		super(x,y,64,64);
		nuitrition=64;
	}
	public void update(double deltaTime)
	{
		x-=deltaTime*128;
		if(MathUtils.randomBoolean((float) (0.65*deltaTime)))
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
		TextureHandler.rawdrawTex(TextureHandler.getTex("Plane"),x,y,64,64,0,false,false);
	}

	@Override
	public void loadTex() {
		TextureHandler.loadTex("Plane");
	}

	@Override
	public void dispose() {
	}

}
