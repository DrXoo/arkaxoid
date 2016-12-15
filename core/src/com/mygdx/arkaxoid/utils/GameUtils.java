package com.mygdx.arkaxoid.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.arkaxoid.Arkaxoid;
import com.mygdx.arkaxoid.gameObjectTypes.Ball;

public class GameUtils {

	public static Animation create2DAnimation(Texture sheet, int rows, int cols, float changeVelocity) {
		TextureRegion[][] tmp = TextureRegion.split(sheet, (int) sheet.getWidth() / rows,
				(int) sheet.getHeight() / cols);
		TextureRegion[] frames;
		frames = new TextureRegion[rows * cols];
		int index = 0;
		// Load the frames
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[i].length; j++) {
				frames[index] = tmp[i][j];
				index++;
			}
		}
		return new Animation(changeVelocity, frames);
	}

	public static void removeAllBodiesFromBalls(Array<Ball> balls, World world) {
		for (Ball b : balls) {
			world.destroyBody(b.getBody());
		}
	}

	public static BitmapFont createFont(FileHandle file, int dp, float borderWidth, Color color){
		
		FreeTypeFontGenerator fg = new FreeTypeFontGenerator(file);
		
		FreeTypeFontParameter parameter;
		
		parameter = new FreeTypeFontParameter();
		
		parameter.size = Arkaxoid.WIDTH_RES/dp;
		parameter.borderWidth = borderWidth;
		parameter.borderColor = color;
		
	    return fg.generateFont(parameter);
	    
	}
}
