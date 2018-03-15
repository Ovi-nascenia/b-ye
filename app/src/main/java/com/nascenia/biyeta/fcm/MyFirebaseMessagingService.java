package com.nascenia.biyeta.fcm;

/**
 * Created by akash on 5/4/17.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.InboxSingleChat;
import com.nascenia.biyeta.activity.Login;
import com.nascenia.biyeta.activity.NewUserProfileActivity;
import com.nascenia.biyeta.activity.SplashScreen;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static int notificaationNumber = 0;
    private Map<String,String> notificationData = null;
    private String currentLoginUserId;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String token= FirebaseInstanceId.getInstance().getToken();
        System.out.println("MainActivity.onCreate: " + token);

        SharePref sharePref = new SharePref(getApplicationContext());

        System.out.println(remoteMessage.getData());

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        currentLoginUserId = sharePref.get_data("user_id");

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
               // scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }

        // Check if message contains a notification payload.

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }

        else if(remoteMessage.getData()!=null)
        {
            notificationData = remoteMessage.getData();
            sendNotificationWithSenderAndReceiver(notificationData);
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */


    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }


    ///for testing from firebase


    private void sendNotification(String messageBody) {
        notificaationNumber++;
        if(messageBody.equals("hello"))
        {
            Intent intent = new Intent(this, SplashScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle("Firebase Push Notification")
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(notificaationNumber, notificationBuilder.build());
        }

        else if(messageBody.equals("hi"))
        {
            Intent intent = new Intent(this, SplashScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle("Firebase Push Notification")
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(notificaationNumber, notificationBuilder.build());
        }


    }



    ////for main biyeta project


    private void sendNotificationWithSenderAndReceiver(Map<String,String> notificationDataAll) {
        notificaationNumber++;
        String to= null,from = null,text = null,type = null,senderName = null,senderProfileId=null;
        try {
            JSONObject object = new JSONObject(notificationDataAll.toString());

            Log.e("Json Object",object.toString());
            JSONObject data = object.getJSONObject("data");
            to = (String) data.get("receiver");
            from = (String) data.get("sender");
            text = (String) data.get("text");
            type = (String) data.get("type");
            senderName = (String) data.get("senderName");
            senderProfileId = (String) data.get("sender_profile_id");

            Log.e("Json Object",from);
            Log.e("Json Object",to);
            Log.e("Json Object",text);
            Log.e("Json Object",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(type.equals("0")&&Integer.parseInt(to)==Integer.parseInt(currentLoginUserId))
        {
            Intent intent = new Intent(this, NewUserProfileActivity.class);
            intent.putExtra("id", senderProfileId);
//            intent.putExtra("user_name", );
            intent.putExtra("PROFILE_EDIT_OPTION", false);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            showPushNotification(intent,text,"হাসি");
        }

        else if(type.equals("1")&&Integer.parseInt(to)==Integer.parseInt(currentLoginUserId))
        {
            Intent intent = new Intent(this, NewUserProfileActivity.class);
            intent.putExtra("id", senderProfileId);
//            intent.putExtra("user_name", );
            intent.putExtra("PROFILE_EDIT_OPTION", false);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            showPushNotification(intent,text,"বায়োডাটা");
        }
        else if(type.equals("2")&&Integer.parseInt(to)==Integer.parseInt(currentLoginUserId))
        {
            Intent intent = new Intent(this, NewUserProfileActivity.class);
            intent.putExtra("id", senderProfileId);
//            intent.putExtra("user_name", );
            intent.putExtra("PROFILE_EDIT_OPTION", false);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            showPushNotification(intent,text,"যোগাযোগ");
        }
        else if(type.equals("3")&&Integer.parseInt(to)==Integer.parseInt(currentLoginUserId))
        {
            Intent intent = new Intent(this, NewUserProfileActivity.class);
            intent.putExtra("id", senderProfileId);
//            intent.putExtra("user_name", );
            intent.putExtra("PROFILE_EDIT_OPTION", false);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            showPushNotification(intent,text,"ফেভারিট");

        }

        else if(type.equals("4")&&Integer.parseInt(to)==Integer.parseInt(currentLoginUserId))
        {


            Intent in=new Intent(this, InboxSingleChat.class);
            Bundle bundle=new Bundle();
            bundle.putInt("sender_id",Integer.parseInt(senderProfileId.toString()));
            bundle.putInt("receiver_id",Integer.parseInt(to.toString()));
            bundle.putInt("current_user",Integer.parseInt(to.toString()));
            bundle.putString("userName",senderName.toString());
            //bundle.putInt("numberOfMessage",item.getMessage());
            //Log.e("come",item.getMessage().getUserId() +"  "+item.getMessage().getReceiver());
            in.putExtras(bundle);
           // this.startActivity(in);


            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            showPushNotification(in,text,"মেসেজ");
        }

        else if(type.equals("5")&&Integer.parseInt(to)==Integer.parseInt(currentLoginUserId))
        {
            Intent intent = new Intent(this, NewUserProfileActivity.class);
            intent.putExtra("id", senderProfileId);
//            intent.putExtra("user_name", );
            intent.putExtra("PROFILE_EDIT_OPTION", false);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            showPushNotification(intent,text,"বায়োডাটা");
        }
        else if(type.equals("6")&&Integer.parseInt(to)==Integer.parseInt(currentLoginUserId))
        {
            Intent intent = new Intent(this, NewUserProfileActivity.class);
            intent.putExtra("id", senderProfileId);
//            intent.putExtra("user_name", );
            intent.putExtra("PROFILE_EDIT_OPTION", false);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            showPushNotification(intent,text,"যোগাযোগ");
        }else if(type.equals("100")&&Integer.parseInt(to)==Integer.parseInt(currentLoginUserId))
        {
            Utils.ShowAlert(getApplicationContext(), text);
        }
    }

    void showPushNotification(Intent intent, String text, String title)
    {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.push_notification_transparent)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setContentText(text)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


       // NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.push_notification_transparent);
        } else {
            notificationBuilder.setSmallIcon(R.drawable.notification_icon);
        }



        notificationManager.notify(notificaationNumber, notificationBuilder.build());
    }
}
