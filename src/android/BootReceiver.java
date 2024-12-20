package org.apache.cordova.ultimate.health;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import org.apache.cordova.ultimate.health.util.API26Wrapper;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, final Intent intent) {
		SharedPreferences prefs = context.getSharedPreferences("pedometer", Context.MODE_PRIVATE);
		Log.i("STEPPER", "BootReceiver.onReceive enabled:" + prefs.getBoolean("enabled", false) + ", intent_action="
				+ intent.getAction());
		if (!prefs.getBoolean("enabled", false)) {
			return;
		}
		if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
				API26Wrapper.startForegroundService(context, new Intent(context, SensorListener.class));
			} else {
				context.startService(new Intent(context, SensorListener.class));
			}
		}
	}
}
