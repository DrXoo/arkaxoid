package com.mygdx.arkaxoid.utils;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class BodyDefs {


	public static BodyDef createDynamicBody(float x, float y, float width, float height, float pixelsToMeters) {
		BodyDef result = new BodyDef();
		result.type = BodyDef.BodyType.DynamicBody;
		result.position.set((x) / pixelsToMeters, (y) / pixelsToMeters);
		return result;
	}

	public static BodyDef createStaticBody(float x, float y, float pixelsToMeters) {
		BodyDef result = new BodyDef();
		result.type = BodyDef.BodyType.StaticBody;
		result.position.set(x / pixelsToMeters, y / pixelsToMeters);
		return result;
	}

	public static BodyDef createKinematicBody(float x, float y, float width, float height, float pixelsToMeters) {
		BodyDef result = new BodyDef();
		result.type = BodyDef.BodyType.KinematicBody;
		result.position.set((x + width / 2) / pixelsToMeters, (y + height / 2) / pixelsToMeters);
		return result;
	}
}
