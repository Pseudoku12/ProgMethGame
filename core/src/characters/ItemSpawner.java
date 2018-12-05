package characters;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gameprogmeth.game.world.StoneAndGem;

public class ItemSpawner {
	private ArrayList<Item> itemList;
	private ArrayList<Integer> markForRemoved;
	private MainCharacter player;

	public ItemSpawner(MainCharacter player) {
		itemList = new ArrayList<Item>();
		this.player = player;
	}

	public void update(float dt) {
		markForRemoved = new ArrayList<Integer>();
		for (int i = 0; i < itemList.size(); i++) {
			if (itemList.get(i) != null) {
				itemList.get(i).update(dt);
				if (itemList.get(i).isDestroyed()) {
					markForRemoved.add(i);
				}
			}
		}
		for (int i = markForRemoved.size() - 1; i >= 0; i--) {
			itemList.remove(itemList.get(markForRemoved.get(i)));
			System.out.println(player.getScore());
		}
	}

	public void render(SpriteBatch batch) {
		for (Item item : itemList) {
			if (item != null) {
				item.render(batch);
			}
		}
	}

	int typeDrop, rowDrop, colDrop;

	public void dropValueable(StoneAndGem stone, int col, int rol) {
		typeDrop = 0;
		int id = stone.getId();
		Random random = new Random();
		if (id < StoneAndGem.COPPER_ROCK.getId()) {
			int canDrop = random.nextInt(10000);
			typeDrop = 45;
		} else {
			typeDrop = id + 2;
		}
		rowDrop = rol;
		colDrop = col;
		itemList.add(new Item(colDrop * 16, rowDrop * 16, 100, typeDrop, player));
	}
}
