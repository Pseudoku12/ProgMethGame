package spawners;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gameprogmeth.game.world.StoneAndGem;

import characters.Item;
import characters.MainCharacter;

public class ItemSpawner {
	private ArrayList<Item> itemList;
	private ArrayList<Integer> markForRemoved;
	private MainCharacter player;
	private int level;
	private int typeDrop;
	private OrthographicCamera cam;

	public ItemSpawner(MainCharacter player, int level, OrthographicCamera cam) {
		itemList = new ArrayList<Item>();
		this.player = player;
		this.level = level;
		this.cam = cam;
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

	public void dropValueable(int x, int y) {
		typeDrop = -1;
		Random random = new Random();
		int canDrop = -1;
		if (level < 10) {
			canDrop = random.nextInt(7250) + 1;
		} else if (level < 20) {
			canDrop = random.nextInt(8583) + 1;
		} else {
			canDrop = random.nextInt(8874) + 1;
		}
		if (canDrop <= 2000) {
			typeDrop = StoneAndGem.MINERAL_COPPER1.getId();
		} else if (canDrop <= 4000) {
			typeDrop = StoneAndGem.MINERAL_COPPER2.getId();
		} else if (canDrop <= 4100) {
			typeDrop = StoneAndGem.MINERAL_EARTHCRYSTAL.getId();
		} else if (canDrop <= 5100) {
			typeDrop = StoneAndGem.MINERAL_CARROT.getId();
		} else if (canDrop <= 5110) {
			typeDrop = StoneAndGem.MINERAL_PAGE.getId();
		} else if (canDrop <= 5120) {
			typeDrop = StoneAndGem.MINERAL_SPOON.getId();
		} else if (canDrop <= 5130) {
			typeDrop = StoneAndGem.MINERAL_JAR.getId();
		} else if (canDrop <= 6130) {
			typeDrop = StoneAndGem.MINERAL_SILVER1.getId();
		} else if (canDrop <= 7130) {
			typeDrop = StoneAndGem.MINERAL_SILVER2.getId();
		} else if (canDrop <= 7180) {
			typeDrop = StoneAndGem.MINERAL_RUBY.getId();
		} else if (canDrop <= 7230) {
			typeDrop = StoneAndGem.MINERAL_JADE.getId();
		} else if (canDrop <= 7240) {
			typeDrop = StoneAndGem.MINERAL_RING.getId();
		} else if (canDrop <= 7250) {
			typeDrop = StoneAndGem.MINERAL_GEAR1.getId();
		} else if (canDrop <= 7750) {
			typeDrop = StoneAndGem.MINERAL_GOLD1.getId();
		} else if (canDrop <= 8250) {
			typeDrop = StoneAndGem.MINERAL_GOLD2.getId();
		} else if (canDrop <= 8300) {
			typeDrop = StoneAndGem.MINERAL_TOPAZ.getId();
		} else if (canDrop <= 8350) {
			typeDrop = StoneAndGem.MINERAL_EMERALD.getId();
		} else if (canDrop <= 8353) {
			typeDrop = StoneAndGem.MINERAL_DIAMOND.getId();
		} else if (canDrop <= 8553) {
			typeDrop = StoneAndGem.MINERAL_FROZENTEAR.getId();
		} else if (canDrop <= 8563) {
			typeDrop = StoneAndGem.MINERAL_BLADE.getId();
		} else if (canDrop <= 8573) {
			typeDrop = StoneAndGem.MINERAL_GEAR2.getId();
		} else if (canDrop <= 8583) {
			typeDrop = StoneAndGem.MINERAL_BOOK.getId();
		} else if (canDrop <= 8683) {
			typeDrop = StoneAndGem.MINERAL_IRIDIUM1.getId();
		} else if (canDrop <= 8783) {
			typeDrop = StoneAndGem.MINERAL_IRIDIUM2.getId();
		} else if (canDrop <= 8833) {
			typeDrop = StoneAndGem.MINERAL_AMETHYST.getId();
		} else if (canDrop <= 8853) {
			typeDrop = StoneAndGem.MINERAL_MASK.getId();
		} else if (canDrop <= 8873) {
			typeDrop = StoneAndGem.MINERAL_STONESLAB.getId();
		} else if (canDrop <= 8874) {
			typeDrop = StoneAndGem.MINERAL_RAINBOW.getId();
		}
		if (typeDrop != -1) {
			itemList.add(new Item(x, y, 100, typeDrop, player, cam));
		}
	}
}
