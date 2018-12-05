package characters;

public class Enemy extends Character implements Destroyable {

	protected boolean isDestroyed;

	public Enemy() {
		isDestroyed = false;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public void push(double tempAngle) {
	}
}
