package com.lau.stateviewdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.lau.StateView;

public class MainActivity extends AppCompatActivity {

    private StateView stateView;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stateView = findViewById(R.id.stateView);
        stateView.setRetryListener(new StateView.setOnRetry() {
            @Override
            public void onRetry(View retryView) {
                retryView.findViewById(R.id.base_retry).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 模拟重试后加载成功
                        stateView.showView(StateView.State.LOADING);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                stateView.showView(StateView.State.CONTENT);
                            }
                        }, 2000);
                    }
                });
            }
        });
        // 模拟加载失败
        stateView.showView(StateView.State.LOADING);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stateView.showView(StateView.State.RETRY);
            }
        }, 2000);
    }
}
