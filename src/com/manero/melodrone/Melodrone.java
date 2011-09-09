package com.manero.melodrone;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


public class Melodrone {
	
	static int GRID_SIDE = 16;
	//model
	int mBeat = 0; // 0 to 15
	boolean[][] mNotes = 	{
			{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
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
		mBeat++;
		if (mBeat >= 16)
			mBeat = 0;
	}
	
	public void draw(Canvas canvas, Paint paint){
		Rect drawRect = new Rect();
		for (int i = 0; i < GRID_SIDE; i++) {
			for (int j = 0; j < GRID_SIDE; j++) {
				drawRect.set(mCellSize);
				drawRect.offset(i*mCellSize.width(), j*mCellSize.height());
				
				if (mNotes[i][j] == true) {
					paint.setColor(Color.WHITE);
				} else {
					paint.setColor(Color.GRAY);
				}
				canvas.drawRect(drawRect, paint);
			}
		}
	}
	
	public boolean touch(float x, float y) {
		// find column and row
		int column = (int) (x / mCellSize.width());
		int row = (int) (y / mCellSize.height());
		
//		if (mNotes[column][row])
//			lightingUp = false;
//		else
//			lightingUp = true;
//		
//		if (lightingUp)
//			mNotes[column][row] = true;
//		else
//			mNotes[column][row] = false;
		mNotes[column][row] = !mNotes[column][row]; 
//		return mNotes[column][row];
		return true;
	}
}

