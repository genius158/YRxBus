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

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
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
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


}
