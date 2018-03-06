/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapreader;

import java.util.ArrayList;

/**
 *
 * @author Rob
 */
public class Map {
  
  public String version;
  public String tiledVersion;
  public String orientation;
  public String renderOrder;
  public int width;
  public int height;
  public int tileWidth;
  public int tileHeight;
  public int infinite;
  private ArrayList<Tileset> tilesets;
  private ArrayList<Layer> layers;
  
  public Map() {
    tilesets = new ArrayList<>();
    layers = new ArrayList<>();
  }
}
