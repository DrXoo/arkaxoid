package com.mygdx.arkaxoid.screens.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.arkaxoid.Arkaxoid;
import com.mygdx.arkaxoid.gameObjectTypes.Ball;
import com.mygdx.arkaxoid.gameObjectTypes.Block;
import com.mygdx.arkaxoid.gameObjectTypes.GameObjectType;
import com.mygdx.arkaxoid.gameObjectTypes.MadBlock;
import com.mygdx.arkaxoid.gameObjectTypes.Player;
import com.mygdx.arkaxoid.gameObjectTypes.PowerUp;
import com.mygdx.arkaxoid.gameObjectTypes.Room;
import com.mygdx.arkaxoid.managers.BallManager;
import com.mygdx.arkaxoid.managers.BlockManager;
import com.mygdx.arkaxoid.managers.MadBlockManager;
import com.mygdx.arkaxoid.managers.PlayerManager;
import com.mygdx.arkaxoid.managers.PowerUpManager;
import com.mygdx.arkaxoid.screens.ChooseRoomScreen;
import com.mygdx.arkaxoid.utils.BlockType;
import com.mygdx.arkaxoid.utils.GameStateType;
import com.mygdx.arkaxoid.utils.GameUtils;
import com.mygdx.arkaxoid.utils.PowerUpType;
import com.mygdx.arkaxoid.utils.Timer;

public class BossScreenLogic implements ContactListener {
	public final Arkaxoid game;

	// List of the blocks in the game for rendering
	public Array<Block> blocks;

	// Score of the game
	public int score;

	public final Room room;

	// The character of the game
	public Player pj;

	// Boss
	public MadBlock boss;

	// The balls of the game
	public Array<Ball> balls;

	// For the movement of the character
	public Vector3 touchPos;

	// The world of our Box2D entity
	public World world;

	public Timer timeToStart;
	public Timer gameTimeLeft;

	public Array<PowerUp> powerUps;

	public BossScreenLogic(Arkaxoid game, Room room) {
		this.room = room;
		this.game = game;

		// Load world -------------------
		// No gravity at all
		world = new World(new Vector2(0, 0), true);
		world.setContactListener(this);
		// ------------------------------

		powerUps = new Array<PowerUp>();

		balls = new Array<Ball>();

		// Load the character ------------

		float initialX = Arkaxoid.WIDTH_RES / 2 - game.assets.pj.getWidth() / 2;
		float initialY = 150;

		// create a character
		pj = PlayerManager.createPlayer(initialX, initialY, game.assets.pj, world);
		
		pj.setLimitsOfMovement(room.getPlayerLimitOfMovement()[0], room.getPlayerLimitOfMovement()[1]);
		pj.setLives(3);
		score = 0;

		// ---------------------------------

		// Load the Boss -------------------
		// Initial Position
		initialX = Arkaxoid.WIDTH_RES / 2 - 35 / 2;
		initialY = 300;

		boss = MadBlockManager.createBossMadBlock(initialX, initialY, game.assets.madBlock, world);
		boss.setLoop(true);
		// -------------------------------------

		// Load all blocks ----------------------
		blocks = new Array<Block>();
		blocks = BlockManager.loadBlocksFromFile(game, "levels/" + room.getName(), world);
		// ---------------------------------------------

		// Load the walls -----------------------------

		// At the beginning of the game, the player has not win neither loose
		// the game

		timeToStart = new Timer(0, 3, 1);
		timeToStart.start();
		gameTimeLeft = new Timer(3, 1, 1);

		touchPos = new Vector3();
		
		PowerUpManager.setProbs(room.getProbabilitiesOfPowerUps()[0], room.getProbabilitiesOfPowerUps()[1], room.getProbabilitiesOfPowerUps()[2]);

		score = 5000;
	}

	public void update(float delta) {
		// If there is any blocks to destroy here we dispose them
		destroyBlocks();

		switch (game.getState()) {
		case LOST:
			if (Gdx.input.justTouched()) {
				game.setScreen(new ChooseRoomScreen(game));
			}	
			break;
		case PAUSED:
			break;
		case READY:
			if (timeToStart.isFinish()) {
				gameTimeLeft.start();
				game.setState(GameStateType.STARTED);
			}
			timeToStart.update();
			break;
		case STARTED:
			updateCharacter(delta);
			updateBoss();
			world.step(Gdx.graphics.getDeltaTime(), 6, 2);
			updateBalls();
			updatePowerUps();
			break;
		case WON:
			gameTimeLeft.stop();
			if (boss.isDead() && Gdx.input.justTouched()) {
				game.progress.saveProgress();
				game.setScreen(new ChooseRoomScreen(game));
			}
			updateBoss();
			world.step(Gdx.graphics.getDeltaTime(), 6, 2);
			break;
		}
	}

