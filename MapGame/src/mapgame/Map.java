package mapgame;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Map {

    private final MapLoader mapLoader;
    private String[][] map;
    private ArrayList<String> data = new ArrayList<>();
    private final int height;
    private final int width;

    public Map(MapLoader mapLdr) {
        mapLoader = mapLdr;
        height = mapLoader.getHeight();
        width = mapLoader.getWidth();
        mapBuilder();

    }

    public void mapBuilder() {

        mapDimension();
        mapLoader();

    }

    public void mapDimension() {
        map = new String[width][height];
    }

    public void mapLoader() {

        tileGetter();

        for (int y = 0, index = 0; y < height; y++) {
            for (int x = 0; x < width; x++, index++) {
                map[x][y] = data.get(index);
            }
        }
    }

    public void tileGetter() {

        JsonArray layers = mapLoader.getLayers();
        JsonObject data = layers.get(4).getAsJsonObject();
        String dataString = data.get("data").getAsString();

        StringTokenizer st = new StringTokenizer(dataString, ",");
        int index = 0;
        String plop = " ";
        while (st.hasMoreTokens()) {

            plop = (st.nextToken() + " ");
            
            if (plop.length() == 1) {
                plop = (st.nextToken() + "  ");
            }
            this.data.add(plop);
            index++;

        }
    }

    public void displayMap() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(map[x][y]);
            }
            System.out.println();
        }

    }

}
