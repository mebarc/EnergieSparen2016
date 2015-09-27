package luh.energiesparen;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;

/*public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);


    }
}*/

public class NotificationPublisher extends BroadcastReceiver {
    private NotificationManager mNotificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        Intent in = new Intent(context, VerbrauchActivity.class);
/*        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, in, 0);
        mBuilder.setContentIntent(pendingIntent).setAutoCancel(true);*/

        mBuilder.setSmallIcon(R.drawable.ic_strom);
        mBuilder.setContentTitle("Notification Title");
        mBuilder.setContentText("Notification text");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(VerbrauchActivity.class);
        stackBuilder.addNextIntent(in);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pendingIntent).setAutoCancel(true);

        mNotificationManager.notify(0, mBuilder.build());
    }
}