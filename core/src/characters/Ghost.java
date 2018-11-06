package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gameprogmeth.game.GameProgMeth;

public class Ghost extends Character {

	private Character player;

	public Ghost(int x, int y, int speed, Character player) {
		animationSpeed = 0.5f;
		renderWidth = 160;
		renderHeight = 240;
		widthPixel = 16;
		heightPixel = 24;

		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		this.speed = speed;

		animation = new Animation[4];
		roll = 2;

		TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("Ghost.png"), widthPixel, heightPixel);

		animation[0] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[0]);
		animation[1] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[1]);
		animation[2] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[2]);
		animation[3] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[3]);

		this.player = player;
	}

	float dx;
	float dy;
	float ds;
	@Override
	public void update(float dt) {
		dx = player.getPosition().x - position.x;
		dy = player.getPosition().y - position.y;
		ds =(float) Math.pow(Math.pow(dx, 2) + Math.pow(dy, 2), 1/2);
		velocity.x = (dx/ds)*speed/1000;
		velocity.y = (dy/ds)*speed/1000;
		
		if(dx >= 0 && Math.abs(dy) < dx) {
			roll = 1;
		} else if(dy >= 0 && dy >= Math.abs(dx)) {
			roll = 2;
		} else if(dx < 0 && Math.abs(dy) < -dx) {
			roll = 3;
		}else {
			roll = 0;
		}
		velocity.scl(dt);
		position.add(velocity.x, velocity.y);
		velocity.scl(1/dt);
	}
}
