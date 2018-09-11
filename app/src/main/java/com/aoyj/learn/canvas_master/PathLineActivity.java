package com.aoyj.learn.canvas_master;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by aoyuanjie on 2018/7/30.
 */

public class PathLineActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paht_draw_line);
    }

    public static void toPathLineActivity(Context mContext){
        Intent intent = new Intent(mContext,PathLineActivity.class);
        mContext.startActivity(intent);

    }
}
