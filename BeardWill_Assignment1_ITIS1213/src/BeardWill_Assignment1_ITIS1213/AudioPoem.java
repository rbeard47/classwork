/*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
 */
package BeardWill_Assignment1_ITIS1213;

import BookClasses.Sound;
import java.io.File;

/**
 * This class contains methods for mixing up the words in an audio file and creating sound poetry
 * out of them. It contains many stub methods which need to be completed as part of Assignment 1.
 *
 * @author clatulip, (will)
 */
public class AudioPoem {

  private static final int MAX_NUM_WORDS = 100;
  private static final Sound[] myWordArray = new Sound[MAX_NUM_WORDS];
  private static int numWords = 0;

  public AudioPoem(Sound originalSource, int[] spliceArray, int numSplicePoints) {

    // break the sound into separate words, copying each into the word array
    for (int i = 0, j = 0; i < numSplicePoints; i = i + 2, j++) {
      myWordArray[j] = new Sound(spliceArray[i + 1] - spliceArray[i]);
      for (int x = spliceArray[i], y = 0; x < spliceArray[i + 1]; x++, y++) {
        myWordArray[j].setSampleValueAt(y, originalSource.getSampleValueAt(x));
      }
      numWords++;
    }
  }

  /**
   * Plays the words, in order with a 200 millisecond pause between each
   *
   * @throws InterruptedException
   */
  public void play() throws InterruptedException {
    // play the words in order
    for (int i = 0; i < numWords; i++) {
      myWordArray[i].blockingPlay();
      Thread.sleep(200);
    }
  }

  /**
   * Plays the words, in order with a parameter-specified pause between each
   *
   * @param pause the number of milliseconds to pause between words
   * @throws InterruptedException
   */
  public void play(int pause) throws InterruptedException {
    //plays the words in order with a specified pause in between each
    for (int i = 0; i < numWords; i++) {
      myWordArray[i].blockingPlay();
      Thread.sleep(pause);
    }
  }

  /**
   * Saves a sound array to a single output file
   *
   * @param sounds
   * @param filename
   * @param path
   * @throws InterruptedException
   */
  public void saveSoundToFile(Sound[] sounds, String filename, String path) throws InterruptedException {

  }

  /**
   * Plays the words, in order with a parameter-specified pause between each and writes the
   * resulting sound out to a file
   *
   * @param pause the number of milliseconds to pause between words
   * @param filename the name of the file to write
   * @param path the path where the file should be written
   * @throws InterruptedException
   */
  public void play(int pause, String filename, String path) throws InterruptedException {
    // plays the words in order with a specified pause in between each and writes this new sound to
    // a file with a specified location 

    double samplingRate = myWordArray[0].getSamplingRate();
    int pauseFrames = (int) (samplingRate * (pause / 1000));
    int totalSamples = 0;

    for (int soundArray = 0; soundArray < numWords; soundArray++) {
      totalSamples += myWordArray[soundArray].getLength();
    }

    totalSamples += pauseFrames * (numWords - 1);
    Sound exportSound = new Sound(totalSamples);

    for (int i = 0, exportSoundSample = 0; i < numWords; i++) {
      //write word samples from soundArray myWordArray to exportSound's sampleArray
      for (int currentSample = 0;
              currentSample < myWordArray[i].getLength(); currentSample++, exportSoundSample++) {
        exportSound.setSampleValueAt(exportSoundSample, myWordArray[i].getSampleValueAt(currentSample));
      }

      //write number of samples in specified pause time to exportSound's sampleArray to a value of 0
      for (int pauseSample = 0; pauseSample < pauseFrames; pauseSample++, exportSoundSample++) {
        exportSound.setSampleValueAt(exportSoundSample, 0);
      }
    }

    File exportFile = new File(path, filename);
    exportSound.write(exportFile.getPath());
  }

  /**
   * Plays the words in random order, each word can be played multiple times
   *
   * @param totalWords the total number of words that will be played
   * @param pause the number of milliseconds to pause between words
   * @throws InterruptedException
   */
  public void playRandomOrder(int totalWords, int pause) throws InterruptedException {
    for (int currentWord = 0; currentWord < totalWords; currentWord++) {
      int randomIndex = (int) (Math.random() * numWords);
      myWordArray[randomIndex].blockingPlay();
      Thread.sleep(pause);
    }
  }

