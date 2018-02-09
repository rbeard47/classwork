    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package BeardWill_Assignment1_ITIS1213;

    import BookClasses.FileChooser;
    import BookClasses.Sound;
    import BookClasses.SoundException;
    import BookClasses.SoundSample;
    import java.util.logging.Level;
    import java.util.logging.Logger;

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
         * Plays the words, in order with a parameter-specified pause between each and writes the resulting sound out to a file
         * @param pause the number of milliseconds to pause between words
         * @param filename the name of the file to write
         * @param path the path where the file should be written
         * @throws InterruptedException 
         */
        public void play(int pause, String filename, String path) throws InterruptedException {
            /*plays the words in order with a specified pause in between each and writes this new sound to a file with a
             *specified location
             *CURRENT METHOD
             */
            for (int i = 0; i < numWords; i++) {
                myWordArray[i].blockingPlay();
                Thread.sleep(pause);
            }
            
        }

        /**
         * Plays the words in random order, each word can be played multiple times
         *
         * @param totalWords the total number of words that will be played
         * @param pause the number of milliseconds to pause between words
         * @throws InterruptedException
         */
        public void playRandomOrder(int totalWords, int pause) throws InterruptedException {

        }

        /**
         * Plays the words in random order, playing each word only once
         *
         * @param pause the number of milliseconds to pause between words
         * @throws InterruptedException
         */
        public void playRandomUnique(int pause) throws InterruptedException {

        }

        /**
         * Plays the sound words in reverse order (e.g. 'this is a test' will be
         * played 'test a is this')
         *
         * @param pause the number of milliseconds to pause between words
         * @throws InterruptedException
         */
        public void playReverseOrder(int pause) throws InterruptedException {

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

        }
        
        /* This section conntains all of the
           helper methods that I have written to 
           aid the simplification of my other code
        */
        int i = 0;
        double samplingRate = myWordArray[0].getSamplingRate();
        Sound exportSound = null;
        Sound soundThis = myWordArray[0];
        Sound soundIs = myWordArray[1];
        Sound soundA = myWordArray[2];
        Sound soundTest = myWordArray[3];
        int numSamplesThis = soundThis.getNumSamples();
        int numSamplesIs = soundIs.getNumSamples();
        int numSamplesA = soundA.getNumSamples();
        int numSamplesTest = soundTest.getNumSamples();
        public int thisSound() {
            //writes the samples from the originalSource of the word "this"
            //to the new Sound object
            
            for(int j = 0; j < numSamplesThis; i++, j++) {
            exportSound.setSampleValueAt(i, soundThis.getSampleValueAt(j));
        }        
         return i;  
        }
        public int isSound() {
            //writes the samples from the originalSource of the word "is"
            //to the new Sound object
            
            for(int j = 0; j < numSamplesIs; i++, j++) {
            exportSound.setSampleValueAt(i, soundIs.getSampleValueAt(j));
        }        
         return i;  
        }
        public int aSound() {
            //writes the samples from the originalSource of the word "a"
            //to the new Sound object
            
            for(int j = 0; j < numSamplesA; i++, j++) {
            exportSound.setSampleValueAt(i, soundA.getSampleValueAt(j));
        }        
         return i;  
        }
        public int testSound() {
            //writes the samples from the originalSource of the word "test"
            //to the new Sound object
            
            for(int j = 0; j < numSamplesTest; i++, j++) {
            exportSound.setSampleValueAt(i, soundTest.getSampleValueAt(j));
        }        
         return i;  
        }
        public int space(int time) {
            //writes in a space of silence
            //to the new Sound object
            
            for(int j = 0; j < (int) samplingRate * time; i++, j++) {
            exportSound.setSampleValueAt(i, soundIs.getSampleValueAt(j));
        }        
         return i;  
        }
        
        
    }
