package com.mygdx.arkaxoid.gameObjectTypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.arkaxoid.Arkaxoid;
import com.mygdx.arkaxoid.managers.BallManager;
import com.mygdx.arkaxoid.screens.logic.BossScreenLogic;
import com.mygdx.arkaxoid.utils.AnimationManager;
import com.mygdx.arkaxoid.utils.Timer;

public class MadBlock extends GameObjectScripted {

	public static final float SPEED_X = 0.15f;
	public static final float SPEED_Y = 0.15f;

	private static final int REST = 0;
	private static final int LOOKDOWN = 1;
	private static final int LOOKLEFT = 2;
	private static final int LOOKRIGHT = 3;
	private static final int LAUGH = 4;
	private static final int HIT = 5;
	private static final int DIE = 6;

	private Array<Ball> madBalls;

	private Timer timeToSpawnMadBalls;
	private Timer timeToMoveFromStandby;

	public static final int MAX_LIFE = 12;
	private int life;
	private boolean gotHit;
	private boolean playerGotHit;
	private boolean dead;
	private Timer timeAnimationHit;

	private AnimationManager animationManager;

	private Music bossLaugh;
	private Sound bossHitLaugh;
	private Music bossHitted;
	private Music bossDeath;
	private Sound bossShoot;
	private Sound bossBreathing;
	private boolean breathing;

	public MadBlock(Texture t, Vector2 p, Body b, Shape shape) {
		super(t, p, b, shape, 0.1f, 1f, 1f, true);
		b.setFixedRotation(false);
		madBalls = new Array<Ball>();

		Array<Animation> animations = new Array<Animation>();

		TextureRegion[][] tmp = TextureRegion.split(t, 35,
				35);
		Array<TextureRegion> aux = new Array<TextureRegion>();

		aux.add(tmp[0][0]);
		Animation rest = new Animation(0f, aux);
		animations.add(rest);

		aux.clear();
		aux.add(tmp[0][1]);
		Animation lookDown = new Animation(0f, aux);
		animations.add(lookDown);

		aux.clear();
		aux.add(tmp[0][2]);
		Animation lookLeft = new Animation(0f, aux);
		animations.add(lookLeft);

		aux.clear();
		aux.add(tmp[0][3]);
		Animation lookRight = new Animation(0f, aux);
		animations.add(lookRight);

		aux.clear();
		aux.add(tmp[0][4]);
		aux.add(tmp[1][0]);
		Animation laugh = new Animation(0.2f, aux);
		animations.add(laugh);

		aux.clear();
		aux.add(tmp[1][1]);
		aux.add(tmp[1][2]);
		Animation getHit = new Animation(0.05f, aux);
		animations.add(getHit);

		aux.clear();
		aux.add(tmp[1][3]);
		aux.add(tmp[1][4]);
		aux.add(tmp[2][0]);
		aux.add(tmp[2][1]);
		aux.add(tmp[2][2]);
		aux.add(tmp[2][3]);
		Animation die = new Animation(0.75f, aux);
		animations.add(die);

		animationManager = new AnimationManager(animations);
		animationManager.changeAnimation(REST);
		setTexture(animationManager.getCurrentFrame());

		timeToSpawnMadBalls = new Timer(0, 4, 1);
		timeToMoveFromStandby = new Timer(0, 3, 1);
		timeAnimationHit = new Timer(0, 1, 1);

		gotHit = false;
		dead = false;
		playerGotHit = false;

		life = MAX_LIFE;

		bossHitLaugh		= Gdx.audio.newSound(Gdx.files.internal("sounds/fx/boss_hit_laugh.ogg"));
		bossBreathing 		= Gdx.audio.newSound(Gdx.files.internal("sounds/fx/boss_breathing.ogg"));
		bossShoot			= Gdx.audio.newSound(Gdx.files.internal("sounds/fx/boss_shoot.ogg"));

		bossLaugh			= Gdx.audio.newMusic(Gdx.files.internal("sounds/fx/boss_laugh.wav"));
		bossDeath			= Gdx.audio.newMusic(Gdx.files.internal("sounds/fx/boss_death.ogg"));
		bossHitted			= Gdx.audio.newMusic(Gdx.files.internal("sounds/fx/boss_hitted.ogg"));

		bossLaugh.setLooping(false);
		bossLaugh.setVolume(0.5f);
		bossDeath.setLooping(false);
		bossDeath.setVolume(0.5f);
		bossHitted.setLooping(false);
		bossHitted.setVolume(0.5f);
		breathing = false;
	}

