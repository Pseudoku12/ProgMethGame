package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gameprogmeth.game.GameProgMeth;
import com.gameprogmeth.game.world.StoneAndGem;

public class Item extends Character implements Destroyable {

	private MainCharacter player;
	private Sprite sprite;
	private TextureRegion[][] rollSpriteSheet;
	private int item, rollRow, rollCol;
	private boolean isDestroyed;
	private float elapsedTime;
	private double prw;
	private double prh;

	public Item(int x, int y, int speed, int item, MainCharacter player) {
		animationSpeed = 0.5f;
		renderWidth = 16;
		renderHeight = 16;
		widthPixel = 16;
		heightPixel = 16;

		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		this.speed = speed;

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
			dx = player.getPosition().x - position.x - (renderWidth / 2) + (prw / 2);
			dy = player.getPosition().y - position.y - (renderHeight / 2) + (prh / 2);
			ds = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
			velocity.x = (float) (dx / ds) * speed;
			velocity.y = (float) (dy / ds) * speed;
			velocity.scl(dt);
			position.add(velocity.x, velocity.y);
			velocity.scl(1 / dt);
			if (ds < 5) {
				addScore();
				isDestroyed = true;
			}
		} else {
			elapsedTime += dt;
		}

	}

	public void render(SpriteBatch batch) {
		batch.draw(getTexture(), getPosition().x, getPosition().y, getRenderWidth(), getRenderHeight());
	}

	public TextureRegion getTexture() {
		return rollSpriteSheet[(int) Math.ceil((double) item / (double) 3) - 1][(int) (item + 2) % 3];
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	private void addScore() {
		if (item == StoneAndGem.MINERAL_EARTHCRYSTAL.getId()) {
			player.addScore(50);
		} else if (item == StoneAndGem.MINERAL_CARROT.getId()) {
			player.addHP(5);
		} else if (item == StoneAndGem.MINERAL_PAGE.getId()) {
			player.addScore(100);
		} else if (item == StoneAndGem.MINERAL_RING.getId()) {
			player.addScore(100);
		} else if (item == StoneAndGem.MINERAL_BOOK.getId()) {
			player.addScore(100);
		} else if (item == StoneAndGem.MINERAL_SPOON.getId()) {
			player.addScore(100);
		} else if (item == StoneAndGem.MINERAL_GEAR1.getId()) {
			player.addScore(100);
		} else if (item == StoneAndGem.MINERAL_JAR.getId()) {
			player.addScore(100);
		} else if (item == StoneAndGem.MINERAL_FROZENTEAR.getId()) {
			player.addScore(50);
		} else if (item == StoneAndGem.MINERAL_BLADE.getId()) {
			player.addScore(100);
		} else if (item == StoneAndGem.MINERAL_GEAR2.getId()) {
			player.addScore(100);
		} else if (item == StoneAndGem.MINERAL_MASK.getId()) {
			player.addScore(100);
		} else if (item == StoneAndGem.MINERAL_STONESLAB.getId()) {
			player.addScore(100);
		} else if (item == StoneAndGem.MINERAL_RAINBOW.getId()) {
			player.addScore(500);
		} else if (item == StoneAndGem.MINERAL_COPPER1.getId()) {
			player.addScore(10);
		} else if (item == StoneAndGem.MINERAL_COPPER2.getId()) {
			player.addScore(10);
		} else if (item == StoneAndGem.MINERAL_SILVER1.getId()) {
			player.addScore(15);
		} else if (item == StoneAndGem.MINERAL_SILVER2.getId()) {
			player.addScore(15);
		} else if (item == StoneAndGem.MINERAL_GOLD1.getId()) {
			player.addScore(20);
		} else if (item == StoneAndGem.MINERAL_GOLD2.getId()) {
			player.addScore(20);
		} else if (item == StoneAndGem.MINERAL_IRIDIUM1.getId()) {
			player.addScore(30);
		} else if (item == StoneAndGem.MINERAL_IRIDIUM2.getId()) {
			player.addScore(30);
		} else if (item == StoneAndGem.MINERAL_DIAMOND.getId()) {
			player.addScore(300);
		} else if (item == StoneAndGem.MINERAL_RUBY.getId()) {
			player.addScore(100);
		} else if (item == StoneAndGem.MINERAL_JADE.getId()) {
			player.addScore(70);
		} else if (item == StoneAndGem.MINERAL_TOPAZ.getId()) {
			player.addScore(70);
		} else if (item == StoneAndGem.MINERAL_EMERALD.getId()) {
			player.addScore(70);
		} else if (item == StoneAndGem.MINERAL_AMETHYST.getId()) {
			player.addScore(70);
		}
	}
}
