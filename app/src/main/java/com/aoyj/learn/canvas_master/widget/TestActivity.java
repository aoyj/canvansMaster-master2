package com.aoyj.learn.canvas_master.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.aoyj.learn.canvas_master.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aoyuanjie on 2018/7/27.
 */

public class TestActivity extends AppCompatActivity {
    @BindView(R.id.progress)
    TestView progress;
    @BindView(R.id.rotate_img)
    ImageView rotateImg;
    Handler mHandler;
    int progressValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        startRotate();
        initProgress();
    }

    private void initProgress() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressValue = progressValue + 1;
                if(progressValue >= 100){
                    progressValue = 0;
                }
                progress.setCurrentProgress(progressValue);
                mHandler.sendEmptyMessageDelayed(0,1000);
            }
        };
        mHandler.sendEmptyMessageDelayed(0,1000);
    }

    private void startRotate() {
        Animation rotateAnimation = new RotateAnimation(0,359,Animation.RELATIVE_TO_SELF,0.5F,Animation.RELATIVE_TO_SELF,0.5F);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setRepeatMode(Animation.INFINITE);
        rotateImg.startAnimation(rotateAnimation);
    }

    public static void toTestActivity(Context mContext) {
        Intent intent = new Intent(mContext, TestActivity.class);
        mContext.startActivity(intent);

    }
}
