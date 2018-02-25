import java.util.*;

public class Bin {
  private int capacity;
  public List<Item> items;

  public Bin(int s) {
    capacity = s;
    items = new ArrayList<Item>();
  }

  public boolean addToBin(Item i) {
    if(i.getSize() <= capacity) {
      items.add(i);
      capacity -= i.getSize();
      return true;
    }

    return false;
  }

  public int getCapacity() {
    return capacity;
  }
}
