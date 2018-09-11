package com.aoyj.learn.canvas_master;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by drizzt on 2018/8/28.
 */

public class PathNextContourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_next_contour);
    }

    public static void toPathNextContourActivity(Context mContext){
        Intent intent = new Intent(mContext,PathNextContourActivity.class);
        mContext.startActivity(intent);
    }
}
