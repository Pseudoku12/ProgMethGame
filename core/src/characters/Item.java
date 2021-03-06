package characters;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gameprogmeth.game.world.StoneAndGem;

import effects.CollectedEffect;
import effects.DamageEffect;
import effects.HealEffect;

public class Item extends Character implements Destroyable {

	private MainCharacter player;
	private TextureRegion[][] rollSpriteSheet;
	private int id;
	private boolean isDestroyed;
	private float elapsedTime;
	private double prw;
	private double prh;
	private OrthographicCamera cam;

	public Item(int x, int y, int speed, int id, MainCharacter player, OrthographicCamera cam) {
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
		this.id = id;
		this.cam = cam;
		this.isDestroyed = false;
		this.elapsedTime = 0;
	}

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
		return rollSpriteSheet[(int) Math.ceil((double) id / (double) 3) - 1][(int) (id + 2) % 3];
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	private void addScore() {
		int tempScore = 0;
		if (id == StoneAndGem.MINERAL_EARTHCRYSTAL.getId()) {
			tempScore = 50;
		} else if (id == StoneAndGem.MINERAL_CARROT.getId()) {
			player.addHP(5);
			player.addEffect(new DamageEffect((int) (player.getPosition().x + (player.getRenderWidth() / 2) - 6),
					(int) (player.getPosition().y + player.getRenderHeight() - 15), 5, 3, player));
		} else if (id == StoneAndGem.MINERAL_PAGE.getId()) {
			tempScore = 100;
		} else if (id == StoneAndGem.MINERAL_RING.getId()) {
			tempScore = 100;
		} else if (id == StoneAndGem.MINERAL_BOOK.getId()) {
			tempScore = 100;
		} else if (id == StoneAndGem.MINERAL_SPOON.getId()) {
			tempScore = 100;
		} else if (id == StoneAndGem.MINERAL_GEAR1.getId()) {
			tempScore = 100;
		} else if (id == StoneAndGem.MINERAL_JAR.getId()) {
			tempScore = 100;
		} else if (id == StoneAndGem.MINERAL_FROZENTEAR.getId()) {
			tempScore = 50;
		} else if (id == StoneAndGem.MINERAL_BLADE.getId()) {
			tempScore = 100;
		} else if (id == StoneAndGem.MINERAL_GEAR2.getId()) {
			tempScore = 100;
		} else if (id == StoneAndGem.MINERAL_MASK.getId()) {
			tempScore = 100;
		} else if (id == StoneAndGem.MINERAL_STONESLAB.getId()) {
			tempScore = 100;
		} else if (id == StoneAndGem.MINERAL_RAINBOW.getId()) {
			tempScore = 500;
		} else if (id == StoneAndGem.MINERAL_COPPER1.getId()) {
			tempScore = 10;
		} else if (id == StoneAndGem.MINERAL_COPPER2.getId()) {
			tempScore = 10;
		} else if (id == StoneAndGem.MINERAL_SILVER1.getId()) {
			tempScore = 15;
		} else if (id == StoneAndGem.MINERAL_SILVER2.getId()) {
			tempScore = 15;
		} else if (id == StoneAndGem.MINERAL_GOLD1.getId()) {
			tempScore = 20;
		} else if (id == StoneAndGem.MINERAL_GOLD2.getId()) {
			tempScore = 20;
		} else if (id == StoneAndGem.MINERAL_IRIDIUM1.getId()) {
			tempScore = 30;
		} else if (id == StoneAndGem.MINERAL_IRIDIUM2.getId()) {
			tempScore = 30;
		} else if (id == StoneAndGem.MINERAL_DIAMOND.getId()) {
			tempScore = 300;
		} else if (id == StoneAndGem.MINERAL_RUBY.getId()) {
			tempScore = 100;
		} else if (id == StoneAndGem.MINERAL_JADE.getId()) {
			tempScore = 70;
		} else if (id == StoneAndGem.MINERAL_TOPAZ.getId()) {
			tempScore = 70;
		} else if (id == StoneAndGem.MINERAL_EMERALD.getId()) {
			tempScore = 70;
		} else if (id == StoneAndGem.MINERAL_AMETHYST.getId()) {
			tempScore = 70;
		}
		player.addScore(tempScore);
		if (id != StoneAndGem.MINERAL_CARROT.getId()) {
			DamageEffect tempEffect = new DamageEffect((int) cam.position.x - 135, (int) cam.position.y - 55, tempScore,
					0, player);
			tempEffect.setAttachToCharacter(true);
			player.addEffect(tempEffect);
			player.addEffect(new CollectedEffect(player));
		} else {
			player.addEffect(new HealEffect(player));
		}
	}
}
