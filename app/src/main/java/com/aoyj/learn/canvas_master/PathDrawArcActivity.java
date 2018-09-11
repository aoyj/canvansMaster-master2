package com.aoyj.learn.canvas_master;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by aoyuanjie on 2018/7/31.
 */

public class PathDrawArcActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_draw_arc);
    }

    public static void toPathDrawArcActivity(Context mContext){
        Intent intent = new Intent(mContext,PathDrawArcActivity.class);
        mContext.startActivity(intent);
    }
}
