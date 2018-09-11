package com.aoyj.learn.canvas_master.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * pathMeasure
 * Created by aoyuanjie on 2018/8/2.
 */

public class PathMeasureView extends View {
    private static int DEFAULT_WIDTH = 600;
    private static int DEFAULT_HEIGHT = 800;
    private static int DEFAULT_RADIUS = 50;

    private Paint mGrayPaint,mYellowPaint;
    private Path mPath1,mPath2,mPath3;

    private int mWidth,mHeight;

    public void setCurrentProgress(float currentProgress) {
        this.currentProgress = currentProgress;
        invalidate();
        postInvalidate();
    }

    private float currentProgress = 0f;
    private PathMeasure mPathMeasure;

    public PathMeasureView(Context context) {
        this(context,null);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mGrayPaint = new Paint();
        mGrayPaint.setColor(Color.GRAY);
        mGrayPaint.setStyle(Paint.Style.STROKE);
        mGrayPaint.setAntiAlias(true);
        mGrayPaint.setStrokeWidth(20);
        mYellowPaint = new Paint();
        mYellowPaint.setColor(Color.YELLOW);
        mYellowPaint.setAntiAlias(true);
        mYellowPaint.setStyle(Paint.Style.STROKE);
        mYellowPaint.setStrokeWidth(21);
        mYellowPaint.setStrokeCap(Paint.Cap.ROUND);

        mPath2 = new Path();
        mPath1 = new Path();
        mPath3 = new Path();
        mPathMeasure = new PathMeasure();
    }

    private void initWidthAndHeight(int width, int height){
        mWidth = width;
        mHeight = height;

        mPathMeasure = new PathMeasure();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initWidthAndHeight(getWidth(),getMeasuredHeight());
        mPath2.reset();
        mPath1.reset();
        mPath3.reset();
        canvas.translate(mWidth/2,mHeight/2);
        RectF rectF = new RectF();
        rectF.left = -DEFAULT_WIDTH/2;
        rectF.top = -DEFAULT_HEIGHT/2;
        rectF.right = rectF.left + DEFAULT_WIDTH;
        rectF.bottom = rectF.top + DEFAULT_HEIGHT;
        mPath1.addRoundRect(rectF, DEFAULT_RADIUS,DEFAULT_RADIUS,Path.Direction.CCW);
        mPathMeasure.setPath(mPath1,false);
        float path1Length = mPathMeasure.getLength();
        float currentDrawLength = path1Length * currentProgress;
        mPathMeasure.getSegment(0,currentDrawLength,mPath2,true);
        mPathMeasure.getSegment(path1Length *0.990f,path1Length,mPath3,true);
        canvas.scale(1,1,0,0);
       // canvas.drawPath(mPath1,mGrayPaint);
        canvas.rotate(0,0,0);
        canvas.drawPath(mPath2,mYellowPaint);
        canvas.drawPath(mPath3,mYellowPaint);
    }
}
