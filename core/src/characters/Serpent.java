package characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Serpent extends Enemy {

	private MainCharacter player;
	private Sprite sprite;
	private TextureRegion[][] rollSpriteSheet;
	private boolean isPlayerDead;
	private double prw;
	private double prh;

	public Serpent(int x, int y, MainCharacter player) {
		animationSpeed = 0.15f;
		renderWidth = 16;
		renderHeight = 16;
		widthPixel = 32;
		heightPixel = 32;

		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		speed = 50;

		animation = new Animation[2];
		roll = 0;

		TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("monster/Serpent.png"), widthPixel,
				heightPixel);

		for (int i = 0; i < 2; i++) {
			animation[i] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[i]);
		}

		this.player = player;
		prw = player.getRenderWidth();
		prh = player.getRenderHeight();

		isPlayerDead = false;

		hp = maxHp = 2;
	}

	double dx;
	double dy;
	double ds;
	double pushDegree = 0;
	double timerDegree = 0;
	int pushForce = 200;

	@Override
	public void update(float dt) {
		stateTime += dt;
		
		if (hp > 0) {
			dx = player.getPosition().x - position.x - (renderWidth / 2) + (prw / 2);
			dy = player.getPosition().y - position.y - (renderHeight / 2) + (prh / 2);
			ds = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
			velocity.x = (float) (dx / ds) * speed;
			velocity.y = (float) (dy / ds) * speed;
			if (timerDegree > 0) {
				velocity.x += pushForce * Math.sin(timerDegree) * Math.cos(pushDegree);
				velocity.y += pushForce * Math.sin(timerDegree) * Math.sin(pushDegree);
				timerDegree -= Math.toRadians(3);
			}
			velocity.scl(dt);
			position.add(velocity.x, velocity.y);
			velocity.scl(1 / dt);
			if (ds < 5) {
				player.addHP(-20);
				if (player.hp <= 0) {
					isPlayerDead = true;
				}
				isDestroyed = true;
			}
		} else {
			roll = 1;
			if (getAnimation().isAnimationFinished(stateTime)) {
				isDestroyed = true;
			}
		}
	}

	public boolean isPlayerDead() {
		return isPlayerDead;
	}

	@Override
	public void push(double degree) {
		this.pushDegree = Math.toRadians(degree);
		this.timerDegree = Math.PI / 2;
	}
	
	@Override
	public double getAngle() {
		float angle = (float) Math.toDegrees(Math.atan2(dy, dx));
		if (angle < 0) {
			angle += 360;
		}
		return angle;
	}
}
