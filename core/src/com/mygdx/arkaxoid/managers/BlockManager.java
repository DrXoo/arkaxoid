package com.mygdx.arkaxoid.managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.arkaxoid.Arkaxoid;
import com.mygdx.arkaxoid.gameObjectTypes.Block;
import com.mygdx.arkaxoid.gameObjectTypes.GameObjectType;
import com.mygdx.arkaxoid.utils.BlockType;
import com.mygdx.arkaxoid.utils.BodyDefs;
import com.mygdx.arkaxoid.utils.Shapes;

public class BlockManager {

	private final static char normalBlue = 'b';
	private final static char normalRed = 'r';
	private final static char normalPink = 'p';
	private final static char normalGreen = 'g';

	private final static char hardBlue = 'B';
	private final static char hardRed = 'R';
	private final static char hardGreen = 'G';
	private final static char hardPink = 'P';

	private final static char indestructible = 'x';

	private final static char wall = 'X';

	public static final int SCORE_PER_NORMALBLOCK = 50;
	public static final int SCORE_PER_HARDBLOCK = 100;


	public static Array<Block> loadBlocksFromFile(Arkaxoid game, String fileName, World world) {
		Array<Block> result;
		FileHandle file;
		char charInRow;
		TextureRegion[] textureBlock = null;
		BlockType type = null;
		Vector2 positionBlock;
		Body bodyBlock;

		file = Gdx.files.internal(fileName);
		InputStreamReader input = new InputStreamReader(file.read());
		BufferedReader br = new BufferedReader(input);
		result = new Array<Block>();

		String row;
		int rowCount = 0;

		float x;
		float y;
		int textureWidth = 25;
		int textureHeight = 25;

		try {
			while ((row = br.readLine()) != null) {
				for (int j = 0; j < row.length(); j++) {
					charInRow = row.charAt(j);
					if (charInRow != '-') {
						// Choose the color of the sprite (the texture)
						switch (charInRow) {
						case normalBlue:
							textureBlock = game.assets.normalBlue;
							type = BlockType.NORMAL;
							break;
						case normalRed:
							textureBlock = game.assets.normalRed;
							type = BlockType.NORMAL;
							break;
						case normalGreen:
							textureBlock = game.assets.normalGreen;
							type = BlockType.NORMAL;
							break;
						case normalPink:
							textureBlock = game.assets.normalPink;
							type = BlockType.NORMAL;
							break;
						case hardBlue:
							textureBlock = game.assets.hardBlue;
							type = BlockType.HARD;
							break;
						case hardRed:
							textureBlock = game.assets.hardRed;
							type = BlockType.HARD;
							break;
						case hardGreen:
							textureBlock = game.assets.hardGreen;
							type = BlockType.HARD;
							break;
						case hardPink:
							textureBlock = game.assets.hardPink;
							type = BlockType.HARD;
							break;
						case indestructible:
							textureBlock = game.assets.indestructible;
							type = BlockType.INDESTRUCTIBLE;
							break;
						case wall:
							textureBlock = game.assets.wall;
							type = BlockType.INDESTRUCTIBLE;
							break;
						}

						// Set the position that corresponds

						x = (j) * textureWidth + 2.5f;
						y = Arkaxoid.HEIGHT_RES - 175 - rowCount * textureHeight;

						// Create the body, a block is a static body because it
						// doesn't
						// move
						positionBlock = new Vector2(x, y);
						bodyBlock = world.createBody(BodyDefs.createStaticBody(x + textureWidth / 2,
								y + textureHeight / 2, Arkaxoid.PIXELS_TO_METERS));
						// Tell the body what type of game object is
						if(textureBlock.equals(game.assets.wall)){
							bodyBlock.setUserData(GameObjectType.WALL);	
						}else{
							bodyBlock.setUserData(GameObjectType.BLOCK);
						}
						
						// Add it to the array of blocks
						result.add(new Block(textureBlock, positionBlock, bodyBlock,
								Shapes.createRectangularShape(textureWidth, textureHeight, Arkaxoid.PIXELS_TO_METERS),
								type));
					}
				}
				rowCount++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
