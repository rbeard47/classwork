package mapgame;

import java.io.File;

public class MapGame {

    public static void main(String[] args) {
        File file = new File("src/map.json");
        MapLoader mapLoader = new MapLoader(file);
        Map map = new Map(mapLoader);
        map.displayMap();
        
    }
    
}