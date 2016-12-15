package com.mygdx.arkaxoid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.arkaxoid.Arkaxoid;
import com.mygdx.arkaxoid.gameObjectTypes.Room;

public class PauseInsideScreen {

	private Rectangle restartOption;

	private Rectangle backMenuOption;

	private Rectangle quitOption;

	public PauseInsideScreen() {
		restartOption = new Rectangle(Arkaxoid.WIDTH_RES / 2, Arkaxoid.HEIGHT_RES - 210 , Arkaxoid.WIDTH_RES / 2, 30);

		backMenuOption = new Rectangle(Arkaxoid.WIDTH_RES / 2, Arkaxoid.HEIGHT_RES - 255, Arkaxoid.WIDTH_RES / 2, 30);

		quitOption = new Rectangle(Arkaxoid.WIDTH_RES / 2, Arkaxoid.HEIGHT_RES - 300, Arkaxoid.WIDTH_RES / 2, 30);
	}

	public void render(Arkaxoid game, OrthographicCamera camera, Room room) {
		game.batch.begin();
		
		game.batch.draw(game.assets.pauseBackground, Arkaxoid.WIDTH_RES / 2, Arkaxoid.HEIGHT_RES - 300);
		
		game.batch.end();
		
		checkOption(game,camera, room);
	}

	private void checkOption(Arkaxoid game,OrthographicCamera camera, Room room) {
		if (Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3();
			// We wet the position of that touch/click
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			// Transforms it to our camera settings
			camera.unproject(touchPos);

			if (restartOption.contains(touchPos.x, touchPos.y)) {
				game.setScreen(new GameScreen(game, room));
			}

			if (backMenuOption.contains(touchPos.x, touchPos.y)) {
				game.setScreen(new ChooseRoomScreen(game) );
			}

			if (quitOption.contains(touchPos.x, touchPos.y)) {
				game.dispose();
				Gdx.app.exit();
			}

		}

	}
}
