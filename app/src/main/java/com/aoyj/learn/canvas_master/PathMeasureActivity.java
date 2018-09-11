package com.aoyj.learn.canvas_master;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;

import com.aoyj.learn.canvas_master.widget.PathMeasureView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.animation.ValueAnimator.REVERSE;

/**
 * Created by aoyuanjie on 2018/8/2.
 */

public class PathMeasureActivity extends AppCompatActivity {
    ValueAnimator valueAnimator;
    @BindView(R.id.progress)
    PathMeasureView progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_measure);
        ButterKnife.bind(this);
        initAnimator();
    }

    private void initAnimator() {
        valueAnimator = ValueAnimator.ofFloat(1f, 0f);
        valueAnimator.setDuration(20000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //valueAnimator.setRepeatMode(REVERSE);
        valueAnimator.reverse();
        valueAnimator.addUpdateListener(animator -> {
           float ret = (float) animator.getAnimatedValue();
            progress.setCurrentProgress(ret);
        });
        valueAnimator.start();
    }

    public static void toPathMeasureActivity(Context mContext) {
        Intent intent = new Intent(mContext, PathMeasureActivity.class);
        mContext.startActivity(intent);

    }
}
