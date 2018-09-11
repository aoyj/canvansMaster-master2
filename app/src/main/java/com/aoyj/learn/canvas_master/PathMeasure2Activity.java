package com.aoyj.learn.canvas_master;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by aoyuanjie on 2018/8/3.
 */

public class PathMeasure2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_measure_2);
    }

    public static void toPathMeasure2Activity(Context mContext){
        Intent intent = new Intent(mContext,PathMeasure2Activity.class);
        mContext.startActivity(intent);
    }
}
