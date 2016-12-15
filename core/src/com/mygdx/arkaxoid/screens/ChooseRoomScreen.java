package com.mygdx.arkaxoid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.arkaxoid.Arkaxoid;
import com.mygdx.arkaxoid.gameObjectTypes.Room;

public class ChooseRoomScreen implements Screen {

	final Arkaxoid game;

	private Vector3 touchPos;

	private String stringToPrint;

	public ChooseRoomScreen(final Arkaxoid gam) {
		game = gam;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);

		game.batch.begin();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 10; j++) {
				game.batch.draw(game.assets.menuBackground, game.assets.menuBackground.getWidth() * i,
						(game.assets.gameBackground.getHeight() + 70) * j);
			}
		}

		game.batch.draw(game.assets.titleSelectLevel, 40,
				Arkaxoid.HEIGHT_RES - game.assets.titleSelectLevel.getHeight() - 10);
		game.bigFont.draw(game.batch, game.bundle.get("selectRoom"), 80, Arkaxoid.HEIGHT_RES - 30);

		for (Room room : game.rooms) {
			game.batch.draw(room.getTexture(), room.getX(), room.getY());
			stringToPrint = game.bundle.get("room")+" " + room.getName().charAt(room.getName().length() - 1);
			game.font.draw(game.batch, stringToPrint, room.getX() + 23, room.getY() + room.getHeight() - 18);
			if (Integer.parseInt(game.progress.getScoreRooms().get(room.getName())) > 0) {
				room.setCurrentFrame(1);
				game.font.draw(game.batch, game.progress.getScoreRooms().get(room.getName()), room.getX() + 23,
						room.getY() + 30);
			}

		}

		game.batch.end();

		logic();
	}

	private void logic() {

		if (Gdx.input.justTouched()) {
			touchPos = new Vector3();
			// We wet the position of that touch/click
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			// Transforms it to our camera settings
			game.camera.unproject(touchPos);

			for (Room room : game.rooms) {
				if (room.getRectangle().contains(touchPos.x, touchPos.y)) {
					if (!room.hasBoss()) {
						game.setScreen(new GameScreen(game, room));
						dispose();
					} else {
						game.setScreen(new BossScreen(game, room));
						dispose();
					}
				}
			}

		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void show() {
	}

}
