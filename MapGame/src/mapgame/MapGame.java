package mapgame;

import java.io.File;

public class MapGame {

    public static void main(String[] args) {
        File file = new File("src/map.json");
        MapLoader map = new MapLoader(file);
        
        System.out.println(map.getVersion());
        System.out.println(map.getTiledVersion());
        
    }
    
}
