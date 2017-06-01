package com.example.radog.patm_cine_mapas;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.example.radog.patm_cine_mapas.Activities.Login;

/**
 * Created by radog on 22/05/2017.
 */

public class LoginService extends Service {

    final class MyThread implements Runnable {
        int service_id;

        public MyThread(int service_id) {
            this.service_id = service_id;
        }

        /**
         * NEW THREAD
         */
        @Override
        public void run() {
            int i = 0;
            synchronized (this) {
                while (i < 10) {
                    try {
                        //wait(1000); //10 seconds
                        wait(180000); //1800 seg = 30 min
                        i++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf(service_id);
                Intent intent = new Intent(getApplicationContext(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Session started", Toast.LENGTH_SHORT).show();
        Thread thread = new Thread(new MyThread(startId));
        thread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Session finished", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
