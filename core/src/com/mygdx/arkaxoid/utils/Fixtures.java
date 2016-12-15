package com.mygdx.arkaxoid.utils;

import com.badlogic.gdx.physics.box2d.Filter;

public class Fixtures {
	static final short CHARACTER	= 0x0001;
	static final short BLOCK  		= 0x0002;
	static final short BALL   		= 0x0004;
	static final short POWERUP		= 0x0008;
	
	public static Filter createFilterForCharacter(Filter filter){
		Filter result;
		result = filter;
		result.categoryBits = CHARACTER;
		result.maskBits = BALL | POWERUP;
		return result;
	}
	
	public static Filter createFilterForBall(Filter filter){
		Filter result;
		result = filter;
		result.categoryBits = BALL;
		result.maskBits = CHARACTER | BLOCK;
		return result;
	}
	
	public static Filter createFilterForPowerUp(Filter filter){
		Filter result;
		result = filter;
		result.categoryBits = POWERUP;
		result.maskBits = CHARACTER;
		return result;
	}
	
	public static Filter createFilterForBlock(Filter filter){
		Filter result;
		result = filter;
		result.categoryBits = BLOCK;
		result.maskBits = BALL;
		return result;
	}
}
