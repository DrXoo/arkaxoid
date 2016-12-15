package com.mygdx.arkaxoid.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.arkaxoid.Arkaxoid;
import com.mygdx.arkaxoid.gameObjectTypes.Ball;
import com.mygdx.arkaxoid.gameObjectTypes.GameObjectType;
import com.mygdx.arkaxoid.utils.BodyDefs;

public class BallManager {

	public static Ball createBall(float x, float y,  Texture texture, World world, Vector2 initialPos) {
		Ball result;
		// Body
		Body body_ball = world.createBody(BodyDefs.createDynamicBody(x, y,
				texture.getWidth(), texture.getHeight(), Arkaxoid.PIXELS_TO_METERS));
		// Define what type of body is
		body_ball.setUserData(GameObjectType.BALL);
		// Finally, create the ball
		result = new Ball(texture, new Vector2(x , y),
				body_ball, Arkaxoid.PIXELS_TO_METERS);
		
		result.setInitialPosition(initialPos);
		
		return result;
	}
	
	public static Ball createEnemyBall(float x, float y,  Texture texture, World world, Vector2 movement) {
		Ball result;
		// Body
		Body body_ball = world.createBody(BodyDefs.createDynamicBody(x, y,
				texture.getWidth(), texture.getHeight(), Arkaxoid.PIXELS_TO_METERS));
		// Define what type of body is
		body_ball.setUserData(GameObjectType.ENEMYBALL);
		// Finally, create the ball
		result = new Ball(texture, new Vector2(x , y),
				body_ball, Arkaxoid.PIXELS_TO_METERS);
		
		result.applyVector2Impulse(movement);
		
		return result;
	}
	
	public static Ball findByBody(Body body, Array<Ball> balls) {
		Ball result = null;
		for (Ball b : balls) {
			if (b.getBody().equals(body)) {
				result = b;
				break;
			}
		}
		return result;
	}
}
