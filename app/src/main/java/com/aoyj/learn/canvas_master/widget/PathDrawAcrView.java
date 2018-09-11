package com.aoyj.learn.canvas_master.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by aoyuanjie on 2018/7/30.
 */

public class PathDrawAcrView extends View {
    private static final int DEFAULT_SIZE = 200;

    private Paint mPaint,mRedPaint;
    private Path path;

    private Paint.Cap mCap;

    private int mWidth,mHeight;

    public PathDrawAcrView(Context context) {
        this(context,null);
    }

    public PathDrawAcrView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathDrawAcrView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mCap =  Paint.Cap.values()[1];
        mPaint.setStrokeCap(mCap);

        mRedPaint = new Paint();
        mRedPaint.setAntiAlias(true);
        mRedPaint.setColor(Color.RED);
        mRedPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);

        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        canvas.translate(mWidth/2,mHeight/2);
        drawCoordinateSystem(canvas);
        drawAcr(canvas);
    }

    private void drawCoordinateSystem(Canvas canvas) {
        //画X轴
        path.moveTo(-mWidth/2,0);
        path.lineTo(mWidth/2,0);
        path.lineTo(mWidth/2 -15,-15);
        path.moveTo(mWidth/2,0);
        path.lineTo(mWidth/2 -15,15);

        //画Y轴
        path.moveTo(0,-mHeight/2);
        path.lineTo(0,mHeight/2);
        path.lineTo(15,mHeight/2 - 15);
        path.moveTo(0,mHeight/2);
        path.lineTo(-15,mHeight/2 -15);
        //canvas.drawPath(path,mRedPaint);
    }

    private void drawAcr(Canvas canvas) {
     //   path.reset();
        RectF rectF = new RectF();
        rectF.left = 0;
        rectF.top = 0;
        rectF.right = DEFAULT_SIZE;
        rectF.bottom = DEFAULT_SIZE;
        int startAngle = 0;
        int sweepAngle = 120;
        path.addArc(rectF,startAngle,sweepAngle);
        canvas.drawPath(path,mPaint);
    }
}
