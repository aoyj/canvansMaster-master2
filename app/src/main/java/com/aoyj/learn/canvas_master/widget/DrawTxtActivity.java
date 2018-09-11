package com.aoyj.learn.canvas_master.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aoyj.learn.canvas_master.R;

/**
 * Created by aoyuanjie on 2018/7/23.
 */

public class DrawTxtActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_txt);
    }

    public static void toDrawTxtActivity(Context mContext){
        Intent intent = new Intent(mContext,DrawTxtActivity.class);
        mContext.startActivity(intent);
    }
}
