package characters;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Character implements Destroyable {
	protected MainCharacter player;
	protected TextureRegion[][] rollSpriteSheet;
	protected boolean isPlayerDead;
	protected double prw;
	protected double prh;
	protected boolean isDestroyed;
	protected double pushDegree;
	protected double timerDegree;
	protected int pushForce;
	protected Sound enemySound;
	protected boolean isSoundPlayed;

	public Enemy(int x, int y, MainCharacter player, int hp) {
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		this.player = player;
		prw = player.getRenderWidth();
		prh = player.getRenderHeight();
		isPlayerDead = false;
		this.hp = maxHp = hp;
		isDestroyed = false;
		isSoundPlayed = false;
		pushDegree = 0;
		timerDegree = 0;
		pushForce = 100;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public void push(double degree) {
		this.pushDegree = Math.toRadians(degree);
		this.timerDegree = Math.PI / 2;
	}

	public boolean isPlayerDead() {
		return isPlayerDead;
	}
}
