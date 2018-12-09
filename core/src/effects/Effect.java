package effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import characters.Destroyable;

public abstract class Effect implements Destroyable {
	protected boolean isDestroyed;
	protected Vector2 pos;

	public Effect() {
		isDestroyed = false;
	}

	public abstract void render(SpriteBatch batch);
	public abstract void update(float dt);

	public boolean isDestroyed() {
		return isDestroyed;
	}
}
