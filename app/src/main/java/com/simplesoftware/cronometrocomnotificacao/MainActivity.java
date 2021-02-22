package com.simplesoftware.cronometrocomnotificacao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;

import static com.simplesoftware.cronometrocomnotificacao.App.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {
    private Chronometer cronometro;
    private boolean running;
    private long pauseOffset;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);

        cronometro = findViewById(R.id.cronometro);

        cronometro.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //Se o tempo for entre 30 e 31 segundos, lançar notificação.
                if ((SystemClock.elapsedRealtime() - cronometro.getBase()) >= 30000 && (SystemClock.elapsedRealtime() - cronometro.getBase()) <= 31000) {
                    Notification notify = new NotificationCompat.Builder(MainActivity.this, CHANNEL_1_ID)
                            .setSmallIcon(R.drawable.ic_water)
                            .setContentTitle("COLOQUE AQUI O TITULO DA NOTIFICAÇÃO")
                            .setContentText("COLOQUE AQUI O TEXTO DA NOTIFICAÇÃO")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .build();

                    notificationManager.notify(1, notify);
                }

            }
        });
    }

    public void startCronometro(View v) {
        if (!running) {
            cronometro.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            cronometro.start();
            running = true;
        }
    }

    public void pauseCronometro(View v) {
        if (running) {
            cronometro.stop();
            pauseOffset = SystemClock.elapsedRealtime() - cronometro.getBase();
            running = false;
        }
    }

    public void resetCronometro(View v) {
        cronometro.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

}