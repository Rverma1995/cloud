package com.example.musicplay;

import java.io.File;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;


public class MusicService extends Service
{
static NotificationManager nm;
static Notification.Builder nb;
Notification n;
Intent i;
File f1=Environment.getExternalStoragePublicDirectory("songs");
String list1[]=f1.list();
Intent i1, i2, i3;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nb=new Notification.Builder(this);
		i1=new Intent(this,MusicPlay2.class);
		i1.putExtra("key", "prev");
		i2=new Intent(this,MusicPlay2.class);
		i2.putExtra("key", "stop");
		
		i3=new Intent(this,MusicPlay2.class);
		i3.putExtra("key", "next");
		PendingIntent pi1=PendingIntent.getService(this, 1, i1, PendingIntent.FLAG_UPDATE_CURRENT);
    	PendingIntent pi2=PendingIntent.getService(this, 2, i2, PendingIntent.FLAG_UPDATE_CURRENT);
    	PendingIntent pi3=PendingIntent.getService(this, 3, i3, PendingIntent.FLAG_UPDATE_CURRENT);
    	nb.setSmallIcon(R.drawable.ic_launcher);
    	nb.setTicker("Playing Music");
   
    	nb.setContentTitle(list1[MainActivity.id]);
    	nb.setContentText("Music");
    	Intent in=new Intent(this,MainActivity.class);
    	PendingIntent pi= PendingIntent.getActivity(this, 1, in , 0);
    	nb.setContentIntent(pi);
    	nb.addAction(android.R.drawable.ic_media_previous, "Prev", pi1);
    	nb.addAction(android.R.drawable.ic_media_pause, "Stop", pi2);
    	nb.addAction(android.R.drawable.ic_media_next, "Next", pi3);
    	//nb.setOngoing(true);
	   	n=nb.build();
    	startForeground(1, n);
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			//stopService(i1);
			//stopService(i);
			//nb.setOngoing(false);
			//stopForeground(false);
		
			
		}
}

