package characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

public abstract class Character {

	protected float animationSpeed;
	protected float renderWidth;
	protected float renderHeight;
	protected int widthPixel;
	protected int heightPixel;

	protected float stateTime;

	protected Vector2 position;
	protected Vector2 velocity;
	protected float speed;

	protected Animation<TextureRegion>[] animation;
	protected int roll;
	protected int maxHp;
	protected int hp;

	public Character() {
		stateTime = 0;
	}

	public float getAnimationSpeed() {
		return animationSpeed;
	}

	public void setAnimationSpeed(float animationSpeed) {
		this.animationSpeed = animationSpeed;
	}

	public float getRenderWidth() {
		return renderWidth;
	}

	public void setRenderWidth(float renderWidth) {
		this.renderWidth = renderWidth;
	}

	public float getRenderHeight() {
		return renderHeight;
	}

	public void setRenderHeight(float renderHeight) {
		this.renderHeight = renderHeight;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(float x, float y) {
		this.position = new Vector2(x, y);
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(float x, float y) {
		this.velocity = new Vector2(x, y);
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getRoll() {
		return roll;
	}

	public void setRoll(int roll) {
		this.roll = roll;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public Animation<TextureRegion> getAnimation() {
		return animation[roll];
	}

	public void update(float dt) {
		velocity.scl(dt);
		position.add(velocity.x, velocity.y);

		if (position.x < 0) {
			position.x = 0;
		}
		if (position.y < 0) {
			position.y = 0;
		}

		velocity.scl(1 / dt);
	}

	public int getHP() {
		return this.hp;
	}

	public void setHP(int hp) {
		this.hp = hp;
	}

	public void addHP(int hp) {
		this.hp += hp;
		if (this.hp < 0) {
			this.hp = 0;
		}
		if (this.hp > maxHp) {
			this.hp = maxHp;
		}
	}

	public double getAngle() {
		return 0;
	}
}