  /**
   *
   * @param totalWords
   * @param pause
   * @param filename
   * @param path
   * @throws InterruptedException THIS METHOD HAS A TIMING ERROR IN THE EXPORTED .WAV FILE
   */
  public void playRandomOrder(int totalWords, int pause, String filename, String path)
          throws InterruptedException {
    if (numWords <= 0) {
      return;
    }

    int numSamples = 0;
    double samplingRate = myWordArray[0].getSamplingRate();
    int pauseFrames = (int) (samplingRate * (pause / 1000));

    //integer array used to keep track of the order that the random words are chosen in for usage when outputting a .wav file
    int[] wordOrder = new int[totalWords];

    //for loop that runs through a number of times equal to totalWords and each time chooses a random index to play from
    //myWordArray. Also sets the values of the int[] wordOrder for usage later. Also increments the sampleLength for the
    //creation of Sound exportSound
    for (int currentWord = 0; currentWord < totalWords; currentWord++) {
      int randomIndex = (int) (Math.random() * numWords);
      myWordArray[randomIndex].blockingPlay();
      numSamples += myWordArray[randomIndex].getNumSamples();
      wordOrder[currentWord] = randomIndex;
      Thread.sleep(pause);
    }

    //adds the number of samples that the pauses in between words make up to numSamples
    numSamples += (totalWords - 1) * (pauseFrames);

    //creates the Sound object to be exported, passes it the calculated number of samples
    Sound exportSound = new Sound(numSamples);

    //beginning of the loop to create the Sound object, loops through a number of times equal to totalWords, each time
    //writing the samples of the current word, along with a pause into it, before continuing to loop
    //exportSoundSample is only incremented in the loops integrated into this loop, so it keeps track of which sample index
    //it should be at for the Sound itself
    for (int i = 0, exportSoundSample = 0; i < totalWords; i++) {
      //the loop that takes in the length in samples of the current word that is being written over to the new Sound
      //object. the order at which it the random words were chosen are stored in the in[] wordOrder. By going
      //through the array incrementally, only up to the passed in parameter totalWords
      //thus being able to correctly increment through the number of samples in each Sound object within the
      //Sound[] myWordArray
      for (int wordSample = 0; wordSample < myWordArray[wordOrder[i]].getNumSamples(); wordSample++, exportSoundSample++) {
        exportSound.setSampleValueAt(exportSoundSample, myWordArray[wordOrder[i]].getSampleValueAt(wordSample));
      }

      //increments through the samples in exportSound for the number of samples in a calculated pause
      //and sets them equal to zero to simulate silence
      for (int pauseSample = 0; pauseSample < pauseFrames; pauseSample++, exportSoundSample++) {
        exportSound.setSampleValueAt(exportSoundSample, 0);
      }
    }

    File exportFile = new File(path, filename);
    exportSound.write(exportFile.getPath());
  }

  /**
   * Plays the words in random order, playing each word only once
   *
   * @param pause the number of milliseconds to pause between words
   * @throws InterruptedException
   */
  public void playRandomUnique(int pause) throws InterruptedException {
    boolean[] hasBeenPlayed = new boolean[numWords];
    boolean isDone = false;

    while (isDone == false) {
      int randomIndex = (int) (Math.random() * numWords);

      if (hasBeenPlayed[randomIndex] == false) {
        myWordArray[randomIndex].blockingPlay();
        Thread.sleep(pause);
        hasBeenPlayed[randomIndex] = true;
      }

      isDone = true;
      for (int i = 0; isDone && i < numWords; i++) {
        if (hasBeenPlayed[i] == false) {
          isDone = false;
        }
      }
    }
  }

  public void playRandomUnique(int pause, String filename, String path) {

  }

  /**
   * Plays the sound words in reverse order (e.g. 'this is a test' will be played 'test a is this')
   *
   * @param pause the number of milliseconds to pause between words
   * @throws InterruptedException
   */
  public void playReverseOrder(int pause) throws InterruptedException {
    for (int i = numWords - 1; i >= 0; i--) {
      myWordArray[i].blockingPlay();
      Thread.sleep(pause);
    }
  }

  public void playReverseOrder(int pause, String filename, String path) {

  }

  /**
   * Plays random consecutive pairs of words with only a 100 millisecond pause between them, with a
   * four hundred millisecond pause between pairs Ex: for 'this is a test' a pair would be 'this is'
   * or 'is a' or 'a test'
   *
   * @param numDoublets the number of doublets to play
   * @throws InterruptedException
   */
  public void playDoublets(int numDoublets) throws InterruptedException {
    playNlets(2, 100, 400, numDoublets);
  }

  /**
   *
   * @param numDoublets
   * @param filename
   * @param path
   * @throws InterruptedException
   */
  public void playDoublets(int numDoublets, String filename, String path)
          throws InterruptedException {
    playNlets(2, 100, 400, numDoublets, filename, path);
  }

