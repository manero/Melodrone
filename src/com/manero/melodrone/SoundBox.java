package com.manero.melodrone;

import java.util.ArrayList;
import java.util.List;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class SoundBox {
	AudioTrack mainTrack;
	float duration = 0.125f; // seconds
	private final int sampleRate = 44100;
	private final int numSamples = (int)(duration * sampleRate);
	private final double sample[] = new double[numSamples];
//	private final double freqOfTone = 440; // hz
//	private final byte generatedSnd[] = new byte[2 * numSamples];
	ArrayList<byte[]> snds;
	
	public SoundBox() {
		//http://www.bongo.net/theremin/AndroidSound.htm
		int minSize = AudioTrack.getMinBufferSize(sampleRate,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT);
		mainTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, minSize, AudioTrack.MODE_STREAM);
		mainTrack.play();
		snds = new ArrayList<byte[]>();
		
		generateAllTones();
	}

	//http://marblemice.blogspot.com/2010/04/generate-and-play-tone-in-android.html
	void generateAllTones() {
		double frequency;
		for (int j = 0; j < 16; j++) {
			frequency = 100 * j;
			byte generatedSnd[] = new byte[2 * numSamples];
			for (int i = 0; i < numSamples; ++i) {
				sample[i] = Math.sin(2 * Math.PI * i / (sampleRate / frequency));
			}

			// convert to 16 bit pcm sound array
			// assumes the sample buffer is normalised.
			int idx = 0;
			for (double dVal : sample) {
				short val = (short) (dVal * 32767);
				generatedSnd[idx++] = (byte) (val & 0x00ff);
				generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
			}
			snds.add(generatedSnd);
		}
	}

	public void playTone(int index) {
		// fill out the array
		mainTrack.write(snds.get(index), 0, numSamples);
	}
}
