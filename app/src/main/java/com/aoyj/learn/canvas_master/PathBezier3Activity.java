package com.aoyj.learn.canvas_master;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by aoyuanjie on 2018/7/31.
 */

public class PathBezier3Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier3);
    }

    public static void toBezier3Activity(Context mContext){
        Intent intent = new Intent(mContext,PathBezier3Activity.class);
        mContext.startActivity(intent);
    }
}

