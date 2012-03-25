package org.duncavage.volumemiser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class VolumeMiserActivity extends Activity {
	private AudioManager audio_manager;

	private SeekBar speaker_vol_slider;
	private SeekBar headset_vol_slider;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        audio_manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        if(PreferenceHelper.isEnabled(this)) {
        	startService(new Intent(this, VolumeMiserService.class));
        }
        
        final CheckedTextView enabled_view = (CheckedTextView)findViewById(R.id.enabled);
        enabled_view.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				enabled_view.toggle();
				if(!enabled_view.isChecked()) {
					stopService(new Intent(VolumeMiserActivity.this, VolumeMiserService.class));
				} else {
					startService(new Intent(VolumeMiserActivity.this, VolumeMiserService.class));
				}
				PreferenceHelper.setEnabled(VolumeMiserActivity.this, enabled_view.isChecked());
			}
        });
        
        final CheckedTextView start_on_boot_view = (CheckedTextView)findViewById(R.id.start_on_boot);
        start_on_boot_view.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				start_on_boot_view.toggle();
				PreferenceHelper.setBootOnStartup(VolumeMiserActivity.this, start_on_boot_view.isChecked());
			}
        });
        
        speaker_vol_slider = (SeekBar)findViewById(R.id.speaker_volume_bar);
        speaker_vol_slider.setMax(audio_manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        speaker_vol_slider.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
				if(!fromUser) {
					return;
				}
				PreferenceHelper.setSpeakerVolumePref(VolumeMiserActivity.this, value);
			}

			public void onStartTrackingTouch(SeekBar arg0) {
			}

			public void onStopTrackingTouch(SeekBar arg0) {
			}
        	
        });
        
        headset_vol_slider = (SeekBar)findViewById(R.id.headset_volume_bar);
        headset_vol_slider.setMax(audio_manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        headset_vol_slider.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
				if(!fromUser) {
					return;
				}
				PreferenceHelper.setHeadsetVolumePref(VolumeMiserActivity.this, value);
			}

			public void onStartTrackingTouch(SeekBar arg0) {
			}

			public void onStopTrackingTouch(SeekBar arg0) {
			}
        	
        });
        
        ((TextView)findViewById(R.id.my_credit)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)findViewById(R.id.footer)).setMovementMethod(LinkMovementMethod.getInstance());
    }
    
    @Override
    public void onResume() {
    	super.onResume();

        int speaker_vol = PreferenceHelper.getSpeakerVolume(this);
        int headset_vol = PreferenceHelper.getHeadsetVolume(this);

        speaker_vol_slider.setProgress(speaker_vol);
        headset_vol_slider.setProgress(headset_vol);
        
        ((CheckedTextView)findViewById(R.id.enabled)).setChecked(PreferenceHelper.isEnabled(this));
        ((CheckedTextView)findViewById(R.id.start_on_boot)).setChecked(PreferenceHelper.isStartOnBootEnabled(this));
    }
}