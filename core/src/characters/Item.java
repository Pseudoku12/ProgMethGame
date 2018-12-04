package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gameprogmeth.game.GameProgMeth;

public class Item extends Character {

	private MainCharacter player;
	private Sprite sprite;
	private TextureRegion[][] rollSpriteSheet;
	private int item, rollRow, rollCol;
	private boolean isDestroyed;
	private float elapsedTime;
	private double prw;
	private double prh;

	public Item(int x, int y, int speed, int item, int rollRow, int rollCol, MainCharacter player) {
		animationSpeed = 0.5f;
		renderWidth = 16;
		renderHeight = 16;
		widthPixel = 16;
		heightPixel = 16;

		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		this.speed = speed;

		this.rollRow = rollRow;
		this.rollCol = rollCol;

		rollSpriteSheet = TextureRegion.split(new Texture("resource/Stone_Gem_Ladder.png"), widthPixel, heightPixel);

		this.player = player;
		prw = player.getRenderWidth();
		prh = player.getRenderHeight();
		this.item = item;
		this.isDestroyed = false;
		this.elapsedTime = 0;
	}

	double dx;
	double dy;
	double ds;

	@Override
	public void update(float dt) {
		if (elapsedTime >= 1) {
			dx = player.getPosition().x - position.x - (renderWidth/2) + (prw/2);
			dy = player.getPosition().y - position.y - (renderHeight/2) + (prh/2);
			ds = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
			velocity.x = (float) (dx / ds) * speed;
			velocity.y = (float) (dy / ds) * speed;
			velocity.scl(dt);
			position.add(velocity.x, velocity.y);
			velocity.scl(1 / dt);
			if (ds < 5) {
				if(item == 1) {
					player.setStamina(player.getStamina() + 5);
				}
				player.addScore(10);
				isDestroyed = true;
			}
		} else {
			elapsedTime += dt;
		}

	}

	public TextureRegion getTexture() {
		return rollSpriteSheet[item][2];
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}
}
