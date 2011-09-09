package com.manero.melodrone;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;


public class Melodrone {
	public enum NoteState {
		OFF,
		ON,
		PLAYING,
	}
	
	static int GRID_SIDE = 16;
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
	
	
	//gfx
	int mWidth;
	int mHeight;
	Rect mCellSize;
	public Melodrone() {

	}
	
	public Melodrone(int width, int height) {
		mWidth = width;
		mHeight = height;
		mCellSize = new Rect(0,0, width/GRID_SIDE, height/GRID_SIDE);
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
			}
			if (mNotes[lastBeat][i] == NoteState.PLAYING) {
				mNotes[lastBeat][i] = NoteState.ON;
			}
		}
		mCurrentBeat++;
		printGrid();
	}

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
	
	public boolean touch(float x, float y) {
		// find column and row
		int column = (int) (x / mCellSize.width());
		int row = (int) (y / mCellSize.height());
		
		if (mNotes[column][row] == NoteState.OFF)
			mNotes[column][row] = NoteState.ON;
		else
			mNotes[column][row] = NoteState.OFF;
		return true;
	}
}

