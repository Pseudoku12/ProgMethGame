package halloffame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class HallOfFameLoader {
	private static Json json = new Json();
	
	public static HallOfFameData loadHallOfFame() {
		Gdx.files.local("hof/").file().mkdirs();
		FileHandle file = Gdx.files.local("hof/HallOfFame.txt");
		if(file.exists()) {
			HallOfFameData data = json.fromJson(HallOfFameData.class, file.readString());
			return data;
		} else {
			HallOfFameData data = generateHallOfFame();
			saveHallOfFame(data.names, data.scores);
			return data;
		}
	}

	public static void saveHallOfFame(String[] names, String[] scores) {
		HallOfFameData data = new HallOfFameData();
		data.names = names;
		data.scores = scores;
		
		for(int i = 0; i < 5; i++) {
			System.out.println(data.names[i] + " " + data.scores[i]);
		}
		
		Gdx.files.local("hof/").file().mkdirs();
		FileHandle file = Gdx.files.local("hof/HallOfFame.txt");
		file.writeString(json.prettyPrint(data), false);
		
	}
	

	public static HallOfFameData generateHallOfFame() {
		HallOfFameData data = new HallOfFameData();
		data.names = new String[5];
		data.scores = new String[5];
		
		data.names[0] = "Peter";
		data.names[1] = "James";
		data.names[2] = "Tom";
		data.names[3] = "Jerry";
		data.names[4] = "Smith";
		
		data.scores[0] = "500";
		data.scores[1] = "300";
		data.scores[2] = "100";
		data.scores[3] = "70";
		data.scores[4] = "50";
		
		return data;
	}

}
