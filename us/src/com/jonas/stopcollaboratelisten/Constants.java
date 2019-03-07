package com.jonas.stopcollaboratelisten;



public interface Constants {
    
  public static final double kLowFrequency = 18000; //the lowest frequency used
  public static final double kFrequencyStep = 50; //the distance between frequencies

  public static final int kBytesPerDuration = 1; //how wide is the data stream 
  								// (rps - that is, the pre-encoding data stream, not the audio)
  								// (rps - kFrequencies must be this long)
  
  public static final int kBitsPerByte = 8; //unlikely to change, I know

  // Amplitude of each frequency in a frame.
  public static final double kAmplitude = 0.125d; /* (1/8) */

  // Sampling frequency (number of sample values per second)
  public static final double kSamplingFrequency = 44100; //rps - reduced to 11025 from 22050
  		// to enable the decoder to keep up with the audio on Motorola CLIQ  

  // Sound duration of encoded byte (in seconds)
  public static final double kDuration = 0.3; // rps - increased from 0.1 to improve reliability on Android 

  // Number of samples per duration
  public static final int kSamplesPerDuration = (int)(kSamplingFrequency * kDuration);

  //This is used to convert the floats of the encoding to the bytes of the audio
  public static final int kFloatToByteShift = 128;

  // The length, in durations, of the hail sequence
  public static final int kDurationsPerHail = 3; 

  // The frequency used in the initial hail of the key
  public static final int kHailFrequency = 18000;

  //The frequencies we use for each of the 8 bits
  public static final int kBaseFrequency = 19000;
  public static final int[] kFrequencies = {kBaseFrequency,   //1000
					    (int)(kBaseFrequency +125), //1125
					    (int)(kBaseFrequency +125*2), //1250
					    (int)(kBaseFrequency +125*3), //1500 
					    (int)(kBaseFrequency +125*4), //1666
					    (int)(kBaseFrequency +125*5), //2000
					    (int)(kBaseFrequency +125*6), //2250
					    (int)(kBaseFrequency +125*7)};//2500
  
  //The length, in durations, of the hail sequence
  public static final int kDurationsPerSOS = 1;
  
  // The frequency used to signal for help
  public static final int kSOSFrequency = (int)(kBaseFrequency +125); //1125
  
  // The length, in durations, of some session timing jitters
  public static final int kPlayJitter = 5;
  public static final int kSetupJitter = 10;
  
  // The length, in durations, of the CRC
  public static final int kDurationsPerCRC = 1;
}
