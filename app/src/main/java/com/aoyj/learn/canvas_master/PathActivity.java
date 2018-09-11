package com.aoyj.learn.canvas_master;

import android.content.Context;
import android.content.Intent;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aoyuanjie on 2018/7/30.
 */

public class PathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        ButterKnife.bind(this);
    }


    public static void toPathActivity(Context mContext) {
        Intent intent = new Intent(mContext, PathActivity.class);
        mContext.startActivity(intent);
    }

    @OnClick({R.id.line_btn, R.id.base_shaper_btn,R.id.acr_btn,R.id.bezier_2_btn,R.id.bezier_3_btn,R.id.other_btn,
            R.id.boolean_operation_btn,R.id.path_measure_btn,R.id.path_measure2_btn,R.id.path_next_contour_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.line_btn:
                PathLineActivity.toPathLineActivity(this);
                break;
            case R.id.base_shaper_btn:
                PathDrawShaperActivity.toPathDrawShaperActivity(this);
                break;
            case R.id.acr_btn:
               PathDrawArcActivity.toPathDrawArcActivity(this);
                break;
            case R.id.bezier_2_btn:
                PathBezier2Activity.toBezier2Activity(this);
                break;
            case R.id.bezier_3_btn:
                PathBezier3Activity.toBezier3Activity(this);
                break;
            case R.id.other_btn:
                PathOtherActivity.toPahtOtherActivity(this);
                break;
            case  R.id.boolean_operation_btn:
                PathBooleanOperatorActivity.toPathBooleanOperationActivity(this);
                break;
            case R.id.path_measure_btn:
                PathMeasureActivity.toPathMeasureActivity(this);
                break;
            case R.id.path_measure2_btn:
                PathMeasure2Activity.toPathMeasure2Activity(this);
                break;
            case R.id.path_next_contour_btn:
                PathNextContourActivity.toPathNextContourActivity(this);
                break;
        }
    }
}
