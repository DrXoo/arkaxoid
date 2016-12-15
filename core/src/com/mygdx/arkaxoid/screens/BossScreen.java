package com.mygdx.arkaxoid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.arkaxoid.Arkaxoid;
import com.mygdx.arkaxoid.gameObjectTypes.Room;
import com.mygdx.arkaxoid.screens.logic.BossScreenLogic;
import com.mygdx.arkaxoid.screens.render.BossScreenRenderer;
import com.mygdx.arkaxoid.utils.GameStateType;

public class BossScreen implements Screen {
	final Arkaxoid game;
	final Room room;

	// DebugMode of Box2D
	private Box2DDebugRenderer debugRenderer;
	private Matrix4 debugMatrix;
	private boolean enableDebugMode;

	public PauseInsideScreen pauseScreen;
	public BossScreenRenderer renderer;
	public BossScreenLogic logic;

	public BossScreen(final Arkaxoid gam, final Room room) {
		this.game = gam;
		this.room = room;
		game.setState(GameStateType.READY);
		logic = new BossScreenLogic(gam, room);
 
		// Create our Debug rendered and settings
		debugRenderer = new Box2DDebugRenderer();
		debugMatrix = game.batch.getProjectionMatrix().cpy().scale(Arkaxoid.PIXELS_TO_METERS, Arkaxoid.PIXELS_TO_METERS,
				0);
		enableDebugMode = false;
		// -------------------------------------------
		
		pauseScreen = new PauseInsideScreen();

		game.changeBackgroundMusic(Arkaxoid.assets.bossTheme);
		game.setBackgroundMusicVolume(0.3f);

		renderer = new BossScreenRenderer(game, this);
	}

	@Override
	public void render(float delta) {
		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// tell the camera to update its matrices.
		game.camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(game.camera.combined);
		
		enableDisableDebugMode();
		renderer.render();
		
		// Show the debug mode (only on PC)
		if (enableDebugMode)
			debugRenderer.render(logic.world, debugMatrix);

		// The logic of our game
		logic.update(delta);
	}
	
	private void enableDisableDebugMode(){
		if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
			if (!enableDebugMode)
				enableDebugMode = true;
			else if (enableDebugMode)
				enableDebugMode = false;
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