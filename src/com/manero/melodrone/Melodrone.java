package com.manero.melodrone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;


public class Melodrone {
	public enum NoteState {
		OFF,
		ON,
		PLAYING,
	}
	
	static int GRID_SIDE = 16;
	static int BPM = 120;
	//model
	int mCurrentBeat = 0; // 0 to 15
	NoteState[][] mNotes = 	{
			{NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF},
			{NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF},
			{NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF},
			{NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF},
			{NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF},
			{NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF},
			{NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF},
			{NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF},
			{NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF},
			{NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF},
			{NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF},
			{NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF},
			{NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF},
			{NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF},
			{NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF},
			{NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF, NoteState.OFF},
							};
	SoundPool sp;
	int soundIds[] = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
	int mScaleOffset = 0;
	//gfx
	Rect mCellSize;

	public Melodrone() {

	}
	
	public Melodrone(int width, int height, Context context) {
		mCellSize = new Rect(0,0, width/GRID_SIDE, height/GRID_SIDE);
		sp = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
		soundIds[0] = sp.load(context, R.raw.re2, 1);
		soundIds[1] = sp.load(context, R.raw.mi2, 1);
		soundIds[2] = sp.load(context, R.raw.fas2, 1);
		soundIds[3] = sp.load(context, R.raw.la2, 1);
		soundIds[4] = sp.load(context, R.raw.si2, 1);

		soundIds[5] = sp.load(context, R.raw.re3, 1);
		soundIds[6] = sp.load(context, R.raw.mi3, 1);
		soundIds[7] = sp.load(context, R.raw.fas3, 1);
		soundIds[8] = sp.load(context, R.raw.la3, 1);
		soundIds[9] = sp.load(context, R.raw.si3, 1);

		soundIds[10] = sp.load(context, R.raw.re4, 1);
		soundIds[11] = sp.load(context, R.raw.mi4, 1);
		soundIds[12] = sp.load(context, R.raw.fas4, 1);
		soundIds[13] = sp.load(context, R.raw.la4, 1);
		soundIds[14] = sp.load(context, R.raw.si4, 1);

		soundIds[15] = sp.load(context, R.raw.re5, 1);
		soundIds[16] = sp.load(context, R.raw.mi5, 1);
		soundIds[17] = sp.load(context, R.raw.fas5, 1);
		soundIds[18] = sp.load(context, R.raw.la5, 1);
		soundIds[19] = sp.load(context, R.raw.si5, 1);
}

	public void update() {
		if (mCurrentBeat >= GRID_SIDE)
			mCurrentBeat = 0;
		for (int i = 0; i < GRID_SIDE; i++) {
//			int lastBeat = (mCurrentBeat - 1) % 16;
			int lastBeat = mCurrentBeat - 1;
			if (lastBeat == -1) lastBeat = 15;
			if (mNotes[mCurrentBeat][i] == NoteState.ON) {
				mNotes[mCurrentBeat][i] = NoteState.PLAYING;
				if (soundIds[i] != -1){
					sp.play(soundIds[16-(i+mScaleOffset)], 1, 1, 1, 0, 1f);	
				}
			}
			if (mNotes[lastBeat][i] == NoteState.PLAYING) {
				mNotes[lastBeat][i] = NoteState.ON;
			}
		}
		mCurrentBeat++;
//		printGrid(); //debug purposes
	}

	@SuppressWarnings("unused")
	private void printGrid() {
		Log.i("grid", "mCurrentBeat = " + mCurrentBeat);
		Log.i("grid", "  -------------------------");
		for (int i = 0; i < GRID_SIDE; i++) {
			String currentLine = " |";
			for (int j = 0; j < GRID_SIDE; j++) {
				switch (mNotes[j][i]) {
				case OFF:
					currentLine = currentLine.concat(" ");
					break;
				case ON:
					currentLine = currentLine.concat("o");
					break;
				case PLAYING:
					currentLine = currentLine.concat("X");
					break;

				default:
					break;
				}
				
			}
			currentLine = currentLine.concat("|");
			Log.i("grid", currentLine);
		}
		Log.i("grid", "  -------------------------");
	}
	
	public void draw(Canvas canvas, Paint paint){
		Rect drawRect = new Rect();
		for (int i = 0; i < GRID_SIDE; i++) {
			for (int j = 0; j < GRID_SIDE; j++) {
				drawRect.set(mCellSize);
				drawRect.offset(i*mCellSize.width(), j*mCellSize.height());
				
				switch (mNotes[i][j]) {
				case OFF:
					paint.setColor(Color.BLACK);
					break;
				case ON:
					paint.setColor(Color.GRAY);
					break;
				case PLAYING:
					paint.setColor(Color.WHITE);
					break;

				default:
					break;
				}
				canvas.drawRect(drawRect, paint);
			}
		}
	}
	
	public boolean touchDown(float x, float y) {
		boolean lightingUp = false;
		// find column and row
		int column = (int) (x / mCellSize.width());
		int row = (int) (y / mCellSize.height());
		
		if (mNotes[column][row] == NoteState.OFF) {
			mNotes[column][row] = NoteState.ON;
			lightingUp = true;
		}
		else {
			mNotes[column][row] = NoteState.OFF;
			lightingUp = false;
		}
		return lightingUp;
	}

	public void touchMove(float x, float y, boolean lightingUp) {
		int column = (int) (x / mCellSize.width());
		int row = (int) (y / mCellSize.height());
		//adjusting to the bounds of the array
		if(column >= mNotes.length){
			column = mNotes.length - 1;
		}
		if(row >= mNotes.length){
			row = mNotes[column].length - 1;
		}
		
		if (lightingUp) {
			mNotes[column][row] = NoteState.ON;
		} else {
			mNotes[column][row] = NoteState.OFF;
		}
	}

	public void reset() {
		for (int i = 0; i < GRID_SIDE; i++) {
			for (int j = 0; j < GRID_SIDE; j++) {
				mNotes[i][j] = NoteState.OFF;
			}
		}
	}
}

