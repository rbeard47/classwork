/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapreader;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Rob
 */
public class MapParser {

    private String mapfile;
    private Map map;

    public MapParser(String mapfile) {
        map = new Map();
        this.mapfile = mapfile;
    }

    public Map Parse() {

        try {
            if (ReadFile()) {
                return map;
            }
        } catch (IOException ex) {
            System.out.format("Exception %d\n", ex.getMessage());
        }

        // Failed - return null
        return null;
    }

    private boolean ReadFile() throws IOException {

        File f = new File(mapfile);

        if (!f.exists() || f.isDirectory()) {
            return false;
        }

        JsonReader reader = new JsonReader(new FileReader(
                mapfile));

        reader.beginObject();

        while (reader.hasNext()) {

            String name = reader.nextName();
            if (name.equals("map")) {
                System.out.println("Found map...");
                ReadMap(reader);
            }
        }

        reader.endObject();
        reader.close();

        return true;
    }

    private void ReadMap(JsonReader reader) throws IOException {

        map = new Map();

        reader.beginObject();

        while (reader.hasNext()) {

            String name = reader.nextName();

            if (name.equals("version")) {
                map.version = reader.nextString();
            } else if (name.equals("tiledversion")) {
                map.tiledVersion = reader.nextString();
            } else if (name.equals("orientation")) {
                map.orientation = reader.nextString();
            } else if (name.equals("renderorder")) {
                map.renderOrder = reader.nextString();
            } else if (name.equals("width")) {
                map.width = reader.nextInt();
            } else if (name.equals("height")) {
                map.height = reader.nextInt();
            } else if (name.equals("tilewidth")) {
                map.tileWidth = reader.nextInt();
            } else if (name.equals("tileheight")) {
                map.tileHeight = reader.nextInt();
            } else if (name.equals("infinite")) {
                map.infinite = reader.nextInt();
            } else if (name.equals("tileset")) {

                reader.beginArray();

                while (reader.hasNext()) {

                    reader.beginObject();
                    Tileset t = new Tileset();

                    while (reader.hasNext()) {

                        name = reader.nextName();

                        if (name.equals("firstgrid")) {
                            t.firstGrid = reader.nextInt();
                        } else if (name.equals("source")) {
                            t.Source = reader.nextString();
                        }
                    }

                    map.tilesets.add(t);
                    reader.endObject();
                }

                reader.endArray();
            } else if (name.equals("layers")) {

                reader.beginArray();

                while (reader.hasNext()) {

                    reader.beginObject();

                    Layer layer = new Layer();

                    while (reader.hasNext()) {

                        name = reader.nextName();

                        if (name.equals("name")) {
                            layer.Name = reader.nextString();
                        } else if (name.equals("width")) {
                            layer.Width = reader.nextInt();
                        } else if (name.equals("height")) {
                            layer.Height = reader.nextInt();
                        } else if (name.equals("locked")) {
                            layer.Locked = reader.nextInt();
                        } else if (name.equals("data")) {

                            // Parse the string into an array of substrings, each containing a
                            // string version of an integer
                            name = reader.nextString();
                            String[] dataString = name.split(",");
                            for (String s : dataString) {
                                // Convert the string to an integer and store it in the data array
                                layer.Data.add(Integer.parseInt(s));
                            }
                        }
                    }

                    map.layers.add(layer);

                    reader.endObject();
                }

                reader.endArray();
            }
        }
    }
}
