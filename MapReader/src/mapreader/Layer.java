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
public class Layer {

  public String Name;
  public int Width;
  public int Height;
  public int Locked;
  private ArrayList<Integer> Data;

  public Layer() {
    Data = new ArrayList<>();
  }
}
