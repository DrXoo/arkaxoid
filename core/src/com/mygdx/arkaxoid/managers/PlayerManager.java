package com.mygdx.arkaxoid.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.arkaxoid.Arkaxoid;
import com.mygdx.arkaxoid.gameObjectTypes.Player;
import com.mygdx.arkaxoid.gameObjectTypes.GameObjectType;
import com.mygdx.arkaxoid.utils.BodyDefs;

public class PlayerManager {

	public static Player createPlayer(float x, float y, Texture texture,World world) {
		Player result;

		// Body
		Body body_pj = world.createBody(BodyDefs.createStaticBody(x+texture.getWidth()/2, y+texture.getHeight()/2, Arkaxoid.PIXELS_TO_METERS));
		// Define what type of body is
		body_pj.setUserData(GameObjectType.CHARACTER);
		// Finally, create a character
		result = new Player(texture, new Vector2(x, y), body_pj, Arkaxoid.PIXELS_TO_METERS);

		result.setInitialPosition(new Vector2(x, y));
	
		return result;
	}

	public static Player setToBigPj(World world, Player pj, Texture texture) {
		Player result;
		world.destroyBody(pj.getBody());
		// Body
		Boolean wasGlue = pj.isGlue();
		Body body_pj = world.createBody(BodyDefs.createKinematicBody(pj.getX(), pj.getY(), texture.getWidth(),
				texture.getHeight(), Arkaxoid.PIXELS_TO_METERS));
		// Define what type of body is
		body_pj.setUserData(GameObjectType.CHARACTER);
		// Finally, create a character
		result = new Player(texture, new Vector2(pj.getX(), pj.getY()), body_pj, Arkaxoid.PIXELS_TO_METERS);
		result.setGlue(wasGlue);
		result.setInitialPosition(new Vector2(Arkaxoid.WIDTH_RES / 2 - texture.getWidth() / 2,pj.getInitialPosition().y));
		result.setBig(true);
		result.setLives(pj.getLives());

		return result;
	}
	
}
