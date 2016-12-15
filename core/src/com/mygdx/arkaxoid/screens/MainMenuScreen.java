package com.mygdx.arkaxoid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.arkaxoid.Arkaxoid;

public class MainMenuScreen implements Screen {

	final Arkaxoid game;
	
	private OrthographicCamera camera;

	public MainMenuScreen(final Arkaxoid gam) {
		game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Arkaxoid.WIDTH_RES, Arkaxoid.HEIGHT_RES);

		game.changeBackgroundMusic(Arkaxoid.assets.introTheme);
		game.setBackgroundMusicVolume(0.4f);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(game.assets.introBackground, 0, 0);
		game.batch.draw(game.assets.introTitle, Arkaxoid.WIDTH_RES/2 - game.assets.introTitle.getWidth()/2, Arkaxoid.HEIGHT_RES/2 + game.assets.introTitle.getHeight() +30);
		game.batch.end();

		if (Gdx.input.justTouched()) {
			game.setScreen(new ChooseRoomScreen(game));
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}