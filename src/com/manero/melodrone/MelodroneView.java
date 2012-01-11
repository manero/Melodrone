package com.manero.melodrone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Toast;

public class MelodroneView extends View implements OnTouchListener{
	Melodrone mMelo;
	Paint paint = new Paint();
	int mWidth;
	int mHeight;
	boolean paused = false;
	boolean lightingUp = true;

	public MelodroneView(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);

        setup(context);
        this.setOnTouchListener(this);
	}


	private void setup(Context context) {
		Display dsp = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

		mWidth = dsp.getWidth();
		mHeight = dsp.getHeight();
		if(mMelo == null ){
			Toast.makeText(context, R.string.initial_toast, 10).show();
			createNewMelodrone(context);
		}
	}


	private void createNewMelodrone(Context context) {
		mMelo = new Melodrone(mWidth, mHeight, context);

		startUpdatingThread();

		//force repaint
		invalidate();
	}


	private void startUpdatingThread() {
		Thread thread = new Thread() {
			@Override

			public void run() {
				while (true) {
					if (!paused) {
						mMelo.update();
						postInvalidate();
						try {
							Thread.sleep(125);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		thread.start();
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
    
    public void pause(){
    	paused = true;
    }
    public void resume(){
    	paused = false;
    }
    public void reset(){
    	mMelo.reset();
    }
    
    public void serialize() {
    	mMelo.serialize();
    }
    public void deserialize() {
    	mMelo.deserialize();
    }
}