  /**
   * Plays random consecutive triplets of words with only a 100 millisecond pause between the three
   * words, with a four hundred millisecond pause between triplets Ex: for 'this is a test' a
   * triplet would be 'this is a' or 'is a test'
   *
   * @param numTriplets the number of triplets to play
   * @throws InterruptedException
   */
  public void playTriplets(int numTriplets) throws InterruptedException {
    playNlets(3, 100, 400, numTriplets);
  }

  public void playTriplets(int numTriplets, String filename, String path)
          throws InterruptedException {
    playNlets(3, 100, 400, numTriplets, filename, path);
  }

  /**
   *
   * @param n
   * @param pauseBetweenWords
   * @param pauseBetweenRounds
   * @param numberOfRounds
   * @throws InterruptedException
   */
  public void playNlets(int n, int pauseBetweenWords, int pauseBetweenRounds, int numberOfRounds)
          throws InterruptedException {
    if (numWords < n || n < 1) {
      return;
    }

    for (int i = 0; i < numberOfRounds; i++) {
      int randomIndex = (int) Math.round(Math.random() * (numWords - n));
      for (int j = randomIndex; j < n + randomIndex; j++) {
        myWordArray[j].blockingPlay();
        Thread.sleep(pauseBetweenWords);
      }
      Thread.sleep(pauseBetweenRounds);
    }
  }

  public void playNlets(int n, int pauseBetweenWords, int pauseBetweenRounds, int numNlets,
          String filename, String path) throws InterruptedException {

    // Range check the value of n to reject too large or too small numbers
    if (numWords < n || n < 1) {
      return;
    }

    // Create an output array that will contain all of the played sounds as well as the pause sounds
    // and then write the new output array to a file.
    // total sounds will be equals to n * numNlets + (pauseBetweenWords * (n - 1))
    int totalSounds = 2 * n * numNlets - 1;

    // The outputSounds layout will look like this
    // outputSounds[0] = first sound to play
    // outputSounds[1] = first pause
    // outputSounds[2] = next sound
    // outputSounds[3] = next pause
    // ...
    Sound[] outputSounds = new Sound[totalSounds];

    // Create a new sound to represent the pauses between words.  Later, the copy constructor will
    // be called to write this pause between each sound in the outputArray
    Sound pauseSound = new Sound(pauseBetweenWords);
    int pauseSoundSamples = pauseSound.getNumSamples();

    // Keep track of the number of total samples in the outputArray
    int totalSamples = 0;

    // Do the full loop numNlet number of times
    for (int i = 0; i < numNlets; i++) {
      int randomIndex = (int) (Math.random() * (numWords - n) + 0.5);

      // Setup an inner loop to select runs of sounds n words in length
      for (int j = 0; j < n; j++) {
        // Write the sound to the index specified by i * n + j
        int outputIndex = 2 * (i * n + j);
        outputSounds[outputIndex] = new Sound(myWordArray[randomIndex + j]);
        totalSamples += myWordArray[randomIndex + j].getNumSamples();

        // Play the sound
        myWordArray[randomIndex + j].blockingPlay();

        // Pause
        Thread.sleep(pauseBetweenWords);

        // Call the copy constructor passing the pauseSound and write it to the next index in the 
        // outputSounds array except for the final pause which is omitted.
        if (outputIndex + 1 < totalSounds) {
          outputSounds[outputIndex + 1] = new Sound(pauseSound);
        }

        totalSamples += pauseSoundSamples;
      }

      Thread.sleep(pauseBetweenRounds);
    }

//     This takes the sampling rate and adds 0.5 to it to handle how Java rounds by default.
//     For simple casts to int, java drops the decimal places which rounds to nearest neighbor
//     i.e. 2.95 rounds to 2 with int cast.  But adding 0.5 changes the behavior.
//     i.e. 2.95 + 0.5 = 3.45 which rounds down to 3.
    int samplingRate = (int) (myWordArray[0].getSamplingRate() + 0.5);
    // Create a final sound to write to disk
    Sound finalSound = new Sound(totalSamples, samplingRate);

    // At this point, the outputSounds array should contain all the random sounds and pauses between
    // sounds so write it to the output file.
    for (int i = 0, finalSoundIndex = 0; i < outputSounds.length; i++) {
      for (int j = 0; j < outputSounds[i].getLength(); j++) {
        finalSound.setSampleValueAt(finalSoundIndex++, outputSounds[i].getSampleValueAt(j));
      }
    }

    File outputFile = new File(path, filename);
    finalSound.write(outputFile.getPath());
  }
}