	private void updateBoss() {
		boss.updateSpritePositionFromBody();

		boss.update(this);

		if(boss.getLife()<=0){
			game.setState(GameStateType.WON);
			GameUtils.removeAllBodiesFromBalls(balls, world);
			GameUtils.removeAllBodiesFromBalls(boss.getMadBalls(),world);
			
			balls.clear();
			boss.getMadBalls().clear();
		}
	}

	private void updateBalls() {
		for (Ball ball : balls) {
			ball.updateSpritePositionFromBody();
			if (!ball.isInMovement()) {
				ball.applyInitialImpulse();
				ball.setInMovement(true);
			}

			if (ball.isAttachToCharacter()) {
				ball.getBody().setLinearVelocity(0, 0);
				ball.getBody().setTransform(pj.getPhysicalX() + ball.getDistanceAttached(), pj.getPhysicalY() + 0.5f,
						0);
			}
			
			if(ball.getBody().getUserData().equals(GameObjectType.DELETABLE)){
				world.destroyBody(ball.getBody());
				balls.removeValue(ball, true);
			}
		}
	}

	private void updatePowerUps() {
		for (PowerUp powerUp : powerUps) {
			
			powerUp.update();
			
			if (powerUp.getY() < 0) {
				world.destroyBody(powerUp.getBody());
				powerUps.removeValue(powerUp, false);
			} else {
				if (powerUp.getBody().getUserData().equals(GameObjectType.DELETABLE)) {
					world.destroyBody(powerUp.getBody());
					powerUps.removeValue(powerUp, false);
					if (powerUp.getType().equals(PowerUpType.EXTRABALL)) {
						Ball newBall = BallManager.createBall(powerUp.getX() + powerUp.getWidth() / 2,
								pj.getY() + pj.getHeight() + game.assets.texture_ball.getHeight(),
								game.assets.texture_ball, world,
								new Vector2(
										pj.getInitialPosition().x + pj.getWidth() / 2
												- game.assets.texture_ball.getWidth() / 2,
										pj.getInitialPosition().y + pj.getHeight()
												+ game.assets.texture_ball.getHeight()));
						balls.add(newBall);
					} else if (powerUp.getType().equals(PowerUpType.BIGPJ))
						pj = PlayerManager.setToBigPj(world, pj, game.assets.bigPj);
					else
						pj.setGlue(true);
				}
			}
		}
	}

	private void updateCharacter(float delta) {
		// Updates the position of the character
		pj.updateSpritePositionFromBody();
		// If the player is touching the screen or clicking with his mouse
		if (Gdx.input.isTouched()) {
			// We wet the position of that touch/click
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			// Transforms it to our camera settings
			game.camera.unproject(touchPos);
			if (touchPos.y < 130) {
				pj.updateMovement(touchPos, delta);
			} else if (touchPos.y > 130) {
				releaseAttachedBalls(touchPos.x, touchPos.y);
			}
		}
		
		if(pj.getLives()<=0){
			game.setState(GameStateType.LOST);
		}
	}

	private void releaseAttachedBalls(float x, float y) {
		for (Ball b : balls) {
			if (b.isAttachToCharacter()) {
				b.setAttachToCharacter(false);
				b.applySpecifiedImpulseFromPosition(x, y, Ball.SPEED_X);
				pj.setGlue(false);
			}
		}
	}

	private void destroyBlocks() {
		// For the blocks to be removed we check if any
		// of the blocks is deleteable
		for (Block b : blocks) {
			if (b.getBody().getUserData().equals(GameObjectType.DELETABLE)) {
				if (b.getType().equals(BlockType.NORMAL)) {
					// if it is, we destroy it from our physics world
					world.destroyBody(b.getBody());
					// and remove it from our array
					blocks.removeValue(b, false);
					score += BlockManager.SCORE_PER_NORMALBLOCK;
					if (MathUtils.randomBoolean(PowerUpManager.probCreatePowerUp)) {
						PowerUp powerUp = PowerUpManager.createPowerUp(b.getX() + b.getWidth() / 2, b.getY(), world,
								game);
						powerUps.add(powerUp);
					}
				} else if (b.getType().equals(BlockType.HARD)) {
					if (b.canBeRemoved()) {
						// if it is, we destroy it from our physics world
						world.destroyBody(b.getBody());
						// and remove it from our array
						blocks.removeValue(b, false);
						score += BlockManager.SCORE_PER_HARDBLOCK;
						if (MathUtils.randomBoolean(PowerUpManager.probCreatePowerUp)) {
							PowerUp powerUp = PowerUpManager.createPowerUp(b.getX() + b.getWidth() / 2, b.getY(), world,
									game);
							powerUps.add(powerUp);
						}
					} else {
						b.nextFrame();
						b.getBody().setUserData(GameObjectType.BLOCK);
					}
				}

			}
		}

		// This solution came up because it is forbidden to destroy bodies in
		// the
		// collision methods
	}

