package com.manero.melodrone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public class MelodroneView extends View implements OnTouchListener{
	Melodrone mMelo = new Melodrone();
	Paint paint = new Paint();
	int mWidth;
	int mHeight;

	public MelodroneView(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        paint.setColor(Color.GREEN);
//        paint.setAntiAlias(true);
        setup(context);
	}


	private void setup(Context context) {
		Display dsp = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

		mWidth = dsp.getWidth();
		mHeight = dsp.getHeight();
		mMelo = new Melodrone(mWidth, mHeight);

		/* force repaint */
		invalidate();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN)
			if (this.mMelo.touch(event.getX(), event.getY()))
				invalidate();
		return false;
	}
	
    @Override
    public void onDraw(Canvas canvas) {
    	mMelo.draw(canvas, paint);
    }

}
