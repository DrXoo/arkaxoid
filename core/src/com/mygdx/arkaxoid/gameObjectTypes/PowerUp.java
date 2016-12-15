package com.mygdx.arkaxoid.gameObjectTypes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.arkaxoid.utils.AnimationManager;
import com.mygdx.arkaxoid.utils.Fixtures;
import com.mygdx.arkaxoid.utils.PowerUpType;
import com.mygdx.arkaxoid.utils.Shapes;

public class PowerUp extends GameObject{

	private PowerUpType type;
	
	public static final float SPEED_Y = -0.4f;

	
	public AnimationManager animationManager;
	
	public PowerUp(Texture sheet, Body b, Vector2 p, float density,
			float restitution, float friction, boolean isSensor) {
		super(sheet, p, b, Shapes.createCircleShape(sheet.getWidth()/4),density,restitution,friction,isSensor);
		getFixture().setFilterData(Fixtures.createFilterForPowerUp(getFixture().getFilterData()));
		animationManager = new AnimationManager(sheet, 1, 4);
		this.setTexture(animationManager.getCurrentFrame());
	}

	public PowerUpType getType() {
		return type;
	}

	public void setType(PowerUpType type) {
		this.type = type;
	}
	
	public void applyInitialLinearImpulse() {
		// Initial impulse, to down
		getBody().applyLinearImpulse(new Vector2(0, SPEED_Y),
				new Vector2(getBody().getPosition().x, getBody().getPosition().y), true);
		// Not to rotate
		getBody().setFixedRotation(false);
	}
	
	public void update(){
		updateSpritePositionFromBody();
		animationManager.updateAnimation();
		setTexture(animationManager.getCurrentFrame());
	}

}
