package com.mygdx.arkaxoid.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.arkaxoid.Arkaxoid;
import com.mygdx.arkaxoid.gameObjectTypes.GameObjectType;
import com.mygdx.arkaxoid.gameObjectTypes.MadBlock;
import com.mygdx.arkaxoid.gameObjectTypes.Player;
import com.mygdx.arkaxoid.utils.BodyDefs;
import com.mygdx.arkaxoid.utils.Shapes;

public class MadBlockManager {

	public static MadBlock createBossMadBlock(float x, float y, Texture texture, World world) {
		MadBlock result;
		int width = 35;
		int height = 35;

		// Body
		Body body = world.createBody(BodyDefs.createDynamicBody(x + width / 2, y + height / 2,
				width, height, Arkaxoid.PIXELS_TO_METERS));
		// Define what type of body is
		body.setUserData(GameObjectType.MADBLOCK);

		result = new MadBlock(texture, new Vector2(x, y), body,
				Shapes.createRectangularShape(width,height,Arkaxoid.PIXELS_TO_METERS));

		result.setInitialPosition(new Vector2(x, y));
		
		ArrayMap<Integer, Array<Vector2>> patternMoves;
		
		patternMoves = new ArrayMap<Integer, Array<Vector2>>();

		Array<Vector2> rectangularMovement = new Array<Vector2>();

		rectangularMovement.add(new Vector2(Player.LEFT_LIMIT_OF_MOVEMENT, y));
		rectangularMovement.add(new Vector2(Player.LEFT_LIMIT_OF_MOVEMENT, Arkaxoid.HEIGHT_RES - 200 - height));
		rectangularMovement.add(new Vector2(Player.RIGHT_LIMIT_OF_MOVEMENT-width, Arkaxoid.HEIGHT_RES - 200 - height));
		rectangularMovement.add(new Vector2(Player.RIGHT_LIMIT_OF_MOVEMENT-width, y));
		
		Array<Vector2> triangularMovement = new Array<Vector2>();

		triangularMovement.add(new Vector2(Player.LEFT_LIMIT_OF_MOVEMENT, y));
		triangularMovement.add(new Vector2(x, Arkaxoid.HEIGHT_RES - 200 - height));
		triangularMovement.add(new Vector2(Player.RIGHT_LIMIT_OF_MOVEMENT-height, y));

		Array<Vector2> lateralMovement = new Array<Vector2>();

		lateralMovement.add(new Vector2(Player.LEFT_LIMIT_OF_MOVEMENT, y));
		lateralMovement.add(new Vector2(Player.RIGHT_LIMIT_OF_MOVEMENT-width, y));
		
		patternMoves.put(0, lateralMovement);
		patternMoves.put(1, triangularMovement);
		patternMoves.put(2, rectangularMovement);
		
		result.setPatternMoves(patternMoves);
		result.chooseMovement(MathUtils.random(patternMoves.size-1));

		return result;
	} 

}
