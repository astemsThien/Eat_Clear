package vn.coffee.eatclean.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import vn.coffee.eatclean.R;

public class NotificationWater extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyLenumbit")
                .setSmallIcon(R.drawable.ic_baseline_add_alert_24)
                .setContentTitle(context.getString(R.string.Lemubit))
                .setContentText(context.getString(R.string.Please))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200,builder.build());
    }
}
