/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeardWill_Assignment1_ITIS1213;

import BookClasses.Sound;
import java.io.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 * This is the test harness for Assignment 1, and it is used to call and test the audio poetry
 * methods.
 *
 * @author clatulip, (add your name here)
 */
public class Assignment1_ITIS1213 {

  /**
   * @param args the command line arguments
   * @throws java.lang.InterruptedException
   */
  public static void main(String[] args) throws InterruptedException {

    //TODO: change the path below to reflect the path to your mediasources file
    String path = "C:\\NetBeansProjects\\mediasources";

    //*********************************************************************
    // DO NOT CHANGE THE CODE BELOW
    File soundFilename = null;
    // next two lines create a custom file chooser, with a specific frame heading
    final JFileChooser myChooser = new JFileChooser(path);
    myChooser.setDialogTitle("Please select a sound file...");
    int retVal = myChooser.showOpenDialog(null);

    if (retVal == JFileChooser.APPROVE_OPTION) {
      soundFilename = myChooser.getSelectedFile();
    } else {
      System.exit(retVal);
    }

    File spliceFilename = null;
    // Reset the open dialog title
    myChooser.setDialogTitle("Now select the file that contains the splice points...");

    // open the dialog again to pick the spliceFilename
    retVal = myChooser.showOpenDialog(null);

    // If the user selected a file, then set spliceFilename
    if (retVal == JFileChooser.APPROVE_OPTION) {
      spliceFilename = myChooser.getSelectedFile();
    } else { // CANCEL_OPTION or ERROR_OPTION
      System.exit(retVal); // Exit the application with a return code of retVal
    }

    // I moved these variables closer to where they'll be used.
    int spliceIndex[] = new int[200];
    int numSplicePoints = 0;

    // This code should check whether or not soundFilename is null before calling getPath()
    // create a sound object from this filename
    Sound mySound = new Sound(soundFilename.getPath());
    // open the file
    // some of the code below could generate exceptions, so enclose in try-catch
    try ( // create a scanner object variable so we can read in the file, token by token
            Scanner s = new Scanner(new BufferedReader(new FileReader(spliceFilename))) // this gets called no matter what, to close the scanner
            ) {
      // check if there is another token in the file
      while (s.hasNext()) {
        // check if it's a number
        if (s.hasNextInt()) {
          // add it to the array, increment number of items in array
          spliceIndex[numSplicePoints] = s.nextInt();
          numSplicePoints++;
        } else {
          // if it's not a number, skip it
          s.next();
        }
      }
    } catch (FileNotFoundException fnfe) {
      System.out.println("main method: splicefile not found");
    } catch (InputMismatchException ime) {
      System.out.println("main method: splice input not integer");
    } catch (NoSuchElementException nsee) {
      System.out.println("main method: no such element after " + numSplicePoints);
    }

    // create an audiopoem object out of the sound and the splicearary
    AudioPoem myPoem = new AudioPoem(mySound, spliceIndex, numSplicePoints);

    // DO NOT CHANGE THE CODE ABOVE
    //**********************************************************
    //**********************************************************
    // TODO: Put your Assignment 1 code to play the different AudioPoem methods here
//    myPoem.play();
//    myPoem.playRandomOrder(10, 100);
//    myPoem.playRandomUnique(100);
//    myPoem.playReverseOrder(100);
//    myPoem.playDoublets(5);
//    myPoem.playTriplets(6);
//    myPoem.play(200, "exportSound.wav", "C:\\netBeansProjects");
//    myPoem.playRandomOrder(10, 200, "exportSoundRandomOrder.wav", "C:\\netBeansProjects");
    myPoem.playTriplets(2, "exportSoundTriples.wav", "c:\\netbeansprojects");
    myPoem.playDoublets(6, "exportSoundDoubles.wav", "c:\\netbeansprojects");
    // Part 4: When you get to part 4, comment everything from line 28 through line 93, then uncomment the four lines of 
    // code below and run the project to see what happens.
    // Then look at the CupSong class as an example for how to build your own song-playing class.
//    CupSong myCupSong = new CupSong();
//    myCupSong.play();
//    myCupSong.changeInstrument(24); // change to guitar
//    myCupSong.play();
  }
}
