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

import effects.DamageEffect;
import effects.Effect;
import effects.HealEffect;

public class MainCharacter extends Character {

	private int score;
	private int damage;
	public boolean isBlockedLeft, isBlockedRight, isBlockedUp, isBlockedDown;
	public Animation<TextureRegion>[] idleAnimation;
	private ArrayList<Effect> effList;
	private ArrayList<Integer> markForRemoved;

	private float slowCounter;
	private float bleedCounter;
	private float hitTime;

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

		effList = new ArrayList<Effect>();

		score = 0;
		hp = maxHp = 100;
		damage = 1;
		slowCounter = 0;
		bleedCounter = 0;
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
			animationSpeed = 0.2f;
		} else {
			animationSpeed = 0.1f;
		}
		if (bleedCounter > 0) {
			if (hitTime <= 0) {
				this.addHP(-3);
				this.addEffect(new DamageEffect((int) (this.getPosition().x + (this.getRenderWidth() / 2) - 2),
						(int) (this.getPosition().y + this.getRenderHeight() - 15), 3, 2, this));
				hitTime = 1;
			}
			bleedCounter -= dt;
			hitTime -= dt;
		}
		getAnimation().setFrameDuration(animationSpeed);
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
		markForRemoved = new ArrayList<Integer>();
		for (int i = 0; i < effList.size(); i++) {
			if (effList.get(i) != null) {
				effList.get(i).update(dt);
				if (effList.get(i).isDestroyed()) {
					markForRemoved.add(i);
				}
			}
		}
		for (int i = markForRemoved.size() - 1; i >= 0; i--) {
			effList.remove(effList.get(markForRemoved.get(i)));
		}
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

	public void renderEffect(SpriteBatch batch) {
		for (Effect eff : effList) {
			if (eff != null) {
				eff.render(batch);
			}
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
				effList.add(new DamageEffect((int) (enemy.getPosition().x + (enemy.getRenderWidth() / 2)),
						(int) (enemy.getPosition().y + enemy.getRenderHeight() + 10), damage, 1, enemy));
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

	public void addEffect(Effect eff) {
		effList.add(eff);
	}

	public void bleed() {
		bleedCounter = 4.1f;
		hitTime = 1;
	}
}
