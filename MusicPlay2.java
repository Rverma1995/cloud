package com.example.musicplay;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

public class MusicPlay2 extends Service implements OnCompletionListener

{
	
static MediaPlayer mp;
String s;
Intent  i1;

int pos;



	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
	 pos= intent.getIntExtra("pos", 0);
		s=intent.getStringExtra("key").toString();
	//	Boolean k= intent.getBooleanExtra("key", false);
		if(s.equals("play"))
		{
			play();
			
		}
		else if(s.equals("pause"))
		
			{
			pause();
			}
		else if(s.equals("stop"))
		
			{
				stop();
			}
			
			
			
		else if(s.equals("next"))
		{
			if(mp!=null)
			{
				mp.stop();
				mp=null;
			}
			if(MainActivity.id==MainActivity.list.length-1)
				MainActivity.id=0;
			else
				MainActivity.id++;
			play();
			//seek();
		}
		else if(s.equals("prev"))
		{
			if(mp!=null)
			{
				mp.stop();
				mp=null;
			}
			if(pos==0)
				pos=MainActivity.list.length-1;
			else
				pos--;
			play();
			
		}
	
		return super.onStartCommand(intent, flags, startId);
	}

	public void play()
	{
		
		if(mp==null)
		{
			
			mp=MediaPlayer.create(MusicPlay2.this, Uri.parse(MainActivity.f.getAbsolutePath()+"/"+MainActivity.list[pos]));
			MainActivity.sb1.setMax(mp.getDuration());
			MainActivity.sb1.setProgress(mp.getCurrentPosition());
			mp.start();
			
		}	
	
		
		mp.setOnCompletionListener(this);
		mp.start();
		MainActivity.b.setText("Pause");
		i1=new Intent(this,MusicService.class);
		startService(i1);
	}
	
	public void pause()
	{
		if(mp!=null)
		{
			mp.pause();
			MainActivity.b.setText("Play");
			MusicService.nb.setOngoing(false);
		}
		
	}
	

	
	public void stop()
	{
		if(mp!=null)
		{
			mp.stop();
			//mp.release();
			mp=null;
			MainActivity.b.setText("Play");
			MusicService.nm.cancelAll();
			stopService(i1);
			stopService(MainActivity.i);
		}
	}
	



/**/


@Override
public void onCompletion(MediaPlayer mp) {
	 
	Toast.makeText(this, "hi", Toast.LENGTH_LONG).show();
	MainActivity.sb1.setProgress(0);
	
	//play();

}







}
