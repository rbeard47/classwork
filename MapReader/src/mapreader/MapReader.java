/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapreader;

import java.io.IOException;

/**
 *
 * @author Rob
 */
public class MapReader {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // TODO code application logic here

    MapParser parser = new MapParser("C:\\Users\\Rob\\src\\classwork\\Assignments\\MapReader\\dungeon.json");

    Map map = parser.Parse();

    if (map != null) {
      System.out.format("Map version %s\n", map.version);

      if (map.orientation.equals("orthogonal")) {
        for (Layer l : map.layers) {

          System.out.format("Layer: %s\n", l.Name);
          
          if (map.renderOrder.equals("right-down")) {
            for (int y = 0; y < map.height; y++) {
              for (int x = 0; x < map.width; x++) {
                System.out.format("%03d ", (l.Data.get(y * map.width + x)));
              }
              System.out.println();
            }
          }
        }
      }
    }
  }
}
