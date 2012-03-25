package org.duncavage.volumemiser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Booter extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(PreferenceHelper.isStartOnBootEnabled(context)) {
			context.startService(new Intent(context, VolumeMiserService.class));
		}
	}

}
