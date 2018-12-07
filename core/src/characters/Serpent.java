package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import effects.DamageEffect;

public class Serpent extends Enemy {

	private MainCharacter player;
	private Sprite sprite;
	private TextureRegion[][] rollSpriteSheet;
	private boolean isPlayerDead;
	private double prw;
	private double prh;
	private Sound serpantSound;
	private boolean isSoundPlayed; 

	public Serpent(int x, int y, MainCharacter player, int hp) {
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

		this.hp = maxHp = hp;
		serpantSound = Gdx.audio.newSound(Gdx.files.internal("music/SerpantSound.mp3"));
		isSoundPlayed = false;
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
				player.addEffect(new DamageEffect((int) (player.getPosition().x + (player.getRenderWidth() / 2) - 6),
						(int) (player.getPosition().y + player.getRenderHeight() - 15), 20, 2, player));
				if (player.hp <= 0) {
					isPlayerDead = true;
				}
				isDestroyed = true;
			}
		} else {
			roll = 1;
			if(!isSoundPlayed) {
				serpantSound.play();
				isSoundPlayed = true;
			}
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
