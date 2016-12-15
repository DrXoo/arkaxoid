package com.mygdx.arkaxoid.gameObjectTypes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.arkaxoid.Arkaxoid;

public abstract class GameObject {

	private TextureRegion texture;
	private Body body;
	private Vector2 position;
	private Fixture fixture;

	private Vector2 initialPosition;

	public GameObject(TextureRegion t, Vector2 p, Body b, Shape shape) {
		position = p;
		texture = t;
		body = b;
		this.setPhysics(shape, 0.1f, 1f, 1f, false);
	}

	public GameObject(Texture t, Vector2 p, Body b, Shape shape, float density, float restitution, float friction,
			boolean isSensor) {
		position = p;
		texture = new TextureRegion(t);
		body = b;
		this.setPhysics(shape, density, restitution, friction, isSensor);
	}

	public void setPhysics(Shape shape, float density, float restitution, float friction, boolean sensor) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.isSensor = sensor;
		fixtureDef.density = density;
		fixtureDef.restitution = restitution;
		fixtureDef.friction = friction;
		fixture = body.createFixture(fixtureDef);
		shape.dispose();
	}

	public void updateSpritePositionFromBody() {
		position.set((getBody().getPosition().x * Arkaxoid.PIXELS_TO_METERS) - getWidth() / 2,
				(getBody().getPosition().y * Arkaxoid.PIXELS_TO_METERS) - getHeight() / 2);
	}

	public void resetPosition() {
		position.set(getInitialPosition());
		getBody().setLinearVelocity(0, 0);
		getBody().setTransform((getInitialPosition().x + getWidth() / 2) / Arkaxoid.PIXELS_TO_METERS,
				(getInitialPosition().y + getHeight() / 2) / Arkaxoid.PIXELS_TO_METERS, 0);
	}

	public void applySpecifiedImpulseFromPosition(float x, float y,float maxSpeed) {

		Vector2 o = new Vector2(getPosition());
		Vector2 d = new Vector2(x, y);

		Vector2 od = new Vector2(d);
		od.sub(o);

		// Angulo del vector OD
		float angleOD = od.angleRad();
		// Con ese angulo calculamos el seno y coseno para las coordenadas X e Y
		double cosAngleOD = Math.cos(angleOD);
		double sinAngleOD = Math.sin(angleOD);

		float speedX = maxSpeed * (float) cosAngleOD;
		float speedY = maxSpeed * (float) sinAngleOD;

		getBody().setLinearVelocity(0, 0);

		getBody().applyLinearImpulse(new Vector2(speedX,speedY), new Vector2(getPhysicalX(), getPhysicalY()), true);
	}

	public void applyVector2Impulse(Vector2 v) {
		getBody().setLinearVelocity(0, 0);

		getBody().applyLinearImpulse(new Vector2(v.x, v.y), new Vector2(getPhysicalX(), getPhysicalY()), true);
	}

	public Vector2 getInitialPosition() {
		return initialPosition;
	}

	public void setInitialPosition(Vector2 initialPosition) {
		this.initialPosition = initialPosition;
	}

	public TextureRegion getTexture() {
		return texture;
	}

	public void setPosition(Vector2 position) {
		this.position = new Vector2(position);
		getBody().setTransform((position.x + getWidth() / 2) / Arkaxoid.PIXELS_TO_METERS,
				(position.y + getHeight() / 2) / Arkaxoid.PIXELS_TO_METERS, 0);
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getWidth() {
		return texture.getRegionWidth();
	}

	public float getHeight() {
		return texture.getRegionHeight();
	}

	public float getPhysicalX() {
		return body.getPosition().x;
	}

	public float getPhysicalY() {
		return body.getPosition().y;
	}

	public Body getBody() {
		return body;
	}

	public Fixture getFixture() {
		return fixture;
	}

	public Vector2 getPosition() {
		return position;
	}
	
	public void setTexture(TextureRegion texture) {
		this.texture = texture;
	}

}
