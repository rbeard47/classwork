package mapgame;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class MapLoader {

    private String version;
    private String tiledVersion;
    private String orientation;
    private String renderOrder;
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;
    private int infinite;
    private JsonArray tileset;
    private JsonArray layers;

    public MapLoader(File jsonFile) {

        JsonParser mapParser = new JsonParser();

        try {
            JsonObject map = (JsonObject) mapParser.parse(new FileReader(jsonFile));

            String version = map.get("version").getAsString();
            this.version = version;

            String tiledVersion = map.get("tiledversion").getAsString();
            this.tiledVersion = tiledVersion;

            String orientation = map.get("orientation").getAsString();
            this.orientation = orientation;

            String renderOrder = map.get("renderorder").getAsString();
            this.renderOrder = renderOrder;

            int width = map.get("width").getAsInt();
            this.width = width;

            int height = map.get("height").getAsInt();
            this.height = height;

            int tileWidth = map.get("tilewidth").getAsInt();
            this.tileWidth = tileWidth;

            int tileHeight = map.get("tileheight").getAsInt();
            this.tileHeight = tileHeight;

            int infinite = map.get("infinite").getAsInt();
            this.infinite = infinite;

            JsonArray tileset = map.getAsJsonArray("tileset");
            this.tileset = tileset;

            JsonArray layers = map.getAsJsonArray("layers");
            this.layers = layers;

        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException!");
        }
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTiledVersion() {
        return tiledVersion;
    }

    public void setTiledVersion(String tiledVersion) {
        this.tiledVersion = tiledVersion;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getRenderOrder() {
        return renderOrder;
    }

    public void setRenderOrder(String renderOrder) {
        this.renderOrder = renderOrder;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public int getInfinite() {
        return infinite;
    }

    public void setInfinite(int infinite) {
        this.infinite = infinite;
    }

    public JsonArray getTileset() {
        return tileset;
    }

    public void setTileset(JsonArray tileset) {
        this.tileset = tileset;
    }

    public JsonArray getLayers() {
        return layers;
    }

    public void setLayers(JsonArray layers) {
        this.layers = layers;
    }
    
    

}
