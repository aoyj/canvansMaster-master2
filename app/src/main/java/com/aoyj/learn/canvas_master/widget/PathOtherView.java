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
 * Created by aoyuanjie on 2018/8/1.
 */

public class PathOtherView extends View {
    private Paint mPaint,mRedPaint;
    private Path mPath,tempPath;
    private PathMeasure mPahtMeasure;

    private int mWidth,mHeight;

    public PathOtherView(Context context) {
        this(context,null);
    }

    public PathOtherView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathOtherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);

        mRedPaint = new Paint();
        mRedPaint.setColor(Color.RED);
        mRedPaint.setStyle(Paint.Style.STROKE);
        mRedPaint.setStrokeWidth(1);
        mRedPaint.setAntiAlias(true);
        mRedPaint.setTextSize(25);

        mPath = new Path();
        tempPath = new Path();
        mPahtMeasure = new PathMeasure();
    }

    private void initWAndH(int width,int height){
        mWidth = width;
        mHeight = height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initWAndH(getMeasuredWidth(),getMeasuredHeight());
        //rMoveTo(),rLineTo(),rQuaTo(),rCubiTo()等都是相对于当前位置的偏移量
        canvas.save();
        mPath.moveTo(100,100);
        mPath.lineTo(500,100);
        canvas.drawPath(mPath,mPaint);
        //画第一个箭头
        mPath.reset();
        mPath.moveTo(500,100);
        mPath.rLineTo(20,0);
        canvas.rotate(135,500,100);
        canvas.drawPath(mPath,mRedPaint);
        mPath.reset();
        mPath.moveTo(500,100);
        mPath.rLineTo(20,0);
        canvas.rotate(90,500,100);
        canvas.drawPath(mPath,mRedPaint);
        canvas.restore();
        drawDec(canvas,"lineTo()方法",500,100,0);

        canvas.save();
        mPath.reset();
        mPath.moveTo(100,100);
        mPath.rLineTo(500,100);
        mPahtMeasure.setPath(mPath,false);
        float secondLineLength = mPahtMeasure.getLength();
        mPahtMeasure.getSegment(secondLineLength - 20,secondLineLength,tempPath,true);
        float[] pos = new float[2];
        float[] tan = new float[2];
        mPahtMeasure.getPosTan(secondLineLength,pos,tan);
        float degree = (float) (Math.atan2(tan[1],tan[0]) * 180.0 / Math.PI);
        canvas.drawPath(mPath,mPaint);
       //画第二个箭头
        mPath.reset();
        canvas.rotate(45,600,200);
        canvas.drawPath(tempPath,mRedPaint);
        canvas.rotate(-90,600,200);
        canvas.drawPath(tempPath,mRedPaint);
        canvas.restore();
        canvas.save();
        canvas.translate(100,100);
        drawDec(canvas,"rLineTo()方法",secondLineLength,0,degree);
        canvas.restore();

        //填充模式
        pathFillStyleTest(canvas);
        drawCoordinateLine(canvas);

    }

    private void drawDec(Canvas canvas,String txt,float leftX,float centerY,float rotateDegree){
        canvas.save();
        int baseLineY = (int) Tools.getBaseLine(mRedPaint);
        canvas.rotate(rotateDegree);
        canvas.translate(15 +leftX,centerY);
        canvas.drawText(txt,0,baseLineY,mRedPaint);
        canvas.restore();
    }

    private void pathFillStyleTest(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        canvas.translate(mWidth/2,mHeight/2);
        mPath.reset();
        RectF innerRect = new RectF();
        int innerRectSize = 300;
        innerRect.left = 200 - innerRectSize/2;
        innerRect.top = 200 - innerRectSize/2;
        innerRect.right = innerRect.left + innerRectSize;
        innerRect.bottom = innerRect.top + innerRectSize;

        RectF outRect = new RectF();
        int outRectSize = 400;
        outRect.left = 0;
        outRect.top = 0;
        outRect.right = outRect.left + outRectSize;
        outRect.bottom = outRect.top + outRectSize;
        mPath.addRect(innerRect, Path.Direction.CW);
        mPath.addRect(outRect,Path.Direction.CCW);
        mPath.setFillType(Path.FillType.EVEN_ODD);
        canvas.drawPath(mPath,mPaint);

        mPath.reset();
        RectF outRect1 = new RectF();
        int outRectSize1 = 400;
        outRect1.left = -400;
        outRect1.top = -400;
        outRect1.right = outRect1.left + outRectSize1;
        outRect1.bottom = outRect1.top + outRectSize1;

        RectF innerRect1 = new RectF();
        int innerRectSize1 = 300;
        innerRect1.left = -200 - innerRectSize1/2;
        innerRect1.top = -200 - innerRectSize1/2;
        innerRect1.right = innerRect1.left + innerRectSize1;
        innerRect1.bottom = innerRect1.top + innerRectSize1;

        mPath.addRect(outRect1, Path.Direction.CW);
        mPath.addRect(innerRect1, Path.Direction.CCW);
        mPath.setFillType(Path.FillType.WINDING);
        canvas.drawPath(mPath,mPaint);

        drawFillStyleDec(canvas,"奇偶规则",20,50);
        drawFillStyleDec(canvas,"非0环绕数规则",-400,-450);
    }

    private void drawCoordinateLine(Canvas canvas) {
        canvas.drawLine(0,-mHeight/2,0,mHeight/2,mRedPaint);
        canvas.drawLine(-mWidth/2,0,mWidth/2,0,mRedPaint);
    }

    private void drawFillStyleDec(Canvas canvas,String fillStyleDec,int baseLineX,int centerY){
        int baseLineY = (int) (centerY + Tools.getBaseLine(mPaint));
        canvas.drawText(fillStyleDec,baseLineX,baseLineY,mRedPaint);
    }
}
