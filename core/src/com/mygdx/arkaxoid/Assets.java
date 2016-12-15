package com.mygdx.arkaxoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Json;
import com.mygdx.arkaxoid.gameObjectTypes.Room;
import com.mygdx.arkaxoid.utils.GameUtils;

import java.util.Locale;

public class Assets {

	public Texture introBackground;
	public Texture introTitle;

	public Texture titleSelectLevel;

	// The images
	public TextureRegion[] normalBlue;
	public TextureRegion[] normalRed;
	public TextureRegion[] normalPink;
	public TextureRegion[] normalGreen;

	public TextureRegion[] hardBlue;
	public TextureRegion[] hardRed;
	public TextureRegion[] hardPink;
	public TextureRegion[] hardGreen;
	
	public TextureRegion[] wall;

	public TextureRegion[] indestructible;

	public Texture pauseButton;

	public Texture life;

	public Texture pj;
	public Texture bigPj;

	public Texture madBlock;
	public Texture madBall;

	public Texture pjGlue;
	public Texture bigPjGlue;

	public Texture texture_ball;

	public Texture gameBackground;
	public Texture menuBackground;

	public Texture score;
	public Texture bossLifeBox;

	public Texture powerUp_ExtraBall;
	public Texture powerUp_BigPj;
	public Texture powerUp_Glue;

	public Sound hitBlock;
	public Sound hitCharacter;
	public Sound bossLaugh;
	public Sound bossHitLaugh;
	public Sound bossDeath;
	public Sound bossBreathing;
	public Sound bossHitted;
	public Sound bossShoot;

	public Music bossTheme;
	public Music introTheme;

	public Texture roomType1;
	public Texture bossDoor;

	public Texture pauseBackground;

