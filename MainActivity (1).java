package com.example.musicplay;

import android.os.Bundle;
import android.os.Environment;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;

import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SeekBar;


public class MainActivity extends Activity implements SensorEventListener
{
ListView lv;
static int id;
static String list[];
AutoCompleteTextView actv;
static Intent i;
static SeekBar sb1,sb2;
AudioManager am;
int max,max1;
String s1;

static File f;
TextView tv;
static Button b;
SensorManager sm;
Sensor sensor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv=(ListView) findViewById(R.id.listView1);
	    tv=(TextView)findViewById(R.id.textView1);
		lv=(ListView)findViewById(R.id.listView1);
		actv=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
		sb1=(SeekBar) findViewById(R.id.seekBar1);
		sb1.setProgress(0);
		sm=(SensorManager) getSystemService(SENSOR_SERVICE);
		sensor= sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		sb2=(SeekBar) findViewById(R.id.seekBar2);
		am=(AudioManager) getSystemService(AUDIO_SERVICE);
		b=(Button) findViewById(R.id.button1);
        int max=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        
        int current=am.getStreamVolume(AudioManager.STREAM_MUSIC);
        sb2.setMax(max);
        sb2.setProgress(current);
		f=Environment.getExternalStoragePublicDirectory("songs");
		list=f.list();
				

		ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,list);
		actv.setAdapter(adapter1);	
		actv.setThreshold(1);
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,list);
		lv.setAdapter(adapter);		
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0,View arg1,int pos,long arg3)
			{
				id=pos;
				if(MusicPlay2.mp!=null)
				{
					MusicPlay2.mp.stop();
					MusicPlay2.mp=null;
				}
				play();
				
			}
		});
		
		sb1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				

		
				// TODO Auto-generated method stub
				if(fromUser)
					if(MusicPlay2.mp!=null)
						{
						MusicPlay2.mp.seekTo(progress);
						sb1.setProgress(progress);
						}
			}
		});
		

		sb2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
						
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub
				am.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);				
			}
		});
		try
		{
		Thread t=new Thread()
				
				{
				public  void run()
				{
					super.run();
					while(true)
					{
						if(MusicPlay2.mp==null)
							MainActivity.sb1.setProgress(0);
						else
							MainActivity.sb1.setProgress(MusicPlay2.mp.getCurrentPosition());
					}
				}
			};
			t.start();
		}
		catch(Exception e)
		{
		}
	}
	

	
	
	
	public void playMusic(View v)
	{
		if(b.getText().toString().equals("Play"))
			{
			play();
			//seek();
			}
		else
		{
			i=new Intent(this,MusicPlay2.class);
	    	i.putExtra("key", "pause");
	    	//i.putExtra("pos", id);
	    	
	    	startService(i);
		}
	}
	public void stopMusic(View v)
	{
		i=new Intent(this,MusicPlay2.class);
    	i.putExtra("key", "stop");
    	//i.putExtra("pos", id);
    	startService(i);		
	}
	
	public void nextMusic(View v)
	{
		next();
	}
	public void prevMusic(View v)
	{
		prev();	   
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
		public void play() {
			// TODO Auto-generated method stub
			
			i=new Intent(this,MusicPlay2.class);
			i.putExtra("key", "play");
			startService(i);
		}
		
		public void prev()
		{
			if(MusicPlay2.mp!=null)
			{
				MusicPlay2.mp.stop();
				MusicPlay2.mp=null;
			}
			if(id==0)
				id=list.length-1;
			else
				id--;
			play();
		}
		public void next()
		{
			
			if(MusicPlay2.mp!=null)
			{
				MusicPlay2.mp.stop();
				MusicPlay2.mp=null;
			}
			if(id==(list.length-1))
				id=0;
			else id++;
			play();
		}


		@Override
			protected void onResume() {
				// TODO Auto-generated method stub
				super.onResume();
				sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
			}
		@Override
			protected void onPause() {
				// TODO Auto-generated method stub
				super.onPause();
				sm.unregisterListener(this);
			}

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onSensorChanged(SensorEvent arg0) {
			// TODO Auto-generated method stub
			float value[]=arg0.values;
			float x=value[0];
			if(x!=0){
			
			MusicPlay2.mp.start();
			
			}	
			else
			{
				MusicPlay2.mp.pause();
			}
			
			
				
		}
		
	

@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//stopService(i);
	}

	
}

