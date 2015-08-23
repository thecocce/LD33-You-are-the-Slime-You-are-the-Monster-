package com.emanp.ld33;

import com.badlogic.gdx.math.Rectangle;

public class BoundedObject
{
	public double x,y,w,h;
	public Rectangle bounds;
	public boolean dead;
	public double nuitrition = 1;
	public BoundedObject(double x, double y, double w, double h) 
	{
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		bounds = new Rectangle((float)x,(float)y,(float)w,(float)h);
	}
	public BoundedObject(){}
	public void update(double deltaTime) {
		bounds.x=(float)x;
		bounds.y=(float)y;
		bounds.width=(float)w;
		bounds.height=(float)h;
	}
	public void draw() {
		// TODO Auto-generated method stub
		
	}
}