	public Assets() {


		// Power Up Textures
		powerUp_ExtraBall 	= new Texture(Gdx.files.internal("textures/powerUps/powerUp_ExtraBall.png"));
		powerUp_BigPj 		= new Texture(Gdx.files.internal("textures/powerUps/powerUp_BigPj.png"));
		powerUp_Glue 		= new Texture(Gdx.files.internal("textures/powerUps/powerUp_Glue.png"));

		// UI Textures
		introBackground 	= new Texture(Gdx.files.internal("textures/ui/introBackground.png"));
		pauseBackground 	= new Texture(Gdx.files.internal("textures/ui/pauseScreenBackground.png"));
		introTitle 			= new Texture(Gdx.files.internal("textures/ui/introTitle.png"));
		titleSelectLevel 	= new Texture(Gdx.files.internal("textures/ui/titleSelectLevel.png"));
		gameBackground	 	= new Texture(Gdx.files.internal("textures/ui/gameBackground.png"));
		menuBackground 		= new Texture(Gdx.files.internal("textures/ui/chooseRoomBackground.png"));
		score 				= new Texture(Gdx.files.internal("textures/ui/score.png"));
		pauseButton 		= new Texture(Gdx.files.internal("textures/ui/pauseButton.png"));

		// Player Textures
		life 				= new Texture(Gdx.files.internal("textures/player/life.png"));
		pj 					= new Texture(Gdx.files.internal("textures/player/pj.png"));
		bigPj		 		= new Texture(Gdx.files.internal("textures/player/pjBig.png"));
		pjGlue 				= new Texture(Gdx.files.internal("textures/player/pjGlue.png"));
		bigPjGlue 			= new Texture(Gdx.files.internal("textures/player/pjBigGlue.png"));

		// Bosses Textures
		bossLifeBox 		= new Texture(Gdx.files.internal("textures/bosses/bossLifeBox.png"));
		madBlock 			= new Texture(Gdx.files.internal("textures/bosses/madBlockTile.png"));

		// Balls Textures
		madBall 			= new Texture(Gdx.files.internal("textures/balls/madBall.png"));
		texture_ball 		= new Texture(Gdx.files.internal("textures/balls/ball.png"));

		// Rooms Textures
		roomType1 			= new Texture(Gdx.files.internal("textures/rooms/roomType1.png"));
		bossDoor 			= new Texture(Gdx.files.internal("textures/rooms/bossDoor.png"));

		// Block Textures
		Texture tileBlock 	= new Texture(Gdx.files.internal("textures/blocks/blockTile.png"));

		// Load block textures from their Tile
		normalBlue = new TextureRegion[] {new TextureRegion(tileBlock,0,0,25,25)} ;
		normalPink = new TextureRegion[] {new TextureRegion(tileBlock,25,0,25,25)} ;
		normalGreen = new TextureRegion[] {new TextureRegion(tileBlock,50,0,25,25)} ;
		normalRed = new TextureRegion[] {new TextureRegion(tileBlock,75,0,25,25)} ;
		
		hardBlue = new TextureRegion[] {new TextureRegion(tileBlock,100,0,25,25),new TextureRegion(tileBlock,125,0,25,25)} ;	
		hardPink = new TextureRegion[] {new TextureRegion(tileBlock,150,0,25,25),new TextureRegion(tileBlock,0,25,25,25)} ;
		hardGreen = new TextureRegion[] {new TextureRegion(tileBlock,25,25,25,25),new TextureRegion(tileBlock,50,25,25,25)} ;
		hardRed = new TextureRegion[] {new TextureRegion(tileBlock,75,25,25,25),new TextureRegion(tileBlock,101,25,25,25)} ;
		
		indestructible = new TextureRegion[] {new TextureRegion(tileBlock,126,25,25,25)} ;
		
		wall = new TextureRegion[] {new TextureRegion(tileBlock,151,25,25,25)} ;

		// FX Clips
		hitBlock			= Gdx.audio.newSound(Gdx.files.internal("sounds/fx/hitBlock.wav"));
		hitCharacter		= Gdx.audio.newSound(Gdx.files.internal("sounds/fx/hitCharacter.wav"));
		bossHitLaugh		= Gdx.audio.newSound(Gdx.files.internal("sounds/fx/boss_hit_laugh.ogg"));
		bossBreathing 		= Gdx.audio.newSound(Gdx.files.internal("sounds/fx/boss_breathing.ogg"));
		bossShoot			= Gdx.audio.newSound(Gdx.files.internal("sounds/fx/boss_shoot.ogg"));
		bossLaugh			= Gdx.audio.newSound(Gdx.files.internal("sounds/fx/boss_laugh.wav"));
		bossDeath			= Gdx.audio.newSound(Gdx.files.internal("sounds/fx/boss_death.ogg"));
		bossHitted			= Gdx.audio.newSound(Gdx.files.internal("sounds/fx/boss_hitted.ogg"));

		// Music Clips
		bossTheme 			= Gdx.audio.newMusic(Gdx.files.internal("sounds/music/bossTheme.ogg"));
		introTheme			= Gdx.audio.newMusic(Gdx.files.internal("sounds/music/introTheme.mp3"));

	}


	public void dispose() {

		powerUp_ExtraBall.dispose();

		introBackground.dispose();
		pauseBackground.dispose();
		introTitle.dispose();
		titleSelectLevel.dispose();

		pauseButton.dispose();

		life.dispose();

		pj.dispose();
		bigPj.dispose();

		texture_ball.dispose();

		gameBackground.dispose();

		score.dispose();

		hitBlock.dispose();
		hitCharacter.dispose();

		roomType1.dispose();
		bossDoor.dispose();

	}

	public BitmapFont createNormalFont(){
		return GameUtils.createFont(Gdx.files.internal("settings/Pixel-UniCode.ttf"), 20, 1, Color.BLACK);
	}

	public BitmapFont createBigFont(){
		return GameUtils.createFont(Gdx.files.internal("settings/Pixel-UniCode.ttf"), 12, 2, Color.BLACK);
	}

	public I18NBundle loadBundle(){
		FileHandle baseFileHandle =  Gdx.files.internal("settings/bundle");
		return I18NBundle.createBundle(baseFileHandle, Locale.ENGLISH);
	}

	public FileHandle loadFile(String filePath){
		return Gdx.files.internal(filePath);
	}

	public Progress loadProgress(Array<RoomWrapper> configRooms){
		Progress result;
		FileHandle file = Gdx.files.local("progress");

		if (!file.exists()) {
			result = new Progress();

			result.setTotalBlocksHit("0");
			result.setInitialScoreRooms(configRooms);

			result.saveProgress();

		} else {
			Json json = new Json();
			result = json.fromJson(Progress.class, file);
		}
		return result;
	}
}
