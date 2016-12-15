package com.mygdx.arkaxoid.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.arkaxoid.Arkaxoid;
import com.mygdx.arkaxoid.gameObjectTypes.GameObjectType;
import com.mygdx.arkaxoid.gameObjectTypes.PowerUp;
import com.mygdx.arkaxoid.utils.BodyDefs;
import com.mygdx.arkaxoid.utils.PowerUpType;

public class PowerUpManager {
	
	public static float probExtraBall;
	public static float probBigPlayer;
	public float probGlue;
	
	public static float probCreatePowerUp;
	
	public static void setProbs(float canCreate, float extraBall, float bigPj){
		probCreatePowerUp = canCreate;
		probExtraBall = extraBall;
		probBigPlayer = bigPj;
	}

	public static PowerUp createPowerUp(float x, float y, World world, Arkaxoid game) {
		PowerUp result;
		Vector2 position = new Vector2(x, y);
		Texture texture;
		PowerUpType type;
		float rnd = MathUtils.random(); // between 0 and 1 (exclusive)

		if (rnd < probExtraBall) {
			texture = game.assets.powerUp_ExtraBall;
			type = PowerUpType.EXTRABALL;
		} else if (rnd < (probExtraBall + probBigPlayer)) {
			texture = game.assets.powerUp_BigPj;
			type = PowerUpType.BIGPJ;
		} else {
			texture = game.assets.powerUp_Glue;
			type = PowerUpType.GLUE;
		}
		Body body = world.createBody(BodyDefs.createDynamicBody(x, y, texture.getWidth() / 4, texture.getHeight(),
				Arkaxoid.PIXELS_TO_METERS));

		result = new PowerUp(texture, body, position, 0.1f, 1f, 0f, false);
		// Type of game object
		result.getBody().setUserData(GameObjectType.POWERUP);
		result.applyInitialLinearImpulse();
		result.setType(type);
		
		return result;
	}
}
