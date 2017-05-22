package com.example.radog.patm_cine_mapas.Firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.radog.patm_cine_mapas.Activities.NotificationActivity;
import com.example.radog.patm_cine_mapas.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by radog on 21/05/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = "NOTICIAS";

    /**
     * Aquí llegarán los mensajes o notificaciones que nos manden
     *
     * @param remoteMessage
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //obtener el código de quien lo origina
        String from = remoteMessage.getFrom();
        Log.d(TAG, "Mensaje recibido de: " + from);

        if (remoteMessage.getNotification() != null || remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Notificación: " + remoteMessage.getNotification().getTitle() + " " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Data: " + remoteMessage.getData());
            mostrarNotificacion(
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody(),
                    remoteMessage.getData().get("message"));
        }
    }

    private void mostrarNotificacion(String title, String text, String msg) {
        //se nececita un pending intent que éste a su vez necesita un intent
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.putExtra("title", title);
        intent.putExtra("text", text);
        intent.putExtra("message", msg);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        //uri del sonido
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher) //icono de las notificaiones
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true) //desaparezca cuando lo presionemos
                .setSound(soundUri) //sonido que se usará si está activo
                .setContentIntent(pendingIntent); //lo que se hará cuando se presione sobre el

        //notification manager se encarga de la carcaza que acabamos de crear
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notiBuilder.build());
    }
}
