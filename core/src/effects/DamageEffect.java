package effects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import characters.Destroyable;

public class DamageEffect implements Destroyable {
	private String damage;
	private BitmapFont font;
	private Vector2 pos;
	private Vector2 velocity;
	private float fadingSpeed;
	private float transparency;
	private boolean isDestroyed;
	private int color;

	public DamageEffect(int x, int y, int damage, int color) {
		this.damage = Integer.toString(damage);
		font = new BitmapFont();
		font.getData().setScale(0.7f);
		pos = new Vector2(x, y);
		velocity = new Vector2(0, 10);
		transparency = 1.0f;
		fadingSpeed = 0.03f;
		isDestroyed = false;
		this.color = color;
	}

	public void render(SpriteBatch batch) {
		if (color == 0) {
			font.setColor(1.0f, 1.0f, 0.0f, transparency);
		} else if (color == 1) {
			font.setColor(1.0f, 0.0f, 0.0f, transparency);
		}
		font.draw(batch, damage, pos.x, pos.y);
	}

	public void update(float dt) {
		velocity.scl(dt);
		pos.add(velocity);
		velocity.scl(1 / dt);
		transparency -= fadingSpeed;
		if (transparency <= 0) {
			transparency = 0;
			isDestroyed = true;
		}
	}

	@Override
	public boolean isDestroyed() {
		return isDestroyed;
	}
}
