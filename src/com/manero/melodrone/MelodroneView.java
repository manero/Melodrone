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
	boolean lightingUp = true;

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
		mMelo = new Melodrone(mWidth, mHeight, context);

		Thread thread = new Thread() {
			@Override

			public void run() {
				while (true) {
					mMelo.update();
					postInvalidate();
					try {
						Thread.sleep(125);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();

		/* force repaint */
		invalidate();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			mMelo.touchMove(event.getX(), event.getY(), lightingUp);
			break;
		case MotionEvent.ACTION_DOWN:
			lightingUp = mMelo.touchDown(event.getX(), event.getY());
			break;

		default:
			break;
		}
		invalidate();
		return true;
	}
	
    @Override
    public void onDraw(Canvas canvas) {
    	mMelo.draw(canvas, paint);
    }
}
