package com.manero.melodrone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MelodroneActivity extends Activity {
    private MelodroneView mMelodroneView;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        mMelodroneView = new MelodroneView(this);
        setContentView(mMelodroneView);
        mMelodroneView.requestFocus();
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	mMelodroneView.pause();
    	this.setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
    }
    
    public void onResume(){
    	super.onResume();
    	mMelodroneView.resume();
    	this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.clear_menu_item:
            mMelodroneView.reset();
            return true;
        case R.id.about_menu_item:
        	showAboutDialog();
            return true;
        case R.id.exit_menu_item:
        	System.exit(0);
            return true;
        case R.id.life:
        	//toggle life
        	Defaults.life = !Defaults.life;
        	//sets menu icon
        	if (Defaults.life) {
        		item.setTitle("Disable Game of Life");
        	} else {
        		item.setTitle("Enable Game of Life");
        	}
        	return true;
        	
        case R.id.grid:
        	//toggle grid
        	Defaults.grid = !Defaults.grid;
        	if (Defaults.grid) {
        		item.setTitle("hide grid");
        	} else {
        		item.setTitle("show grid");
        	}
        	return true;
/*        	
        case R.id.save:
        	//@debug not ready yet.
//        	mMelodroneView.serialize();
        	return true;
        case R.id.load:
        	//@debug not ready yet.
//        	mMelodroneView.deserialize();
        	return true;
*/
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    

    
    public void showAboutDialog(){
  	  final TextView message = new TextView(this);
  	  final SpannableString s = 
  	               new SpannableString(this.getText(R.string.about_text));
  	  Linkify.addLinks(s, Linkify.WEB_URLS);
  	  message.setText(s);
  	  message.setMovementMethod(LinkMovementMethod.getInstance());

  	  AlertDialog about = new AlertDialog.Builder(this)
  	  	.setTitle(R.string.app_name)
  	  	.setIcon(android.R.drawable.ic_dialog_info)
  	  	.setPositiveButton(this.getString(android.R.string.ok), null)
  	  	.setNeutralButton("Rate me!", new DialogInterface.OnClickListener() {
  	  		public void onClick(DialogInterface dialog, int id) {
  	  			Intent intent = new Intent(Intent.ACTION_VIEW);
  	  			intent.setData(Uri.parse("market://details?id=com.manero.melodrone"));
  	  			startActivity(intent);
           }
  	  	})
  	  	.setView(message)
  	  	.create();
  	  about.show();
  }
}