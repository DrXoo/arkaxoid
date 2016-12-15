package com.mygdx.arkaxoid.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationManager {

	private Array<Animation> animations;

	private Animation currentAnimation;

	private TextureRegion currentFrame;

	private float stateTime;

	public AnimationManager(Texture sheet, int rows, int cols) {

		// Initiates the animation
		currentAnimation = GameUtils.create2DAnimation(sheet, cols, rows, 0.200f);
		// Time lapses when starts the animation
		stateTime = 0f;

		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
	}

	public AnimationManager(Array<TextureRegion> frames, float speed) {
		currentAnimation = new Animation(speed, frames);
		stateTime = 0f;
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
	}

	public AnimationManager(Array<Animation> a) {
		animations = new Array<Animation>(a);
		
		stateTime = 0f;
		
		currentAnimation = animations.get(0);
		
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
		
	}
	
	public void changeAnimation(int index){
		stateTime = 0f;
		currentAnimation = animations.get(index);
	}
	
	public int getIndexOfCurrentAnimation(){
		return animations.indexOf(currentAnimation, true);
	}

	public boolean hasFinished(){
		return currentAnimation.isAnimationFinished(stateTime);
	}
	
	public void updateAnimation() {
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

	public float getFrameWidth() {
		return currentFrame.getRegionWidth();
	}

	public float getFrameHeight() {
		return currentFrame.getRegionHeight();
	}

}
