package com.yan.rxbustest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yan.rxbus.EventThread;
import com.yan.rxbus.RxBus;
import com.yan.rxbus.Subscribe;

import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Button button;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.getInstance().post(new String("test"));
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                RxBus.getInstance().post(new Integer((int) (Math.random() * 255)));
                return true;
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.getInstance().postSticky(new Double(323));
                final Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                view.postDelayed(new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                }, 3000);
            }
        });

        RxBus.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unRegister(this);

        ((App) getApplication()).getRefWatcher().watch(this);
        Log.i("getRefWatcher", "getRefWatchergetRefWatcher");
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void show(String str) {
        if (str.equals("test"))
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


}
