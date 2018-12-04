package characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Ghost extends Character implements Destroyable{
	
	private MainCharacter player;
	private Sprite sprite;
	private TextureRegion[][] rollSpriteSheet;
	private boolean isPlayerDead;
	private double prw;
	private double prh;
	
	public boolean isDestroyed;
	
	public Ghost(int x, int y, int speed, MainCharacter player) {
		animationSpeed = 0.5f;
		renderWidth = 16;
		renderHeight = 24;
		widthPixel = 16;
		heightPixel = 24;

		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		this.speed = speed;

		animation = new Animation[4];
		roll = 2;

		TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("Ghost.png"), widthPixel,
				heightPixel);

		animation[0] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[0]);
		animation[1] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[1]);
		animation[2] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[2]);
		animation[3] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[3]);
		
		this.player = player;
		prw = player.getRenderWidth();
		prh = player.getRenderHeight();
		
		isPlayerDead = false;
		isDestroyed = false;
		
		hp = maxHp = 2;
	}

	double dx;
	double dy;
	double ds;
	@Override
	public void update(float dt) {
		dx = player.getPosition().x - position.x - (renderWidth/2) + (prw/2);
		dy = player.getPosition().y - position.y - (renderHeight/2) + (prh/2);
		ds = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		velocity.x = (float)(dx/ds)*speed;
		velocity.y = (float)(dy/ds)*speed;
		velocity.scl(dt);
		position.add(velocity.x, velocity.y);
		velocity.scl(1/dt);
		if(ds < 10) {
			player.addHP(-10);
			if(player.hp <= 0) {
				isPlayerDead = true;
			}
		}
		if(Math.abs(dy) <= dx) {
			roll = 1;
		}
		else if(Math.abs(dx) <= dy) {
			roll = 2;
		}
		else if(-Math.abs(dy) >= dx) {
			roll = 3;
		}
		else if(-Math.abs(dx) >= dy) {
			roll = 0;
		}
	}
	
	public boolean isPlayerDead() {
		return isPlayerDead;
	}

	@Override
	public boolean isDestroyed() {
		return isDestroyed;
	}
}
