package com.aoyj.learn.canvas_master;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by aoyuanjie on 2018/7/23.
 */

public class BaseGraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_graph);
    }

    public static void toBaseGraphActivity(Context mContext){

        Intent intent = new Intent(mContext,BaseGraphActivity.class);
        mContext.startActivity(intent);
    }
}
