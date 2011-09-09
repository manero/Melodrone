package com.manero.melodrone;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MelodroneActivity extends Activity {
    private MelodroneView mMelodroneView;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        /* Call CloverView */ 
        mMelodroneView = new MelodroneView(this);
        setContentView(mMelodroneView);
        mMelodroneView.requestFocus();
    }
}