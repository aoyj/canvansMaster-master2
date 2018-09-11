package com.aoyj.learn.canvas_master.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by aoyuanjie on 2018/7/31.
 */

public class PathBezierView extends View {
    private Paint mPaint,mLinePaint;
    private Path mPath;

    private PointF startPoint,endPoint,controlPoint;


    private int mWidth,mHeight;
    public PathBezierView(Context context) {
        this(context,null);
    }

    public PathBezierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mLinePaint = new Paint();
        mPaint.setColor(Color.RED);
        mLinePaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);

        mPath = new Path();

        startPoint = new PointF(-200f,0f);
        endPoint = new PointF(200f,0f);
        controlPoint = new PointF(0,200);
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
        canvas.translate(mWidth / 2, mHeight / 2);
        mPath.reset();
        drawLine(canvas);
        mPath.moveTo(startPoint.x,startPoint.y);
        mPath.quadTo(controlPoint.x,controlPoint.y,endPoint.x,endPoint.y);
        canvas.drawPath(mPath,mPaint);
        drawDec(canvas,startPoint.x,startPoint.y,"起点");
        drawDec(canvas,controlPoint.x,controlPoint.y,"控制点");
        drawDec(canvas,endPoint.x,endPoint.y,"终点");
    }

    private void drawDec(Canvas canvas,float x,float y,String txt){
        mPaint.setStrokeWidth(15);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(x,y,mPaint);
        mPaint.setTextSize(30f);
        mPaint.setStrokeWidth(2f);
        float drawTxtX = x - 100;
        float drawTxtY = y + Tools.getBaseLine(mPaint);
        canvas.drawText(txt,drawTxtX,drawTxtY,mPaint);
    }

    private void drawLine(Canvas canvas) {
        canvas.drawLine(startPoint.x,startPoint.y,controlPoint.x,controlPoint.y,mLinePaint);
        canvas.drawLine(controlPoint.x,controlPoint.y,endPoint.x,endPoint.y,mLinePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controlPoint.x = event.getX() - mWidth/2;
        controlPoint.y = event.getY() - mHeight/2;
        postInvalidate();
        return true;
    }
}
