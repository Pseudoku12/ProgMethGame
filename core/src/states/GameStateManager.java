package states;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStateManager {
	
	public static Stack<State> states = new Stack<State>();

	public GameStateManager() {	}

	public void push(State state) {
		states.push(state);
	}
	
	public void pop() {
		states.pop();
	}
	
	public void set(State state) {
		states.pop();
		states.push(state);
	}
	
	public void update(float dt) {
		states.peek().update(dt);
	}
	
	public void render(SpriteBatch sb) {
		states.peek().render(sb);
	}
	
}
