package com.manero.melodrone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

	SoundBox sb;
	
	public Melodrone() {

	}
	
	public Melodrone(int width, int height, Context context) {
		int lado = width/GRID_SIDE;
		if (height/GRID_SIDE < lado) lado = height/GRID_SIDE;
		mCellSize = new Rect(0,0, lado, lado);

		
		sb = new SoundBox();
}

	public void update() {
		if (mCurrentBeat >= GRID_SIDE) {
			mCurrentBeat = 0;
			if (Defaults.life) {
				life();
			}
		}
		for (int i = 0; i < GRID_SIDE; i++) {
			int lastBeat = mCurrentBeat - 1;
			if (lastBeat == -1) lastBeat = 15;
			if (mNotes[mCurrentBeat][i] == NoteState.ON) {
				mNotes[mCurrentBeat][i] = NoteState.PLAYING;
				sb.playTone(i);
			}
			if (mNotes[lastBeat][i] == NoteState.PLAYING) {
				mNotes[lastBeat][i] = NoteState.ON;
			}
		}
		mCurrentBeat++;
	}

	private void life() {
		boolean[][] aux = new boolean[16][16];
		for (int i = 0; i < GRID_SIDE; i++) {
			for (int j = 0; j < GRID_SIDE; j++) {
				aux[i][j] = checkNeighbors(i, j); 
			}
		}
		for (int i = 0; i < GRID_SIDE; i++) {
			for (int j = 0; j < GRID_SIDE; j++) {
				if (aux[i][j])
					mNotes[i][j] = NoteState.ON;
				else
					mNotes[i][j] = NoteState.OFF;
			}
		}
	}

	private boolean checkNeighbors(int i, int j) {
//		rules:
//		Any live cell with fewer than two live neighbours dies, as if caused by under-population.
//		Any live cell with two or three live neighbours lives on to the next generation.
//		Any live cell with more than three live neighbours dies, as if by overcrowding.
//		Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
		int liveNeighbors = 0;
		try {
			if (mNotes[i-1][j-1] != NoteState.OFF) liveNeighbors++;
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}

		
		try {
			if (mNotes[i  ][j-1] != NoteState.OFF) liveNeighbors++;
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
		try {
			if (mNotes[i+1][j-1] != NoteState.OFF) liveNeighbors++;
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}

		try {
		if (mNotes[i-1][j  ] != NoteState.OFF) liveNeighbors++;
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
//		if (mNotes[i  ][j  ] != NoteState.OFF) liveNeighbors++;
		try {
			if (mNotes[i+1][j  ] != NoteState.OFF) liveNeighbors++;
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}

		try {
			if (mNotes[i-1][j+1] != NoteState.OFF) liveNeighbors++;
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
		try {
			if (mNotes[i  ][j+1] != NoteState.OFF) liveNeighbors++;
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
		try {
			if (mNotes[i+1][j+1] != NoteState.OFF) liveNeighbors++;
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}

		if (liveNeighbors < 2 && (mNotes[i][j] != NoteState.OFF)) {
			return false;
		} else if ((liveNeighbors == 2 || liveNeighbors == 3) && (mNotes[i][j] != NoteState.OFF)) {
			return true;
		} else if (liveNeighbors > 3 && (mNotes[i][j] != NoteState.OFF)) {
			return false;
		} else if ((mNotes[i][j] == NoteState.OFF) && liveNeighbors == 3) {
			return true;
		} else return false; //should never reach this point
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
				if (Defaults.grid) {
					if (i == mCurrentBeat - 1)
						paint.setColor(Color.GRAY);
					else
						paint.setColor(Color.DKGRAY);
					canvas.drawRect(drawRect, paint);
					drawRect.set(drawRect.left+1, drawRect.top+1, drawRect.right-1, drawRect.bottom-1);
				}
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
		try {		//lazy fix... i should instead find out why i get ArrayIndexOutOfBoundsException instead of wrapping it up in a try/catch block 
			if (mNotes[column][row] == NoteState.OFF) {
				mNotes[column][row] = NoteState.ON;
				lightingUp = true;
			} else {
				mNotes[column][row] = NoteState.OFF;
				lightingUp = false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
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
	
	public void serialize() {
		short one = 1;
		for (int i = 0; i < GRID_SIDE; i++) {
			short line = 0;
			for (int j = 0; j < GRID_SIDE; j++) {
				if (mNotes[i][j] == NoteState.ON) {
					line = (short) (line | (one << j));
				}
			}
		}
	}
	
	public void deserialize() {
		short source[] = {4, 5, 7, 9, 12, 8, 23, 8, 23, 8, 3, 8, 56, 9, 2, 9};
		short one = 1;
		int on = 0;
		for (int i = 0; i < GRID_SIDE; i++) {
			for (int j = 0; j < GRID_SIDE; j++) {
				on = source[i] & (one << j);
				if (on > 0) mNotes[i][j] = NoteState.ON; else mNotes[i][j] = NoteState.OFF;
			}
		}
	}
}

