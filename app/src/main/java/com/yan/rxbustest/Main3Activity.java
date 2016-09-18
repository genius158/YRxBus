package com.yan.rxbustest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.yan.rxbus.EventThread;
import com.yan.rxbus.RxBus;
import com.yan.rxbus.Subscribe;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RxBus.getInstance().register(this);
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void show(Float str) {
            Toast.makeText(this, str+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unRegister(this);
    }
}
