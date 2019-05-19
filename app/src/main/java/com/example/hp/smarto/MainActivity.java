package com.example.hp.smarto;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

  //  Button button;
    ToggleButton toggleButton,toggleButton2,toggleButton3,toggleButton4;
    TextView waterlevel,flametext,gastext,irtext;
    DatabaseReference dref;
    DatabaseReference dref1,dref2,dref3,dref4,drefFlame,drefGas,drefir;
    int flag =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waterlevel = findViewById(R.id.txt_view);
        //button = findViewById(R.id.btn1);
        toggleButton = findViewById(R.id.tg);
        toggleButton2 = findViewById(R.id.tg2);
        toggleButton3 = findViewById(R.id.tg3);
        toggleButton4 = findViewById(R.id.tg4);
        flametext = findViewById(R.id.flame);
        gastext = findViewById(R.id.gas);
        irtext = findViewById(R.id.ir);
        dref = FirebaseDatabase.getInstance().getReference("waterlevel");
        dref1 = FirebaseDatabase.getInstance().getReference("relay1");
        dref2 = FirebaseDatabase.getInstance().getReference("relay2");
        dref3 = FirebaseDatabase.getInstance().getReference("relay3");
        dref4 = FirebaseDatabase.getInstance().getReference("relay4");
        drefFlame = FirebaseDatabase.getInstance().getReference("flame");
        drefGas = FirebaseDatabase.getInstance().getReference("gas");
        drefir = FirebaseDatabase.getInstance().getReference("ir");


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    dref1.setValue("ON");

                } else
                    dref1.setValue("OFF");
            }
        });

        toggleButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    dref2.setValue("ON");
                else
                    dref2.setValue("OFF");
            }
        });

        toggleButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) dref3.setValue("ON");
                else dref3.setValue("OFF");
            }
        });

        toggleButton4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) dref4.setValue("ON");
                else dref4.setValue("OFF");
            }
        });



        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String value=dataSnapshot.getValue(String.class);
                Float value = dataSnapshot.getValue(Float.class);
                //String waterlvl = value.toString();
                Log.d("file", "Value is: " + value);


                if (value > 100)
                    waterlevel.setText("Water Level is: " + 0);
            /*else if(value<4)
                waterlevel.setText("Water Level is: "+100);   */
                else
                    waterlevel.setText("Water Level is: " + (100 - value));

                if(value<10 && flag==0){
                    Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    final int not_nu=4;
                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(MainActivity.this)
                                    .setSmallIcon(R.drawable.a)
                                    .setContentTitle("SMARTO")
                                    .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                                    .setSound(soundUri)
                                    .setContentText("Water tank is full");

                    Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(contentIntent);

                    // Add as notification
                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(not_nu, builder.build());
                    flag =1;

                }
                if(value>10){ flag=0;}
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("file", "failed", databaseError.toException());

            }
        });

        drefFlame.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String flameValue = dataSnapshot.getValue(String.class);
                flametext.setText(flameValue);
                if(flameValue.equals("Flame Detected")) {
                        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    final int not_nu=1;
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(MainActivity.this)
                                        .setSmallIcon(R.drawable.a)
                                        .setContentTitle("SMARTO")
                                        .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                                        .setSound(soundUri)
                                        .setContentText(flameValue);

                        Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(contentIntent);

                        // Add as notification
                        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify(not_nu, builder.build());



                }

                else{


                }
            };


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        drefGas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String gasValue = dataSnapshot.getValue(String.class);
                gastext.setText(gasValue);
                if(gasValue.equals("Gas Detected")) {
                    final int not_nu=2;
                    Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(MainActivity.this)
                                    .setSmallIcon(R.drawable.a)
                                    .setContentTitle("SMARTO")
                                    .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                                    .setSound(soundUri)
                                    .setContentText(gasValue);

                    Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(contentIntent);

                    // Add as notification
                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(not_nu, builder.build());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        drefir.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String irValue = dataSnapshot.getValue(String.class);
                irtext.setText(irValue);

                if(irValue.equals("Intruder Detected")) {
                    final int not_nu=3;
                    Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(MainActivity.this)
                                    .setSmallIcon(R.drawable.a)
                                    .setContentTitle("SMARTO")
                                    .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                                    .setSound(soundUri)
                                    .setContentText(irValue);

                    Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(contentIntent);

                    // Add as notification
                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(3, builder.build());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    public int generateRandom(){
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    };
}

