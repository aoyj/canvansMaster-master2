package com.aoyj.learn.canvas_master;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.aoyj.learn.canvas_master.widget.DrawTxtActivity;
import com.aoyj.learn.canvas_master.widget.TestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.base_graph_btn)
    Button baseGraphBtn;
    @BindView(R.id.draw_txt_btn)
    Button drawTxtBtn;
    @BindView(R.id.canvas_operation_btn)
    Button canvasOperationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.base_graph_btn, R.id.draw_txt_btn, R.id.canvas_operation_btn,R.id.test_btn,R.id.path_btn,R.id.paint_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_graph_btn:
                BaseGraphActivity.toBaseGraphActivity(this);
                break;
            case R.id.draw_txt_btn:
                DrawTxtActivity.toDrawTxtActivity(this);
                break;
            case R.id.canvas_operation_btn:
                CanvasOperationActivity.toCanvasOperationActivity(this);
                break;
            case R.id.test_btn:
                TestActivity.toTestActivity(this);
                break;
            case R.id.path_btn:
                PathActivity.toPathActivity(this);
                break;
            case  R.id.paint_btn:
                PaintCapActivity.toPaintCupActivity(this);
                break;
        }
    }


}
