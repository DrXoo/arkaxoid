package com.mygdx.arkaxoid.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.arkaxoid.Arkaxoid;

public class Shapes {
	
	
	public static PolygonShape createRectangularShape(Sprite sprite){
		PolygonShape result = new PolygonShape();
		
		result.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);
		
		return result;
	}
	
	public static PolygonShape createRectangularShape(Sprite sprite, float pixelsToMeters){
		PolygonShape result = new PolygonShape();
		
		result.setAsBox(sprite.getWidth() / 2 / pixelsToMeters, sprite.getHeight() / 2 / pixelsToMeters);
		
		return result;
	}
	
	public static PolygonShape createRectangularShape(Texture texture, float pixelsToMeters){
		PolygonShape result = new PolygonShape();
		
		result.setAsBox(texture.getWidth() / 2 / pixelsToMeters, texture.getHeight() / 2 / pixelsToMeters);
		
		return result;
	}
	
	public static PolygonShape createRectangularShape(int width, int height){
		PolygonShape result = new PolygonShape();
		
		result.setAsBox(width, height);
		
		return result;
	}
	
	public static PolygonShape createRectangularShape(int width, int height, float pixelsToMeters){
		PolygonShape result = new PolygonShape();
		
		result.setAsBox((width/2)/pixelsToMeters, (height/2)/pixelsToMeters);
		
		return result;
	}
	
	public static PolygonShape createPolygonConvexShape(Vector2[] vertices){
		PolygonShape result = new PolygonShape();
		
		result.set(vertices);
		
		return result;
	}
	
	public static CircleShape createCircleShape(Sprite sprite){
		CircleShape result;
		
		result = new CircleShape();
		
		result.setRadius(sprite.getWidth()/2);
		
		return result;
	}
	
	public static CircleShape createCircleShape(Sprite sprite, float pixelsToMeters){
		CircleShape result;
		
		result = new CircleShape();
		
		result.setRadius(sprite.getWidth()/2 / pixelsToMeters);
		
		return result;
	}
	
	public static CircleShape createCircleShape(float width){
		CircleShape result;
		
		result = new CircleShape();
		
		result.setRadius(width/2 / Arkaxoid.PIXELS_TO_METERS);
		
		return result;
	}
}
