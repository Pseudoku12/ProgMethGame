package effects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import characters.Destroyable;
import characters.MainCharacter;

public class DamageEffect implements Destroyable {
	private String damage;
	private BitmapFont font;
	private Vector2 pos;
	private Vector2 staticPos;
	private Vector2 velocity;
	private float fadingSpeed;
	private float transparency;
	private boolean isDestroyed;
	private boolean isAttach;
	private int color;
	private characters.Character character;

	public DamageEffect(int x, int y, int damage, int color, characters.Character character) {
		this.damage = Integer.toString(damage);
		font = new BitmapFont();
		font.getData().setScale(0.7f);
		pos = new Vector2(x, y);
		staticPos = new Vector2(0, 0);
		velocity = new Vector2(0, 10);
		transparency = 1.0f;
		fadingSpeed = 0.03f;
		isDestroyed = false;
		this.color = color;
		this.character = character;
		this.isAttach = false;
		if (color == 0) {
			this.damage = "+" + this.damage;
		}
	}

	public void render(SpriteBatch batch) {
		if (color == 0) {
			font.setColor(1.0f, 1.0f, 1.0f, transparency);
		} else if (color == 1) {
			font.setColor(1.0f, 1.0f, 0.0f, transparency);
		} else if (color == 2) {
			font.setColor(1.0f, 0.0f, 0.0f, transparency);
		} else if (color == 3) {
			font.setColor(0.0f, 1.0f, 0.0f, transparency);
		}
		font.draw(batch, damage, pos.x, pos.y);
	}

	public void update(float dt) {
		if (isAttach) {
			this.pos.set(character.getPosition().x - 103.5f, character.getPosition().y - 23.5f);
			velocity.scl(dt);
			staticPos.add(velocity);
			velocity.scl(1 / dt);
			pos.add(staticPos);
		} else {
			velocity.scl(dt);
			pos.add(velocity);
			velocity.scl(1 / dt);
		}
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

	public void setAttachToCharacter(boolean isAttach) {
		this.isAttach = isAttach;
	}
}
