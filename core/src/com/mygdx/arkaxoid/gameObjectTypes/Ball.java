package com.mygdx.arkaxoid.gameObjectTypes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.arkaxoid.Arkaxoid;
import com.mygdx.arkaxoid.utils.Fixtures;
import com.mygdx.arkaxoid.utils.Shapes;

public class Ball extends GameObject {

	// The movement speed that the character moves
	public final static float SPEED_X = 0.25f;
	public final static float SPEED_Y = 0.25f;

	// The limit of movement that the character can't go through it
	final static int left_limit_of_movement = 0;
	final static int right_limit_of_movement = Arkaxoid.WIDTH_RES;
	final static int top_limit_of_movement = Arkaxoid.HEIGHT_RES;
	final static int bottom_limit_of_movement = 0;

	private boolean inMovement;

	private boolean attachToCharacter;
	private float distanceAttached;

	public float getDistanceAttached() {
		return distanceAttached;
	}

	public void setDistanceAttached(float distanceAttached) {
		this.distanceAttached = distanceAttached;
	}

	public Ball(Texture t, Vector2 p, Body b, float pixelsToMeters) {
		super(t, p, b, Shapes.createCircleShape(t.getWidth()), 0.2f, 1f, 0f, false);
		b.setBullet(true);
		b.setFixedRotation(false);
		inMovement = false;

		getFixture().setFilterData(Fixtures.createFilterForBall(getFixture().getFilterData()));
	}

	public boolean isInMovement() {
		return inMovement;
	}

	public void setInMovement(boolean inMovement) {
		this.inMovement = inMovement;
	}

	public void applyInitialImpulse() {
		// Move the ball in a initial impulse
		getBody().applyLinearImpulse(new Vector2(0, Ball.SPEED_Y), new Vector2(getPhysicalX(), getPhysicalY()), true);
	}


	public boolean isAttachToCharacter() {
		return attachToCharacter;
	}

	public void setAttachToCharacter(boolean attachToCharacter) {
		this.attachToCharacter = attachToCharacter;
	}

}
