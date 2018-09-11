package com.aoyj.learn.canvas_master.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by drizzt on 2018/8/28.
 */

public class PathNextContourView extends View {
    private String TAG = PathNextContourView.class.getSimpleName();

    private Paint paint;
    private Path path;
    private PathMeasure pathMeasure;
    private float mWidth,mHeight;

    public PathNextContourView(Context context) {
        this(context,null);
    }

    public PathNextContourView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathNextContourView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setStrokeWidth(6);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
        pathMeasure = new PathMeasure();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        path.reset();
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        canvas.translate(mWidth/2,mHeight/2);

        /* path.lineTo(300,0);
        path.lineTo(300,300);


        path.moveTo(0,50);
        path.lineTo(100,50);
        path.lineTo(100,150);

        canvas.drawPath(path,paint);


        pathMeasure.setPath(path,false);
        float outLength = pathMeasure.getLength();
        boolean nextContourFlag =  pathMeasure.nextContour();
        float innerLength = pathMeasure.getLength();
        Log.i(TAG,"path out contour length is:" +   outLength);
        Log.i(TAG,"path inner contour length is:" + innerLength);*/

        path.addCircle(0,0,300, Path.Direction.CW);
        paint.setColor(Color.BLUE);
        canvas.drawPath(path,paint);
        pathMeasure.setPath(path,false);
        float cricleLength = pathMeasure.getLength();
        float[] position = new float[2];
        float[] tans = new float[2];
        pathMeasure.getPosTan(cricleLength * progressValue,position,tans);
        float degree = (float) (Math.atan2(tans[1],tans[0]) * 180 / Math.PI);
        canvas.translate(position[0],position[1]);
        canvas.rotate(degree);
        paint.setColor(Color.RED);
        canvas.drawLine(-300,0,300,0,paint);
        startAnimator();
    }


    private ValueAnimator valueAnimator;
    private boolean isStartAnimator = false;
    private float progressValue = 0f;

    private void startAnimator(){
        if(!isStartAnimator){
            isStartAnimator = true;
            if(valueAnimator == null){
                valueAnimator = ValueAnimator.ofFloat(0,1f);
                valueAnimator.setDuration(2000l);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
                valueAnimator.addUpdateListener(v ->{
                    progressValue = (float) v.getAnimatedValue();
                    postInvalidate();
                });
            }
            valueAnimator.start();
        }
    }
}
