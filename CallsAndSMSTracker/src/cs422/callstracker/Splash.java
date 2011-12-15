package cs422.callstracker;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Splash extends Activity {
	private MediaPlayer ourSong;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		ourSong = MediaPlayer.create(Splash.this, R.raw.kalimba);
		ourSong.start();
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(5000);
				} catch (InterruptedException e){
					e.printStackTrace();
				}finally{
						startActivity(new Intent(Splash.this, EditPlan.class));
				}
			}
		};
        timer.start();
	}

	@Override
	protected void onPause() 
	{
		// TODO Auto-generated method stub
		super.onPause();
		ourSong.release();
		finish();
	}
}
