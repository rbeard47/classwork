/*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
 */
package BeardWill_Assignment1_ITIS1213;

import BookClasses.Sound;
import java.io.File;

/**
 * This class contains methods for mixing up the words in an audio file and
 * creating sound poetry out of them. It contains many stub methods which need
 * to be completed as part of Assignment 1.
 *
 * @author clatulip, (will)
 */
public class AudioPoem {

    static final int MAX_NUM_WORDS = 100;
    static private Sound[] myWordArray = new Sound[MAX_NUM_WORDS];

    static private int numWords = 0;

    public AudioPoem(Sound originalSource, int[] spliceArray, int numSplicePoints) {

        // break the sound into sepearate words, copying each into the word array
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
     * Plays the words, in order with a parameter-specified pause between each
     * and writes the resulting sound out to a file
     *
     * @param pause the number of milliseconds to pause between words
     * @param filename the name of the file to write
     * @param path the path where the file should be written
     * @throws InterruptedException
     */
    public void play(int pause, String filename, String path) throws InterruptedException {
        /*plays the words in order with a specified pause in between each and writes this new sound to a file with a
         *specified location
         */
        //plays the words in order with a specified pause in between each
        for (int i = 0; i < numWords; i++) {
            myWordArray[i].blockingPlay();
            Thread.sleep(pause);
        }
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
            
            //checks to see if this is the last word in the Sound[] myWordArray to ensure no pause is put after the last word
            boolean last = (i==numWords - 1);
            //write number of samples in specified pause time to exportSound's sampleArray to a value of 0
            for (int pauseSample = 0; !last && pauseSample < pauseFrames; pauseSample++, exportSoundSample++) {
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
     * @throws InterruptedException
     */
    public void playRandomOrder(int totalWords, int pause, String filename, String path) throws InterruptedException {
        
        int numSamples = 0;
        double samplingRate = myWordArray[0].getSamplingRate();
        int pauseFrames = (int) (samplingRate * (pause / 1000));
        
        //integer array used to keep track of the order that the random words are chosen in for usage when outputting a .wav file
        int[] wordOrder = new int[100];
        
        //for loop that runs through a number of times equal to totalWords and each time chooses a random index to play from
        //myWordArray. Also sets the values of the int[] wordOrder for usage later. Also increments the sampleLength for the
        //creation of Sound exportSound
        for (int currentWord = 0; currentWord < totalWords; currentWord++) {
            
            //generates a random index randomIndex
            int randomIndex = (int) (Math.random() * numWords);
            //plays the Sound from the Sound[] myWordArray at the index randomIndex
            myWordArray[randomIndex].blockingPlay();
            //adds the length of the sound at the Sound[] myWordArray in samples to the variable numSamples
            numSamples += myWordArray[randomIndex].getNumSamples();
            //sets the int[] wordOrder at the index currentWord to the value of the random index randomIndex for later use
            wordOrder[currentWord] = randomIndex;
            //sleeps for the time specified by the parameter pause passed in at the beginning of the method
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
        for(int i = 0, exportSoundSample = 0; i < totalWords; i++) {
            
            //the loop that takes in the length in samples of the current word that is being written over to the new Sound
            //object. the order at which it the random words were chosen are stored in the in[] wordOrder. By going
            //through the array incrementally, only up to the passed in parameter totalWords
            //thus being able to correctly increment through the number of samples in each Sound object within the
            //Sound[] myWordArray
            for(int wordSample = 0; wordSample < myWordArray[wordOrder[i]].getNumSamples(); wordSample++, exportSoundSample++) {
                
                //copies the sample value of the current sample of the sound in the Sound[] myWordArray
                //to the current sample of the exportSound Sound object
                exportSound.setSampleValueAt(exportSoundSample, myWordArray[wordOrder[i]].getSampleValueAt(wordSample));
                
            }
            
            //checks to see if this is the last word in the Sound[] myWordArray to ensure no pause is put after the last word
            boolean last = (i==totalWords - 1);
            //increments through the samples in exportSound for the number of samples in a calculated pause
            //and sets them equal to zero to simulate silence
            for(int pauseSample = 0; !last && pauseSample < pauseFrames; pauseSample++, exportSoundSample++) {
                
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
        
        //create a boolean[] to keep track of whether or not the random sound has been played
        boolean[] hasBeenPlayed = new boolean[numWords];
        //initializes the boolean isDone to false;
        boolean isDone = false;
        
        //creates a loop that runs until every element of the boolean[] hasBeenPlayed returns the value true, upon which
        //it will break and continue on to the rest of the code
        while (isDone == false) {
            
            //selects a random index that is used by the rest of the code in the loop
            int randomIndex = (int) (Math.random() * numWords);
            
            //checks if the current randomIndex index of the boolean[] hasBeenPlayed returns false
            //if so, it continues
            if (hasBeenPlayed[randomIndex] == false) {
                
                //plays the Sound at the index randomIndex within the Sound[] myWordArray
                myWordArray[randomIndex].blockingPlay();
                //pauses for a specified time passed in as a parameter at the beginning of the method
                Thread.sleep(pause);
                //sets the boolean[] value at the index randomIndex to true
                hasBeenPlayed[randomIndex] = true;
            }
            //sets the boolean isDone to be true, which will break the while loop and continue on with the rest of the code
            isDone = true;
            
            //checks through all of the booleans in the boolean[] to ensure that they are true
            //if any of them are false, then it will set isDone to false and the while loop will resume
            for (int i = 0; isDone && i < numWords; i++) {
                if (hasBeenPlayed[i] == false) {
                    isDone = false;
                }
            }
        }
    }
    
    public void playRandomUnique(int pause, String filename, String path) {
        
        //finds the length of the new sound object
        int totalSamples = 0;
        double samplingRate = myWordArray[0].getSamplingRate();
        totalSamples += (int) ((numWords - 1) * (samplingRate * (pause / 1000)));
        double samplesInAPause = (int) (samplingRate * pause);
        
        for(int uniqueWord = 0; uniqueWord < numWords; uniqueWord++) {
            totalSamples += myWordArray[uniqueWord].getLength();
        }
        Sound exportSound = new Sound(totalSamples);
    
                
        //create a boolean[] to keep track of whether or not the random sound has been played
        boolean[] hasBeenPlayed = new boolean[numWords];
        //initializes the boolean isDone to false;
        boolean isDone = false;
        
        //creates a loop that runs until every element of the boolean[] hasBeenPlayed returns the value true, upon which
        //it will break and continue on to the rest of the code
        while (isDone == false) {
            
            //selects a random index that is used by the rest of the code in the loop
            int randomIndex = (int) (Math.random() * numWords);
            
            //checks if the current randomIndex index of the boolean[] hasBeenPlayed returns false
            //if so, it continues
            if (hasBeenPlayed[randomIndex] == false) {
                
                //plays the Sound at the index randomIndex within the Sound[] myWordArray
                myWordArray[randomIndex].blockingPlay();
                //pauses for a specified time passed in as a parameter at the beginning of the method
                Thread.sleep(pause);
                //sets the boolean[] value at the index randomIndex to true
                hasBeenPlayed[randomIndex] = true;
            }
            //sets the boolean isDone to be true, which will break the while loop and continue on with the rest of the code
            isDone = true;
            
            //checks through all of the booleans in the boolean[] to ensure that they are true
            //if any of them are false, then it will set isDone to false and the while loop will resume
            for (int i = 0; isDone && i < numWords; i++) {
                if (hasBeenPlayed[i] == false) {
                    isDone = false;
                }
            }
        }
        int exportSample = 0;
            for(int i = 0; i < numWords; i++) {
                for(int sample = 0; sample < myWordArray[i].getLength(); sample++, exportSample++) {
                    exportSound.setSampleValueAt(exportSample, myWordArray[i].getSampleValueAt(sample));
                }
                boolean last = (i==numWords - 1);
                for(int k = 0; !last && k < samplesInAPause; k++, exportSample++) {
                    exportSound.setSampleValueAt(exportSample, 0);
                }
            }
            
            File exportFile = new File(path, filename);
        exportSound.write(exportFile.getPath());
        
    }

    /**
     * Plays the sound words in reverse order (e.g. 'this is a test' will be
     * played 'test a is this')
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
     * Plays random consecutive pairs of words with only a 100 millisecond pause
     * between them, with a four hundred millisecond pause between pairs Ex: for
     * 'this is a test' a pair would be 'this is' or 'is a' or 'a test'
     *
     * @param numDoublets the number of doublets to play
     * @throws InterruptedException
     */
    public void playDoublets(int numDoublets) throws InterruptedException {
        if (numWords < 2) {
            System.out.println("Not enough words to make a doublet!");
            return;
        }

        for (int i = 0; i < numDoublets; i++) {

            int randomIndex = (int) (Math.random() * (numWords - 1));
            myWordArray[randomIndex].blockingPlay();
            Thread.sleep(100);
            myWordArray[randomIndex + 1].blockingPlay();
            Thread.sleep(400);
        }
    }
    
    public void playDoublets(int numDoublets, String filename, String path) {
        
    }

    /**
     * Plays random consecutive triplets of words with only a 100 millisecond
     * pause between the three words, with a four hundred millisecond pause
     * between triplets Ex: for 'this is a test' a triplet would be 'this is a'
     * or 'is a test'
     *
     * @param numTriplets the number of triplets to play
     * @throws InterruptedException
     */
    public void playTriplets(int numTriplets) throws InterruptedException {
        if (numWords < 3) {
            System.out.println("Not enough words to make a doublet!");
            return;
        }

        for (int i = 0; i < numTriplets; i++) {

            int randomIndex = (int) (Math.random() * (numWords - 2));
            myWordArray[randomIndex].blockingPlay();
            Thread.sleep(100);
            myWordArray[randomIndex + 1].blockingPlay();
            Thread.sleep(100);
            myWordArray[randomIndex + 2].blockingPlay();
            Thread.sleep(400);
        }

    }
    
    public void playTriplets(int numTriplets, String filename, String path) {
        
    }
}
