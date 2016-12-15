package com.mygdx.arkaxoid.gameObjectTypes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Room {

	
	private TextureRegion[] frames;
	private int currentFrame;
	private Rectangle rectangle;
	private String name;
	private String boss;
	private int[] playerLimitOfMovement;
	private float[] probabilitiesOfPowerUps;

	public Room(){
		
	}
	
	public Room(String name, Texture texture, float x, float y, String b, int[] l, float[] p){
		TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth()/2, texture.getHeight());
		frames = new TextureRegion[2];
		for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 2; j++) {
                frames[j] = tmp[i][j];
            }
        }
		currentFrame=0;
		boss = b;
		playerLimitOfMovement = l;
		probabilitiesOfPowerUps = p;
		this.name = name;
		rectangle = new Rectangle(x, y, texture.getWidth()/2, texture.getHeight());
	}
	
	
	public int[] getPlayerLimitOfMovement() {
		return playerLimitOfMovement;
	}

	public Room(String name, Texture texture, float x, float y, float width, float height){
		TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth()/2, texture.getHeight());
		frames = new TextureRegion[2];
		for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 2; j++) {
                frames[j] = tmp[i][j];
            }
        }
		currentFrame=0;
		this.name = name;
		rectangle = new Rectangle(x, y, width, height);
	}
	public boolean hasBoss(){
		return !boss.equals("0");
	}

	public String getBoss() {
		return boss;
	}

	public float getWidth(){
		return frames[currentFrame].getRegionWidth();
	}
	
	public float getHeight(){
		return frames[currentFrame].getRegionHeight();
	}
	
	public float getX(){
		return rectangle.x;
	}
	
	public float getY(){
		return rectangle.y;
	}
	
	public TextureRegion getTexture(){
		return frames[currentFrame];
	}
	
	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public Rectangle getRectangle(){
		return rectangle;
	}
	
	public String getName(){
		return name;
	}
	
	public float[] getProbabilitiesOfPowerUps() {
		return probabilitiesOfPowerUps;
	}
	
}