	public void update(BossScreenLogic logic) {
		if (life > 0) {
			super.updateMovement();

			if (super.getState() == GameObjectScripted.START_MOVEMENT) {

				if (!gotHit && !playerGotHit) {
					if(!breathing){
						bossBreathing.loop(0.5f);
						breathing = true;
					}

					if (logic.pj.getX() + logic.pj.getWidth() < getX())
						animationManager.changeAnimation(LOOKLEFT);
					else if (logic.pj.getX() > getX() + logic.pj.getWidth())
						animationManager.changeAnimation(LOOKRIGHT);
					else
						animationManager.changeAnimation(LOOKDOWN);
				} else {
					if(breathing){
						bossBreathing.stop();
						breathing = false;
					}
					if (!timeAnimationHit.hasStarted()) {
						timeAnimationHit.start();
						if (gotHit){
							animationManager.changeAnimation(HIT);
							bossHitted.play();
						}
						else{
							animationManager.changeAnimation(LAUGH);
							bossHitLaugh.play(0.5f);
						}
					} else {
						if (timeAnimationHit.isFinish()) {
							gotHit = false;
							playerGotHit = false;
							timeAnimationHit.setTime(0, 1, 1);
						}
						timeAnimationHit.update();
					}
				}

				animationManager.updateAnimation();
				setTexture(animationManager.getCurrentFrame());

				if (!timeToSpawnMadBalls.hasStarted()) {
					timeToSpawnMadBalls.start();
				} else {
					if (timeToSpawnMadBalls.isFinish()) {
						if (madBalls.size > 0) {
							Ball b = madBalls.get(MathUtils.random(0, madBalls.size - 1));
							float x = b.getBody().getLinearVelocity().x / Arkaxoid.PIXELS_TO_METERS;
							float y = b.getBody().getLinearVelocity().y / Arkaxoid.PIXELS_TO_METERS;
							Ball newBall = BallManager.createBall(b.getX(), b.getY(), logic.game.assets.texture_ball,
									logic.world, b.getInitialPosition());

							newBall.applyVector2Impulse(new Vector2(x, y));
							newBall.setInMovement(true);
							logic.balls.add(newBall);

							logic.world.destroyBody(b.getBody());
							madBalls.removeValue(b, true);
						}
						if (madBalls.size + logic.balls.size < 5)
							shootBalls(logic.game.assets.madBall, logic.world);
						timeToSpawnMadBalls.stop();
						timeToSpawnMadBalls.setTime(0, 4, 1);
					}

					timeToSpawnMadBalls.update();
				}

			} else {

				if (animationManager.getIndexOfCurrentAnimation() != LAUGH) {
					animationManager.changeAnimation(LAUGH);
					bossLaugh.play();
				}
				animationManager.updateAnimation();
				setTexture(animationManager.getCurrentFrame());

				if (!timeToMoveFromStandby.hasStarted()) {
					timeToMoveFromStandby.start();
				} else {
					if (timeToMoveFromStandby.isFinish()) {
						super.setState(GameObjectScripted.START_MOVEMENT);
					}
					timeToMoveFromStandby.update();
				}
			}

			if (madBalls.size > 0) {
				for (Ball ball : madBalls) {
					ball.updateSpritePositionFromBody();

					if (ball.getBody().getUserData().equals(GameObjectType.DELETABLE)) {
						logic.world.destroyBody(ball.getBody());
						madBalls.removeValue(ball, true);
					}
				}
			}
		}else{
			if(breathing){
				bossBreathing.stop();
				breathing = false;
			}

			if (getPosition().dst(getInitialPosition()) < 2f) {
				setPosition(getInitialPosition());
				getBody().setLinearVelocity(Vector2.Zero);
				if (animationManager.getIndexOfCurrentAnimation() != DIE) {
					animationManager.changeAnimation(DIE);
					bossDeath.play();
				}
				
			} else {
				if (getBody().getLinearVelocity().isZero()) {
					applySpecifiedImpulseFromPosition(getInitialPosition().x, getInitialPosition().y, 1f);
				}
			}
			animationManager.updateAnimation();
			setTexture(animationManager.getCurrentFrame());
			
			if(animationManager.getIndexOfCurrentAnimation() == DIE && animationManager.hasFinished()){
				dead=true;
			}
		}
	}

	public Array<Ball> getMadBalls() {
		return madBalls;
	}

	public int getLife() {
		return life;
	}

	public void shootBalls(Texture texture, World world) {
		Ball leftBall = BallManager.createEnemyBall(getX() + getWidth() / 2, getY() - texture.getHeight() / 2, texture,
				world, new Vector2(-Ball.SPEED_X / 2, -Ball.SPEED_Y / 2));
		Ball centerBall = BallManager.createEnemyBall(getX() + getWidth() / 2, getY() - texture.getHeight() / 2,
				texture, world, new Vector2(0, -Ball.SPEED_Y / 2));
		Ball rightBall = BallManager.createEnemyBall(getX() + getWidth() / 2, getY() - texture.getHeight() / 2, texture,
				world, new Vector2(Ball.SPEED_X / 2, -Ball.SPEED_Y / 2));

		bossShoot.play();
		madBalls.add(leftBall);
		madBalls.add(centerBall);
		madBalls.add(rightBall);

	}

	public void render(Arkaxoid game) {
		game.batch.begin();
		// render the character
		game.batch.draw(getTexture(), getX(), getY());

		for (Ball ball : madBalls) {
			game.batch.draw(ball.getTexture(), ball.getX(), ball.getY());
		}

		game.batch.end();
	}

	public void impactWithBall(Body body) {
		life--;
		if (life <= 0){
			life = 0;
			getBody().setLinearVelocity(Vector2.Zero);
			animationManager.changeAnimation(HIT);
		}
			
		gotHit = true;
		body.setUserData(GameObjectType.DELETABLE);
	}

	public void madBallImpactPLayer(Body body) {
		body.setUserData(GameObjectType.DELETABLE);
		playerGotHit = true;
	}

	public boolean isDead() {
		return dead;
	}
}
