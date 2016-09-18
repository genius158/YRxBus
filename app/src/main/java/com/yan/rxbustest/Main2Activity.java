package com.yan.rxbustest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.yan.rxbus.EventThread;
import com.yan.rxbus.RxBus;
import com.yan.rxbus.Subscribe;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        RxBus.getInstance().register(this);

        RxBus.getInstance().postSticky(new Float(2323));
        Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
        startActivity(intent);
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void show(Double str) {
        Toast.makeText(this, str + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unRegister(this);
    }
}
