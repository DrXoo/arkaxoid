package com.mygdx.arkaxoid.gameObjectTypes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.arkaxoid.Arkaxoid;
import com.mygdx.arkaxoid.utils.Fixtures;
import com.mygdx.arkaxoid.utils.Shapes;

public class Player extends GameObject {

	// The limit of movement that the character can't go through it
	public static int LEFT_LIMIT_OF_MOVEMENT;
	public static int RIGHT_LIMIT_OF_MOVEMENT;

	public static float SPEED = 20f;

	private int lives;

	private boolean isBig;
	private boolean isGlue;

	public Player(Texture t, Vector2 p, Body b, float pixelsToMeters) {
		super(t, p, b, Shapes.createRectangularShape(t, pixelsToMeters), 0.2f, 1f, 0f, true);
		getFixture().setFilterData(Fixtures.createFilterForCharacter(getFixture().getFilterData()));

		isBig = false;
		isGlue = false;
	}

	public void setLimitsOfMovement(int left, int right) {
		LEFT_LIMIT_OF_MOVEMENT = left;
		RIGHT_LIMIT_OF_MOVEMENT = Arkaxoid.WIDTH_RES - right;
	}

	public void updateMovement(Vector3 touchPos, float delta) {
		Vector2 d = new Vector2(touchPos.x - getWidth() / 2, getPosition().y);

		if(d.x <= LEFT_LIMIT_OF_MOVEMENT){
			d.x = LEFT_LIMIT_OF_MOVEMENT;
		}
		
		if(d.x >= (RIGHT_LIMIT_OF_MOVEMENT - getWidth())){
			d.x = RIGHT_LIMIT_OF_MOVEMENT - getWidth();
		}
		
		if (getPosition().dst(d) > delta * 10) {
			float diffx = d.x - getPosition().x;
			setPosition(new Vector2(getPosition().x + diffx * delta * SPEED, getPosition().y));
		} else {
			setPosition(d);
		}
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public boolean isBig() {
		return isBig;
	}

	public void setBig(boolean isBig) {
		this.isBig = isBig;
	}

	public boolean isGlue() {
		return isGlue;
	}

	public void setGlue(boolean isGlue) {
		this.isGlue = isGlue;
	}

}
