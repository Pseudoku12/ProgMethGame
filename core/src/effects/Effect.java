package effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import characters.Destroyable;

public class Effect implements Destroyable {
	protected boolean isDestroyed;

	public Effect() {
		isDestroyed = false;
	}
	public void render(SpriteBatch batch) {

	}

	public void update(float dt) {

	}

	public boolean isDestroyed() {
		return isDestroyed;
	}
}
