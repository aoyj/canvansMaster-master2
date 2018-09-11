package com.aoyj.learn.canvas_master;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by aoyuanjie on 2018/8/2.
 */

public class PathBooleanOperatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_boolean_operator);
    }

    public static void toPathBooleanOperationActivity(Context context){
        Intent intent = new Intent(context,PathBooleanOperatorActivity.class);
        context.startActivity(intent);
    }
}
