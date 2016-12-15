package com.mygdx.arkaxoid.gameObjectTypes;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.arkaxoid.utils.BlockType;
import com.mygdx.arkaxoid.utils.Fixtures;

public class Block extends GameObject {

	private BlockType type;
	private TextureRegion[] frames;
	private int currentFrame;

	public Block(TextureRegion[] t, Vector2 p, Body b, Shape shape, BlockType type) {
		super(t[0], p, b, shape);
		this.type = type;

		frames = t;
		currentFrame = 0;

		getFixture().setFilterData(Fixtures.createFilterForBlock(getFixture().getFilterData()));
	}

	public BlockType getType() {
		return type;
	}

	public void setType(BlockType type) {
		this.type = type;
	}

	@Override
	public TextureRegion getTexture() {
		return frames[currentFrame];
	}

	public boolean canBeRemoved() {
		return frames.length - 1 == currentFrame;
	}

	public void nextFrame() {
		currentFrame++;
	}
	
	@Override
	public float getWidth() {
		return frames[currentFrame].getRegionWidth();
	}

	@Override
	public float getHeight() {
		return frames[currentFrame].getRegionHeight();
	}
}
