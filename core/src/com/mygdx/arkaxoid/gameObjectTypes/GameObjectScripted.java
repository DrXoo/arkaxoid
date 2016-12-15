package com.mygdx.arkaxoid.gameObjectTypes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

public abstract class GameObjectScripted extends GameObject {

	private int actualIndex;

	private Array<Vector2> transitPositions;
	private Vector2 actualTransit;

	private ArrayMap<Integer, Array<Vector2>> patternMoves;

	private boolean loop;

	protected static final int START_MOVEMENT = 1;
	protected static final int STANDBY = 0;

	public boolean isLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	private int state;

	public GameObjectScripted(Texture t, Vector2 p, Body b, Shape shape, float density, float restitution,
			float friction, boolean isSensor) {
		super(t, p, b, shape, density, restitution, friction, isSensor);
		patternMoves = new ArrayMap<Integer, Array<Vector2>>();
		actualIndex = 0;
		actualTransit = new Vector2();
		state = STANDBY;
	}

	public ArrayMap<Integer, Array<Vector2>> getPatternMoves() {
		return patternMoves;
	}

	public void setPatternMoves(ArrayMap<Integer, Array<Vector2>> patternMoves) {
		this.patternMoves = patternMoves;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void chooseMovement(Integer i) {
		transitPositions = new Array<Vector2>(patternMoves.get(i));
		actualTransit = new Vector2(transitPositions.get(actualIndex));
	}

	public void updateMovement() {
		if (state == START_MOVEMENT) {
			if (actualIndex >= 0) {
				if (getPosition().dst(actualTransit) < 2f) {
					setPosition(actualTransit);
					if (actualIndex < transitPositions.size - 1) {
						actualIndex++;
						actualTransit = new Vector2(transitPositions.get(actualIndex));
						getBody().setLinearVelocity(Vector2.Zero);
					} else {
						getBody().setLinearVelocity(Vector2.Zero);
						actualIndex = -1;
					}
				} else {
					if (getBody().getLinearVelocity().isZero()) {
						applySpecifiedImpulseFromPosition(actualTransit.x, actualTransit.y, 1f);
					}
				}

			} else {
				if (getPosition().dst(getInitialPosition()) < 2f) {
					setPosition(getInitialPosition());
					getBody().setLinearVelocity(Vector2.Zero);
					if (loop) {
						actualIndex = 0;
						transitPositions = patternMoves.get(MathUtils.random(2));
						actualTransit = new Vector2(transitPositions.get(actualIndex));
					}
				} else {
					if (getBody().getLinearVelocity().isZero()) {
						applySpecifiedImpulseFromPosition(getInitialPosition().x, getInitialPosition().y, 1f);
					}
				}
			}
		}
	}

}
