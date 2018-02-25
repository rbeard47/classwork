import java.util.*;

public class Program {

  public static void main(String[] args) {

    Packer packer = new Packer(41);

    List<Item> items = new ArrayList<Item>();

    Random rnd = new Random();

    for(int i = 0; i < 50000; i++) {
      items.add(new Item(rnd.nextInt(40)+1));
    }

    for(Item i : items) { 
      // System.out.format("Added item with size %d\n", i.getSize());
    }

    System.out.format("%d items\n", 50);

    packer.BestFit(items);

    System.out.format("BestFit - %d buckets\n", packer.getNumBins());

    // packer.DumpBins();

    Packer p2 = new Packer(41);

    p2.FirstFit(items);

    // p2.DumpBins();

    System.out.format("FirstFitDecreasing - %d buckets\n", p2.getNumBins());
  }
}
