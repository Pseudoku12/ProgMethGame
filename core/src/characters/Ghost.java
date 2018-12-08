package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import effects.DamageEffect;

public class Ghost extends Enemy {

	public Ghost(int x, int y, MainCharacter player, int hp) {
		super(x, y, player, hp);
		animationSpeed = 0.25f;
		renderWidth = 16;
		renderHeight = 24;
		widthPixel = 16;
		heightPixel = 24;
		speed = 20;
		animation = new Animation[5];
		roll = 2;
		TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("monster/Ghost.png"), widthPixel,
				heightPixel);
		for (int i = 0; i < 5; i++) {
			animation[i] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[i]);
		}
		enemySound = Gdx.audio.newSound(Gdx.files.internal("music/GhostSound.mp3"));
	}

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
				timerDegree -= Math.toRadians(5);
			}
			velocity.scl(dt);
			position.add(velocity.x, velocity.y);
			velocity.scl(1 / dt);
			if (ds < 5) {
				player.addHP(-10);
				player.slow();
				player.addEffect(new DamageEffect((int) (player.getPosition().x + (player.getRenderWidth() / 2) - 6),
						(int) (player.getPosition().y + player.getRenderHeight() - 15), 10, 2, this));
				if (player.hp <= 0) {
					isPlayerDead = true;
				}
				isDestroyed = true;
			}
			if (Math.abs(dy) <= dx) {
				roll = 1;
			} else if (Math.abs(dx) <= dy) {
				roll = 2;
			} else if (-Math.abs(dy) >= dx) {
				roll = 3;
			} else if (-Math.abs(dx) >= dy) {
				roll = 0;
			}
		} else {
			roll = 4;
			if (!isSoundPlayed) {
				enemySound.play();
				isSoundPlayed = true;
			}
			if (getAnimation().isAnimationFinished(stateTime)) {
				isDestroyed = true;
			}
		}
	}
}
