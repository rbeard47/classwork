import java.util.*;

public class Packer { 

  private List<Bin> bins = null;
  private int binSize = 0;

  public Packer(int size) {
    bins = new ArrayList<Bin>();
    binSize = size;
  }

  Comparator<Item> comparator = new Comparator<Item>() {
    @Override
    public int compare(Item left, Item right) {
      return right.getSize() - left.getSize(); // use your logic
    }
  };

  public int getNumBins() {
    return bins.size();
  }

  public void FirstFit(List<Item> items) {

    boolean found = false;

    for(int i = 0; i < items.size(); i++) {

      // Get the item at index i
      Item item = items.get(i);

      for(int j = 0; j < bins.size(); j++) {

        // Get the bin at index j 
        Bin b = bins.get(j);

        found = b.addToBin(item);

        if(found) {
          // System.out.format("Added item with size %d to bin #%d\n", item.getSize(), j);
          break;
        }
      }

      if(!found){  
        // System.out.format("Adding new bin for item with size %d\n", item.getSize());
        Bin newBin = new Bin(binSize);
        bins.add(newBin);
        newBin.addToBin(item);
      }
    }
  }

  public void FirstFitDecreasing(List<Item> items) {
    Collections.sort(items, comparator);
    FirstFit(items);
  }

  public void BestFit(List<Item> items) {

    //Collections.sort(items, comparator);

    boolean found = false;

    for(int i = 0; i < items.size(); i++) {

      // Get the item at index i
      Item item = items.get(i);

      int bestFitIndex = -1;
      int leastLeftover = binSize;
      for(int j = 0; j < bins.size(); j++) {

        // Get the bin at index j 
        Bin b = bins.get(j);

        if(b.getCapacity() < item.getSize()) {
          continue;
        }
        
        if(b.getCapacity() == item.getSize()) {         
          bestFitIndex = j;
          break;
        } 

        if((b.getCapacity() - item.getSize()) < leastLeftover) {
          leastLeftover = b.getCapacity() - item.getSize();
          // System.out.format("lastLeftover = %d - Bin #%d\n", leastLeftover, j);
          bestFitIndex = j;
        } 
      }
      
      if(bestFitIndex == -1){  
        // System.out.format("Adding new bin for item with size %d\n", item.getSize());
        Bin newBin = new Bin(binSize);
        bins.add(newBin);
        newBin.addToBin(item);
      } else {
        // System.out.format("Adding to bin #%d\n", bestFitIndex);
        Bin b = bins.get(bestFitIndex);
        b.addToBin(item);
      }
    }
  }

  public void DumpBins() {
    for(int i = 0; i < bins.size(); i++) {
      Bin b = bins.get(i);

      for(int j = 0; j < b.items.size(); j++) {
        System.out.format("Bin #%d - Item %d (%d)\n", i, j, b.items.get(j).getSize());
      }
    }
  }
}
