package halloffame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class HallOfFameLoader {
	private static Json json = new Json();

	public static HallOfFameData loadHallOfFame() {
		Gdx.files.local("hof/").file().mkdirs();
		FileHandle file = Gdx.files.local("hof/HallOfFame.map");
		if (file.exists()) {
			HallOfFameData data = json.fromJson(HallOfFameData.class, file.readString());
			return data;
		} else {
			HallOfFameData data = generateHallOfFame();
			saveHallOfFame(data.names, data.scores);
			return data;
		}
	}

	public static void saveHallOfFame(String[] names, int[] scores) {
		HallOfFameData data = new HallOfFameData();
		data.names = names;
		data.scores = scores;
		Gdx.files.local("hof/").file().mkdirs();
		FileHandle file = Gdx.files.local("hof/HallOfFame.map");
		file.writeString(json.prettyPrint(data), false);
	}

	public static HallOfFameData generateHallOfFame() {
		HallOfFameData data = new HallOfFameData();
		data.names = new String[5];
		data.scores = new int[5];
		data.names[0] = "A";
		data.names[1] = "B";
		data.names[2] = "C";
		data.names[3] = "D";
		data.names[4] = "E";
		data.scores[0] = 500;
		data.scores[1] = 300;
		data.scores[2] = 100;
		data.scores[3] = 70;
		data.scores[4] = 50;
		return data;
	}

}
