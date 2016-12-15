package com.mygdx.arkaxoid.screens.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.arkaxoid.Arkaxoid;
import com.mygdx.arkaxoid.gameObjectTypes.Ball;
import com.mygdx.arkaxoid.gameObjectTypes.Block;
import com.mygdx.arkaxoid.gameObjectTypes.MadBlock;
import com.mygdx.arkaxoid.gameObjectTypes.PowerUp;
import com.mygdx.arkaxoid.screens.BossScreen;

public class BossScreenRenderer {

	private final Arkaxoid game;
	private BossScreen screen;
	ShapeRenderer shapeRenderer;

	public BossScreenRenderer(Arkaxoid gam, BossScreen w) {
		game = gam;
		screen = w;
		shapeRenderer= new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(game.camera.combined);
		shapeRenderer.setAutoShapeType(true);
		
	}

	public void render() {
		renderBackGround();
		renderBlocks();
		renderCharacter();
		renderBoss();

		switch (game.getState()) {
		case LOST:
			gameLost();
			break;
		case PAUSED:
			gamePaused();
			break;
		case READY:
			gameReady();
			break;
		case STARTED:
			renderBalls();
			renderPowerUps();
			break;
		case WON:
			gameWon();
			break;
		}

	}

	private void renderBoss() {
		screen.logic.boss.render(game);
	}

	private void renderCharacter() {
		game.batch.begin();
		// render the character
		game.batch.draw(screen.logic.pj.getTexture(), screen.logic.pj.getX(), screen.logic.pj.getY());
		if(screen.logic.pj.isGlue()){
			if(screen.logic.pj.isBig())
				game.batch.draw(game.assets.bigPjGlue, screen.logic.pj.getX(), screen.logic.pj.getY());
			else
				game.batch.draw(game.assets.pjGlue, screen.logic.pj.getX(), screen.logic.pj.getY());
		}

		game.batch.end();

	}

	private void renderBlocks() {
		game.batch.begin();

		// render the blocks
		for (Block block : screen.logic.blocks) {
			game.batch.draw(block.getTexture(), block.getX(), block.getY());
		}

		game.batch.end();
	}

	private void renderBackGround() {
		game.batch.begin();

		// render the background
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 10; j++) {
				game.batch.draw(game.assets.gameBackground, game.assets.gameBackground.getWidth() * i,
						game.assets.gameBackground.getHeight() * j);
			}
		}

		game.bigFont.draw(game.batch, "MadBlock", Arkaxoid.WIDTH_RES / 2 - 60, Arkaxoid.HEIGHT_RES - 45);
		
		// render the score
		game.batch.draw(game.assets.bossLifeBox, Arkaxoid.WIDTH_RES / 2 - game.assets.score.getWidth() / 2,
				Arkaxoid.HEIGHT_RES - 130);
		
	
		// render the lifes of the character
		for (int i = 0; i < screen.logic.pj.getLives(); i++) {
			game.batch.draw(game.assets.life, i * game.assets.life.getWidth() + 15,
					Arkaxoid.HEIGHT_RES - 135, 30, 30);
		}

		game.bigFont.draw(game.batch, "Time", 30, Arkaxoid.HEIGHT_RES - 55);
		game.bigFont.draw(game.batch, screen.logic.gameTimeLeft.showTimeLeft(), 30, Arkaxoid.HEIGHT_RES - 80);
		screen.logic.gameTimeLeft.update();

		game.batch.end();
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(Arkaxoid.WIDTH_RES / 2 - game.assets.score.getWidth() / 2 + 2, Arkaxoid.HEIGHT_RES - 95-30, (float)(screen.logic.boss.getLife()*(215/MadBlock.MAX_LIFE+1)), 54);
		shapeRenderer.end();
	}

	private void renderBalls() {
		game.batch.begin();
		
		for (Ball b : screen.logic.balls) {
			game.batch.draw(b.getTexture(), b.getX(), b.getY());
		}
		
		game.batch.end();
	}
	
	private void renderPowerUps() {
		game.batch.begin();

		for (PowerUp powerUp : screen.logic.powerUps) {
			game.batch.draw(powerUp.getTexture(), powerUp.getX(), powerUp.getY());
		}

		game.batch.end();
	}

	private void gamePaused() {
		screen.pauseScreen.render(game, game.camera, screen.logic.room);
	}

	private void gameWon() {
		
		
		
		if(screen.logic.boss.isDead()){
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(Color.BLACK);
			shapeRenderer.rect(Arkaxoid.WIDTH_RES/2 - 100, Arkaxoid.HEIGHT_RES/2-150,250,200);
			shapeRenderer.end();
			
			game.batch.begin();
			
			
			game.font.draw(game.batch, "Congratulations!!!", Arkaxoid.WIDTH_RES / 3, Arkaxoid.HEIGHT_RES / 2);
			game.font.draw(game.batch, "You have passed the room", Arkaxoid.WIDTH_RES / 3, Arkaxoid.HEIGHT_RES / 2 - 30);
			game.font.draw(game.batch, "Score Points : " + screen.logic.score, Arkaxoid.WIDTH_RES / 3,
					Arkaxoid.HEIGHT_RES / 2 - 55);
			game.font.draw(game.batch, "Life Points : " + screen.logic.pj.getLives() * 10, Arkaxoid.WIDTH_RES / 3,
					Arkaxoid.HEIGHT_RES / 2 - 70);
			game.font.draw(game.batch, "Time Points : " + screen.logic.gameTimeLeft.showTimeIsSeconds() * 10,
					Arkaxoid.WIDTH_RES / 3, Arkaxoid.HEIGHT_RES / 2 - 85);
			game.font.draw(game.batch,
					"Total Points : " + (screen.logic.gameTimeLeft.showTimeIsSeconds() * 10 + screen.logic.pj.getLives() * 10 + screen.logic.score),
					Arkaxoid.WIDTH_RES / 3, Arkaxoid.HEIGHT_RES / 2 - 110);
			
			game.batch.end();
		}				
		
	}

	private void gameLost() {
		game.batch.begin();
		
		game.font.draw(game.batch, "Sorry!!", Arkaxoid.WIDTH_RES / 3 - 20, Arkaxoid.HEIGHT_RES / 2);
		game.font.draw(game.batch, "You haven't passed the room, try again", Arkaxoid.WIDTH_RES / 3 - 80,
				Arkaxoid.HEIGHT_RES / 2 - 30);
		
		game.batch.end();
	}

	private void gameReady() {
		game.batch.begin();
		
		game.font.draw(game.batch, (screen.logic.timeToStart.showTimeIsSeconds()) + "",
				Arkaxoid.WIDTH_RES / 2, Arkaxoid.HEIGHT_RES / 4);
		
		game.batch.end();
	}
}
