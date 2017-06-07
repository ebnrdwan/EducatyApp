package com.asi.educatyapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;

import View.Activities.Home;

/**
 * Created by ASI on 10/21/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
      //  Toast.makeText(getApplicationContext(),remoteMessage.getNotification().getBody(),Toast.LENGTH_LONG).show();
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Intent intent = new Intent(this,Home.class);
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        String title;
        if (remoteMessage.getNotification().getTitle()==null)
        {
            title="SuezApp";
        }
        else {
            title=remoteMessage.getNotification().getTitle();
        }
        intent.putExtra("title",title);
        intent.putExtra("message",remoteMessage.getNotification().getBody());

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(getNotificationIcon()).setTicker(title).setWhen(0)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle("SuezApp")
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(title))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()))
                .setPriority(Notification.PRIORITY_MAX)
                .setSound(defaultSoundUri)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
        Calendar c = Calendar.getInstance();






    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }



}
