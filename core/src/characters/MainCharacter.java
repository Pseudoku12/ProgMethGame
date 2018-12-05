package characters;

import java.util.ArrayList;

import javax.swing.text.Position;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.gameprogmeth.game.GameProgMeth;

public class MainCharacter extends Character {

	private int score;
	private int damage;
	public boolean isBlockedLeft, isBlockedRight, isBlockedUp, isBlockedDown;
	public Animation<TextureRegion>[] idleAnimation;

	private float slowCounter;

	public MainCharacter(int x, int y, int speed) {
		animationSpeed = 0.1f;
		renderWidth = 63;
		renderHeight = 63;
		widthPixel = 126;
		heightPixel = 126;

		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		this.speed = speed;

		animation = new Animation[8];
		idleAnimation = new Animation[4];
		roll = 2;

		TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("character/Main Character Walk.png"),
				widthPixel, heightPixel);

		for (int i = 0; i < 8; i++) {
			animation[i] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[i]);
		}

		TextureRegion[][] IdleSpriteSheet = new TextureRegion[4][1];
		for (int i = 0; i < 4; i++) {
			IdleSpriteSheet[i][0] = rollSpriteSheet[i][1];
		}

		for (int i = 0; i < 4; i++) {
			idleAnimation[i] = new Animation<TextureRegion>(0, IdleSpriteSheet[i]);
		}

		score = 0;
		hp = maxHp = 100;
		damage = 1;
		slowCounter = 0;
	}

	public void addScore(int score) {
		this.score += score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}

	@Override
	public void update(float dt) {
		stateTime += dt;

		if (slowCounter > 0) {
			velocity.scl(0.5f);
			slowCounter -= dt;
			getAnimation().setFrameDuration(animationSpeed * 2);
		} else {
			getAnimation().setFrameDuration(animationSpeed);
		}
		velocity.scl(dt);
		if (isBlockedLeft && velocity.x < 0) {
			velocity.x = 0;
		}
		if (isBlockedRight && velocity.x >= 0) {
			velocity.x = 0;
		}
		if (isBlockedUp && velocity.y >= 0) {
			velocity.y = 0;
		}
		if (isBlockedDown && velocity.y < 0) {
			velocity.y = 0;
		}
		position.add(velocity.x, velocity.y);
		if (position.x < 0) {
			position.x = 0;
		}
		if (position.y < 0) {
			position.y = 0;
		}

		velocity.scl(1 / dt);
	}

	public void render(SpriteBatch batch) {
		if (getVelocity().x == 0 && getVelocity().y == 0 && getRoll() < 4) {
			batch.draw(getIdleAnimation().getKeyFrame(getStateTime(), false), getPosition().x, getPosition().y,
					getRenderWidth(), getRenderHeight());
		} else {
			batch.draw(getAnimation().getKeyFrame(getStateTime(), true), getPosition().x, getPosition().y,
					getRenderWidth(), getRenderHeight());
		}

		if (getRoll() < 8 && getRoll() > 3 && getAnimation().isAnimationFinished(getStateTime())) {
			setRoll(getRoll() - 4);
		}
	}

	public Animation<TextureRegion> getIdleAnimation() {
		return idleAnimation[roll];
	}

	double dx;
	double dy;
	double ds;
	int sweptAngle = 45;
	double tempAngle;

	public int attack(Enemy enemy) {
		dx = enemy.getPosition().x - position.x - (renderWidth / 2) + (enemy.getRenderWidth() / 2);
		dy = enemy.getPosition().y - position.y - (renderHeight / 2) + (enemy.getRenderHeight() / 2);
		ds = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		tempAngle = getAngle();
		if (ds <= 25 && enemy.getHP() > 0) {
			if ((roll == 4 && 270 - sweptAngle < tempAngle && tempAngle < 270 + sweptAngle)
					|| (roll == 5 && 180 - sweptAngle < tempAngle && tempAngle < 180 + sweptAngle)
					|| (roll == 6 && 0 - sweptAngle < tempAngle && tempAngle < 0 + sweptAngle)
					|| (roll == 7 && 90 - sweptAngle < tempAngle && tempAngle < 90 + sweptAngle)) {
				enemy.push(tempAngle);
				enemy.setStateTime(0);
				return -damage;
			}
		}
		return 0;
	}

	@Override
	public double getAngle() {
		float angle = (float) Math.toDegrees(Math.atan2(dy, dx));
		if (angle < 0) {
			angle += 360;
		}
		return angle;
	}

	public void slow() {
		slowCounter = 3;
	}
}
