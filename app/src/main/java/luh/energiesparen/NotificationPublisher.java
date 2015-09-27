package luh.energiesparen;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;


public class NotificationPublisher extends BroadcastReceiver {
    private NotificationManager mNotificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        Intent in = new Intent(context, VerbrauchActivity.class);

        mBuilder.setSmallIcon(R.drawable.ic_strom);
        mBuilder.setContentTitle("EnergieSparen");
        mBuilder.setContentText("Zählerstände ablesen und Verbrauch bestimmen");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(VerbrauchActivity.class);
        stackBuilder.addNextIntent(in);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pendingIntent).setAutoCancel(true);

        mNotificationManager.notify(0, mBuilder.build());
    }
}