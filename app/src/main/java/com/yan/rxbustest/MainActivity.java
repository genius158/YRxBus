package com.yan.rxbustest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.yan.rxbus.EventThread;
import com.yan.rxbus.RxBus;
import com.yan.rxbus.RxHelper;
import com.yan.rxbus.Subscribe;

import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Button button1;
    Button button2;
    Button button3;
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button1.setOnLongClickListener(view -> {
            RxBus.getInstance().post(new Integer((int) (Math.random() * 255)));
            return true;
        });


        button1.setOnClickListener(view -> {
                    for (int i = 0; i < 2000; i++) {
                        RxBus.getInstance().post((Action.Action1) () -> "Action1");
                        RxBus.getInstance().post((Action.Action2) () -> "Action2");
                        RxBus.getInstance().post((Action.Action2) () -> "Action3");
                    }

                }
        );

        button2.setOnClickListener(view -> {
                    RxBus.getInstance().post((Action.Action2) () -> "Action2");
                    RxBus.getInstance().post((Action.Action2) () -> "Action3");
                }
        );

        button3.setOnClickListener(view ->
                RxBus.getInstance().post((Action.Action2) () -> "Action3")
        );


        button4.setOnClickListener(view -> {
            RxBus.getInstance().postSticky(new Double(323));
            final Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            view.postDelayed(new TimerTask() {
                @Override
                public void run() {
                    startActivity(intent);
                }
            }, 3000);
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
    public void show(Action.Action1 str) {
       // Toast.makeText(this, str.getStr(), Toast.LENGTH_SHORT).show();
        Log.i(" str.getStr()", str.getStr());

    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void show(Action.Action2 str) {
     //   Toast.makeText(this, str.getStr(), Toast.LENGTH_SHORT).show();
       Log.i(" str.getStr()", str.getStr());
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void show(Action.Action3 str) {
     //   Toast.makeText(this, str.getStr(), Toast.LENGTH_SHORT).show();
        Log.i(" str.getStr()", str.getStr());

    }
}
