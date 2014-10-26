package com.artion.androiddemos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.artion.androiddemos.utils.DebugTool;

public class SensorTestActivity extends BaseActivity implements SensorEventListener {
	
	/** Called when the activity is first created. */  
    Button btn_start = null;  
    Button btn_stop = null;  
    Button btn_close = null;  
    Button btn_open = null;  
  
    // /mediaplaer  
    MediaPlayer _mediaPlayer = null; // 音乐播放管理器  
    AudioManager audioManager = null; // 声音管理器  
  
    SensorManager _sensorManager = null; // 传感器管理器  
    Sensor mProximiny = null; // 传感器实例  
  
    float f_proximiny; // 当前传感器距离 
    
    AudioTrack mAudioTrack;
    private int samplerate;  
    private int mAudioMinBufSize; 
    private InputStream din; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_sensor);
		
		initLayout();
		initListener();
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();
		
		btn_start = (Button) findViewById(R.id.btn_start);  
        
  
        btn_stop = (Button) findViewById(R.id.btn_stop);  
        
  
        btn_close = (Button) findViewById(R.id.btn_close);  
        
  
        btn_open = (Button) findViewById(R.id.btn_open);  
        
  
        _mediaPlayer = new MediaPlayer();  
  
        audioManager = (AudioManager) this  
                .getSystemService(Context.AUDIO_SERVICE);  
  
        _sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);  
        mProximiny = _sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);  
        
//        initAudioPlayer();
	}
	
	private void initAudioTrack() {
		int minBufferSize = AudioTrack.getMinBufferSize(0xac44,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT);
		System.out.println("minBufferSize = " + minBufferSize);
		mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 0xac44,
				AudioFormat.CHANNEL_CONFIGURATION_STEREO,
				AudioFormat.ENCODING_PCM_16BIT, minBufferSize * 2,
				AudioTrack.MODE_STREAM);
		mAudioTrack.setStereoVolume(1.0f, 1.0f);// 设置当前音量大小
		mAudioTrack.play();
	} 
	
	private void initAudioPlayer() {  
        // TODO Auto-generated method stub  
        // 声音文件一秒钟buffer的大小  
        mAudioMinBufSize = AudioTrack.getMinBufferSize(0xac44,  
                AudioFormat.CHANNEL_CONFIGURATION_STEREO,  
                AudioFormat.ENCODING_PCM_16BIT);  
  
        mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, // 指定在流的类型  
                // STREAM_ALARM：警告声  
                // STREAM_MUSCI：音乐声，例如music等  
                // STREAM_RING：铃声  
                // STREAM_SYSTEM：系统声音  
                // STREAM_VOCIE_CALL：电话声音  
                  
        		32000,// 设置音频数据的采样率  
                AudioFormat.CHANNEL_CONFIGURATION_STEREO,// 设置输出声道为双声道立体声  
                AudioFormat.ENCODING_PCM_16BIT,// 设置音频数据块是8位还是16位  
                2018, AudioTrack.MODE_STREAM);// 设置模式类型，在这里设置为流类型  
        // AudioTrack中有MODE_STATIC和MODE_STREAM两种分类。  
        // STREAM方式表示由用户通过write方式把数据一次一次得写到audiotrack中。  
        // 这种方式的缺点就是JAVA层和Native层不断地交换数据，效率损失较大。  
        // 而STATIC方式表示是一开始创建的时候，就把音频数据放到一个固定的buffer，然后直接传给audiotrack，  
        // 后续就不用一次次得write了。AudioTrack会自己播放这个buffer中的数据。  
        // 这种方法对于铃声等体积较小的文件比较合适。  
    }  
	
	private void playReal() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					din = new FileInputStream("/mnt/sdcard/DUOMI/down/Andy.mp3");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// 打开mp3文件，读取数据，解码等操作省略 ...
			    byte[] buffer = new byte[4096];
			    int count;
			    while(true)
			    {
			    	try {
			    	din.read(buffer);
			        // 最关键的是将解码后的数据，从缓冲区写入到AudioTrack对象中
			    	mAudioTrack.write(buffer, 0, 4096);
			    	}catch(Exception e) {
			    		
			    	}
			    	
			    }
			}
		}).start();
		
//	    // 最后别忘了关闭并释放资源
//	    mAudioTrack.stop();
//	    mAudioTrack.release();
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		btn_start.setOnClickListener(onClick); 
		btn_stop.setOnClickListener(onClick);  
		
		btn_close.setOnClickListener(onClick);  
		btn_open.setOnClickListener(onClick);  
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		// 取消注册传感器  
        _sensorManager.unregisterListener(this);  
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		// 注册传感器  
        _sensorManager.registerListener(this, mProximiny,  
                SensorManager.SENSOR_DELAY_FASTEST);  
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		 mAudioTrack.stop();  
		 mAudioTrack.release();// 关闭并释放资源  
	}

	private OnClickListener onClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {  
	        case R.id.btn_close:  
	            audioManager.setMode(AudioManager.MODE_NORMAL);  
	            break;  
	        case R.id.btn_open:  
	            audioManager.setMode(AudioManager.MODE_IN_CALL);  
	            break;  
	        case R.id.btn_start:// 音乐取自于Sd卡上的音乐  
	            playerMusic("/mnt/sdcard/DUOMI/down/Andy.mp3");  
	        	
//	        	playReal();
	            break;  
	        case R.id.btn_stop:  
	            stopPlayerMusic();  
	            break;  
	        }  
		}
	};
	
	private void playerMusic(String path) {  
        // 重置播放器  
        _mediaPlayer.reset();  
        try {  
            // 设置播放路径  
            _mediaPlayer.setDataSource(path);  
            // 准备播放  
            _mediaPlayer.prepare();  
            // 开始播放  
            _mediaPlayer.start();  
        } catch (IllegalArgumentException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IllegalStateException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
  
    private void stopPlayerMusic() {  
        // 停止播放  
        if (_mediaPlayer.isPlaying()) {  
            _mediaPlayer.reset();  
        }  
    }  
  

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
//		f_proximiny = event.values[0];  
//        DebugTool.info(tag,  
//                "-->  " + f_proximiny + "  |  " + mProximiny.getMaximumRange());  
//  
//        if (f_proximiny >= mProximiny.getMaximumRange()) {  
//            audioManager.setMode(AudioManager.MODE_NORMAL); 
//            audioManager.setSpeakerphoneOn(true);
////            playerMusic("/mnt/sdcard/DUOMI/down/Andy.mp3");  
//        } else {  
//            audioManager.setMode(AudioManager.MODE_IN_CALL);  
//            audioManager.setSpeakerphoneOn(false);
//            audioManager.setRouting(AudioManager.MODE_NORMAL, AudioManager.ROUTE_EARPIECE, AudioManager.ROUTE_ALL);
//            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
////            playerMusic("/mnt/sdcard/DUOMI/down/Andy.mp3");  
//        } 
	}

}
