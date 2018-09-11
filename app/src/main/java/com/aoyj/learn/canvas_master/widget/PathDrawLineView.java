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
 * Created by aoyuanjie on 2018/7/28.
 */

public class PathDrawLineView extends View {
    private static final int FLAG_DESTANCE = 300;

    private Paint mPaint,mBluePaint;
    private Path path,noClosePath;

    private int mWidth,mHeight;

    public PathDrawLineView(Context context) {
        this(context,null);
    }

    public PathDrawLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathDrawLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint  = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3f);
        mPaint.setTextSize(30);

        mBluePaint  = new Paint();
        mBluePaint.setColor(Color.BLUE);
        mBluePaint.setStyle(Paint.Style.STROKE);
        mBluePaint.setStrokeWidth(3f);
        mBluePaint.setTextSize(30);

        path = new Path();

        noClosePath = new Path();
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
        drawLineByPath(canvas);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

    }

    private void drawLineByPath(Canvas canvas) {
        //将坐标系设置成 数学通用坐标系
        canvas.translate(mWidth/2,mHeight/2);
        canvas.save();
        canvas.scale(1,-1,0,0);

        noClosePath.moveTo(0,0);
        noClosePath.lineTo(FLAG_DESTANCE,FLAG_DESTANCE);
        noClosePath.lineTo(FLAG_DESTANCE,0);
        canvas.drawPath(noClosePath,mBluePaint);
        path.moveTo(0,0);
        path.lineTo(-FLAG_DESTANCE,FLAG_DESTANCE);
        path.lineTo(-FLAG_DESTANCE,0);
        path.close();
        canvas.drawPath(path,mPaint);
        canvas.restore();
        drawDecText(canvas,"path close",-FLAG_DESTANCE*2/3,-FLAG_DESTANCE/3);

        /*path.reset();
        RectF rectF = new RectF();
        float rectSize = 400f;
        rectF.left = 100;
        rectF.top = 100;
        rectF.right = rectF.left + rectSize;
        rectF.bottom = rectF.top + rectSize;
        path.addRect(rectF, Path.Direction.CCW);
        path.lineTo(-500,500);
        canvas.drawPath(path,mBluePaint);*/
    }

    private void drawDecText(Canvas canvas,String dec,int centerX,int centerY){
        canvas.save();
        float txtWidth = mPaint.measureText(dec);
        int baseLineX = (int) (centerX - txtWidth/2);
        int baseLineY = (int) (centerY + Tools.getBaseLine(mPaint));
       // canvas.translate(-80,20);
     //   canvas.rotate(90,centerX,centerX);
        mPaint.setStrokeWidth(2);
        canvas.drawText(dec,baseLineX,baseLineY,mPaint);
        canvas.restore();
    }


}
