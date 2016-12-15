package com.mygdx.arkaxoid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Json;
import com.mygdx.arkaxoid.gameObjectTypes.Room;
import com.mygdx.arkaxoid.screens.MainMenuScreen;
import com.mygdx.arkaxoid.utils.GameStateType;

public class Arkaxoid extends Game {

	// Native Resolution to our game
	public static final int HEIGHT_RES = 800;
	public static final int WIDTH_RES = 480;

	// Static Variable to know how many pixels are one meter
	public static final float PIXELS_TO_METERS = 25f;

	// Sprite Batch to draw our stuff
	public SpriteBatch batch;
	// Normal font of our game
	public BitmapFont font;
	// Big font of our game
	public BitmapFont bigFont;

	// To save and load the player's progress
	public Progress progress;

	// To load all the available rooms
	public Array<Room> rooms;

	// To load all the game assets
	public static Assets assets;

	// The state of the game
	private GameStateType state;

	// Camera
	public OrthographicCamera camera;

	// Localization use
	public I18NBundle bundle;

	private Music backgroundMusic;
	
	public void create() {
		
		assets = new Assets();
		batch = new SpriteBatch();
		
		font = assets.createNormalFont();
		bigFont = assets.createBigFont();

		bundle = assets.loadBundle();

		Json json = new Json();

		rooms = new Array<Room>();

		backgroundMusic = null;

		@SuppressWarnings("unchecked")
		Array<RoomWrapper> configRooms = json.fromJson(Array.class, assets.loadFile("settings/rooms"));

		int colCount = 0;
		int rowCount = 0;
		for (RoomWrapper roomConfig : configRooms) {
			Room room;
			Texture t;
			if (roomConfig.type.equals("1"))
				t = this.assets.roomType1;		
			else
				t = this.assets.bossDoor;

			room = new Room(roomConfig.name,
							t,
							colCount * (assets.roomType1.getWidth()/2 +40 ) + 70,
							Arkaxoid.HEIGHT_RES - 250 - (rowCount*(assets.roomType1.getHeight() + 20)),
							roomConfig.boss, 
							roomConfig.limit,
							roomConfig.probs);
				rooms.add(room);
				colCount++;
				if(colCount == 2){
					rowCount++;
					colCount=0;
				}
		}

		progress = assets.loadProgress(configRooms);

		// Create the camera
		// Our camera's is a orthonormal camera, that means that the z value is
		// 0
		camera = new OrthographicCamera();
		// With the width and the height of our application
		camera.setToOrtho(false, Arkaxoid.WIDTH_RES, Arkaxoid.HEIGHT_RES);
		// ---------------------------------------

		this.setScreen(new MainMenuScreen(this));
	}

	public void dispose() {

		font.dispose();
		bigFont.dispose();
		assets.dispose();
	}

	public GameStateType getState() {
		return state;
	}

	public void setState(GameStateType state) {
		this.state = state;
	}

	public void changeBackgroundMusic(Music music){
		if(backgroundMusic != null)
			backgroundMusic.stop();
		backgroundMusic = music;
		backgroundMusic.setLooping(true);
		backgroundMusic.play();
	}

	public void setBackgroundMusicVolume(float volume){
		if(backgroundMusic != null)
			backgroundMusic.setVolume(volume);
	}



}
