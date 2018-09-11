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

import static android.graphics.Path.Direction.CW;

/**
 * Created by aoyuanjie on 2018/7/30.
 */

public class PathDrawBaseShaper extends View {
    private static final int DEFAULT_RECT_WIDTH = 300;

    private Paint mPaint;
    private int mWidth,mHeight;
    private Path mPath;

    public PathDrawBaseShaper(Context context) {
        this(context,null);
    }

    public PathDrawBaseShaper(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathDrawBaseShaper(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        //mPaint.setStrokeWidth(3);
        mPaint.setTextSize(30);
        mPaint.setAntiAlias(true);

        mPath = new Path();
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
        addRect(canvas);
        addRoundRect(canvas);
        addCircle(canvas);
        addOval(canvas);
        canvas.drawPath(mPath,mPaint);
    }

    private void drawDecText(Canvas canvas,String dec,int centerX,int centerY){
        float txtWidth = mPaint.measureText(dec);
        int baseLineX = (int) (centerX - txtWidth/2);
        int baseLineY = (int) (centerY + Tools.getBaseLine(mPaint));
        canvas.drawText(dec,baseLineX,baseLineY,mPaint);
    }

    private void addRect(Canvas canvas) {

        RectF rect = new RectF(0,0,DEFAULT_RECT_WIDTH,DEFAULT_RECT_WIDTH);
        //顺时针画矩形
        mPath.addRect(rect, CW);
        drawDecText(canvas,"矩形",DEFAULT_RECT_WIDTH/2,DEFAULT_RECT_WIDTH/2);
    }

    private void addRoundRect(Canvas canvas) {
        RectF rectF = new RectF(0,0,-DEFAULT_RECT_WIDTH,DEFAULT_RECT_WIDTH);
        mPath.addRoundRect(rectF,15,15, CW);
        drawDecText(canvas,"圆角矩形",-DEFAULT_RECT_WIDTH/2,DEFAULT_RECT_WIDTH/2);
    }

    private void addCircle(Canvas canvas) {
        RectF rectF = new RectF(0,0,-DEFAULT_RECT_WIDTH,-DEFAULT_RECT_WIDTH);
        mPath.addCircle((rectF.left + rectF.right) / 2,(rectF.top + rectF.bottom) / 2,(rectF.left - rectF.right)/2,CW);
        drawDecText(canvas,"圆",-DEFAULT_RECT_WIDTH/2,-DEFAULT_RECT_WIDTH/2);
    }

    private void addOval(Canvas canvas) {
        RectF rectF = new RectF(0,0,DEFAULT_RECT_WIDTH,-DEFAULT_RECT_WIDTH/2);
        mPath.addOval(rectF,CW);
        drawDecText(canvas,"椭圆",DEFAULT_RECT_WIDTH/2,-DEFAULT_RECT_WIDTH/4);
    }
}
