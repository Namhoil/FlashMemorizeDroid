package me.timgu.flashmemorize;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;
import java.util.concurrent.Executors;

import me.timgu.flashmemorize.DatabaseHelper;
import me.timgu.flashmemorize.Alarm;

import static android.content.Intent.ACTION_BOOT_COMPLETED;
import static me.timgu.flashmemorize.AlarmReceiver.setReminderAlarms;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    final List<Alarm> alarms = DatabaseHelper.getInstance(context).getAlarms();
                    setReminderAlarms(context, alarms);
                }
            });
        }
    }
}
