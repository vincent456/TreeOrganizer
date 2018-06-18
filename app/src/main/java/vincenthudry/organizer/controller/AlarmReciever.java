package vincenthudry.organizer.controller;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import vincenthudry.organizer.R;

public class AlarmReciever extends BroadcastReceiver {

    private Context context;

    public static String NOTIFICATION_CHANNEL_ID = "notification channel id";

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;

        Toast.makeText(context,"alarm reciever triggered",Toast.LENGTH_SHORT).show();

        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(context,uri);
        ringtone.play();

        createNotificationChannel();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Notification title")
                .setContentText("Notification content")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification=builder.build();
        notificationManagerCompat.notify(1,notification);
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager=context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