	@Override
	public void beginContact(Contact contact) {

		// Get the userData for both bodies
		Object userDataA = contact.getFixtureA().getBody().getUserData();
		Object userDataB = contact.getFixtureB().getBody().getUserData();

		// Check that is a collision related to two bodies that has a game
		// object type
		if (userDataA != null && userDataB != null) {
			// There are three possibilities
			// 1.- The ball collides with a block
			if ((GameObjectType.BALL.equals(userDataA) || GameObjectType.BALL.equals(userDataB))
					&& (GameObjectType.BLOCK.equals(userDataA) || GameObjectType.BLOCK.equals(userDataB))) {
				Body body;
				if (GameObjectType.BLOCK.equals(userDataA))
					body = contact.getFixtureA().getBody();
				else
					body = contact.getFixtureB().getBody();
				// We set the body of the block like deletable because
				// we are going to delete it
				body.setUserData(GameObjectType.DELETABLE);
				game.assets.hitBlock.play();
				game.progress.setTotalBlocksHit((Integer.parseInt(game.progress.getTotalBlocksHit()) + 1) + "");
			}

			// 2.- The ball collides with the character
			if ((GameObjectType.BALL.equals(userDataA) || GameObjectType.BALL.equals(userDataB))
					&& (GameObjectType.CHARACTER.equals(userDataA) || GameObjectType.CHARACTER.equals(userDataB))) {
				// Get who is who
				Body ball_body;
				Body character_body;
				if (GameObjectType.BALL.equals(userDataA)) {
					ball_body = contact.getFixtureA().getBody();
					character_body = contact.getFixtureB().getBody();
				} else {
					ball_body = contact.getFixtureB().getBody();
					character_body = contact.getFixtureA().getBody();
				}

				if (pj.isGlue()) {
					Ball ball = BallManager.findByBody(ball_body, balls);
					ball.setAttachToCharacter(true);
					ball.setDistanceAttached(ball_body.getPosition().x - character_body.getPosition().x);

				} else {
					// Okay here is some magic:
					// when you the ball collides with the character it does
					// in a certain "collision position"
					// We are going to use that position to do a
					// "relative" distance to the center of the character
					// in order to give the ball a certain X velocity impulse
					// if the ball collides in the center of the character
					// that X velocity is near to 0
					// but if the ball collides in the most distant point
					// from the center of the character
					// X velocity is at his maximum value
					float distanceFromBallXToCharacterX = Math
							.abs(ball_body.getPosition().x - character_body.getPosition().x);
					float proportionOfXVelocity = distanceFromBallXToCharacterX
							/ (pj.getWidth() / 2 / Arkaxoid.PIXELS_TO_METERS);
					int direction = -1;
					if (ball_body.getPosition().x > character_body.getPosition().x)
						direction = 1;
					// A patch (needs to reset the ball velocity to 0 'cause
					// box2d believe that the next impulse must overlap the
					// previous)
					ball_body.setLinearVelocity(0, 0);
					// Set the new impulse
					ball_body.applyLinearImpulse(
							new Vector2(direction * proportionOfXVelocity * Ball.SPEED_X, Ball.SPEED_Y),
							new Vector2(ball_body.getPosition().x, ball_body.getPosition().y), true);
					game.assets.hitCharacter.play();
				}
			}

			if ((GameObjectType.BALL.equals(userDataA) || GameObjectType.BALL.equals(userDataB))
					&& (GameObjectType.MADBLOCK.equals(userDataA) || GameObjectType.MADBLOCK.equals(userDataB))) {
				Body ball_body;
				if (GameObjectType.BALL.equals(userDataA)) {
					ball_body = contact.getFixtureA().getBody();
				} else {
					ball_body = contact.getFixtureB().getBody();
				}

				boss.impactWithBall(ball_body);
			}
			
			if ((GameObjectType.ENEMYBALL.equals(userDataA) || GameObjectType.ENEMYBALL.equals(userDataB))
					&& (GameObjectType.CHARACTER.equals(userDataA) || GameObjectType.CHARACTER.equals(userDataB))) {
				pj.setLives(pj.getLives()-1);
				
				Body ball_body;
				if (GameObjectType.ENEMYBALL.equals(userDataA)) {
					ball_body = contact.getFixtureA().getBody();
				} else {
					ball_body = contact.getFixtureB().getBody();
				}
				boss.madBallImpactPLayer(ball_body);
				
			}
			
			// 3.- The powerUp collides with the character
			if ((GameObjectType.POWERUP.equals(userDataA) || GameObjectType.POWERUP.equals(userDataB))
					&& (GameObjectType.CHARACTER.equals(userDataA) || GameObjectType.CHARACTER.equals(userDataB))) {
				// Get who is who
				Body powerUp_body;
				if (GameObjectType.BALL.equals(userDataA)) {
					powerUp_body = contact.getFixtureA().getBody();
				} else {
					powerUp_body = contact.getFixtureB().getBody();
				}

				// mark the powerup as deletable
				powerUp_body.setUserData(GameObjectType.DELETABLE);
			}

		}
	}

	@Override
	public void endContact(Contact contact) {
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}

}
