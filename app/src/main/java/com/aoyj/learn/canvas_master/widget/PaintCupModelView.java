package com.aoyj.learn.canvas_master.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by drizzt on 2018/8/9.
 */

public class PaintCupModelView extends View {
    private static final float DEFAULT_TXT_DISATANCE_TO_X = 50;
    private static final float DEFAULT_LINE_LENGTH = 150;
    private static final String BUTT_MODD = "BUTT 模式";
    private static final String ROUND_MODE = "ROUND 模式";
    private static final String SQUARE_MODE = "SQUARE 模式";
    private Paint mPaint,mRedPaint;

    private float mWidth,mHeight;
    private PointF buttTxtPoint,roundTxtPoint,squareTxtPoint;

    private Path mPath;

    public PaintCupModelView(Context context) {
        this(context,null);
    }

    public PaintCupModelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PaintCupModelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(15f);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPaint.setTextSize(30);

        mRedPaint = new Paint();
        mRedPaint.setColor(Color.RED);
        mRedPaint.setStyle(Paint.Style.STROKE);
        mRedPaint.setStrokeWidth(1);
        mRedPaint.setAntiAlias(true);

        buttTxtPoint = new PointF();
        roundTxtPoint = new PointF();
        squareTxtPoint = new PointF();

        mPath = new Path();
    }

     private void setBaseData(float width,float height){
        mWidth = width;
        mHeight = height;
        buttTxtPoint.x = (-mWidth/2 + (mWidth/3 - mWidth/2))/2F;
        buttTxtPoint.y = -DEFAULT_TXT_DISATANCE_TO_X;

        roundTxtPoint.x = 0;
        roundTxtPoint.y = -DEFAULT_TXT_DISATANCE_TO_X;

        squareTxtPoint.x = ((mWidth/2 - mWidth/3) + mWidth/2)/2F;
        squareTxtPoint.y = -DEFAULT_TXT_DISATANCE_TO_X;
     }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setBaseData(w,h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBaseData(getMeasuredWidth(),getMeasuredHeight());
        canvas.translate(mWidth/2,mHeight/2);
        drawContent(canvas,BUTT_MODD);
        drawContent(canvas,ROUND_MODE);
        drawContent(canvas,SQUARE_MODE);

        drawCurve(canvas);
    }

    private float waveOffset = -100f;

    private void drawCurve(Canvas canvas) {
        PointF curveStartPoint = new PointF();
        PointF curveEndPoint = new PointF();
        PointF controlPoint1 = new PointF();
        PointF controlPoint2 = new PointF();
        PointF centerPoint = new PointF();

        curveStartPoint.x = waveOffset;
        curveStartPoint.y = curveEndPoint.y = 500;
        curveEndPoint.x = 400f + curveStartPoint.x;

        centerPoint.x = 0;
        centerPoint.y = curveStartPoint.y - 200;

        controlPoint1.x = curveStartPoint.x + 100;
        controlPoint1.y  = curveStartPoint.y - 300;
        controlPoint2.y = curveStartPoint.y + 300;
        controlPoint2.x = curveEndPoint.x  - 100;
      //  mPath.moveTo(curveStartPoint.x,curveStartPoint.y);
      //  mPath.cubicTo(controlPoint1.x,controlPoint1.y,controlPoint2.x,controlPoint2.y,curveEndPoint.x,curveEndPoint.y);
       // mPath.quadTo(curveStartPoint.x, centerPoint.y,centerPoint.x,centerPoint.y);
        //canvas.drawPath(mPath,mRedPaint);

        PathMeasure pathMeasure = new PathMeasure();
        Path tempPath = new Path();
        float[] startOvals = new float[2];
        float[] startOvalTans = new float[2];
        PointF startOvalPoint = new PointF();
        PointF endOvalPoint = new PointF();
        PointF controlOvalPoint = new PointF();

        float[] rightEndPoints = new float[2];
        float[] rightEndTans = new float[2];
        PointF rightStartOvalPoint = new PointF();
        PointF rightControlOvalPoint = new PointF();
        PointF rightEndOvalPoint = new PointF();

        RectF rectF = new RectF();
        rectF.left = -200;
        rectF.top = 500;
        rectF.right = rectF.left + 400;
        rectF.bottom = rectF.top + 200;
        mPath.addOval(rectF, Path.Direction.CCW);
        pathMeasure.setPath(mPath,false);
        pathMeasure.getSegment(0.05f*pathMeasure.getLength(),0.45f*pathMeasure.getLength(),tempPath,true);
        pathMeasure.setPath(tempPath,false);
        pathMeasure.getPosTan(pathMeasure.getLength(),startOvals,startOvalTans);
        startOvalPoint.x = startOvals[0];
        startOvalPoint.y = startOvals[1];
        float degressRadians = (float) (Math.atan2(startOvalTans[1],startOvalTans[0]));
        endOvalPoint.x = startOvalPoint.x - 12;
        endOvalPoint.y = startOvalPoint.y + 40;
        controlOvalPoint.x =  endOvalPoint.x;
        controlOvalPoint.y = (float) (Math.tan(degressRadians) * (controlOvalPoint.x - startOvalPoint.x) + startOvalPoint.y);

        pathMeasure.getPosTan(0,rightEndPoints,rightEndTans);
        rightEndOvalPoint.x = rightEndPoints[0];
        rightEndOvalPoint.y = rightEndPoints[1];
        rightStartOvalPoint.x = rightEndOvalPoint.x + 12;
        rightStartOvalPoint.y = rightEndOvalPoint.y + 40;
        rightControlOvalPoint.x = rightStartOvalPoint.x;
        rightControlOvalPoint.y = controlOvalPoint.y;
        tempPath.quadTo(controlOvalPoint.x,controlOvalPoint.y,endOvalPoint.x,endOvalPoint.y);
        tempPath.lineTo(rightStartOvalPoint.x,rightStartOvalPoint.y);
        tempPath.quadTo(rightControlOvalPoint.x,rightControlOvalPoint.y,rightEndOvalPoint.x,rightEndOvalPoint.y);
        canvas.drawPath(tempPath,mRedPaint);

        float[] points = new float[8];
        points[0] = curveStartPoint.x;
        points[1] = curveStartPoint.y;
        points[2] = controlPoint1.x;
        points[3] = controlPoint1.y;
        points[4] = controlPoint2.x;
        points[5] = controlPoint2.y;
        points[6] = curveEndPoint.x;
        points[7] = curveEndPoint.y;
        tempPath.reset();
       // tempPath.moveTo(curveStartPoint.x,curveStartPoint.y);

        tempPath.moveTo(curveStartPoint.x - 800,curveStartPoint.y);
        tempPath.cubicTo(controlPoint1.x - 800,controlPoint1.y,controlPoint2.x -800,controlPoint2.y,curveEndPoint.x - 800,curveEndPoint.y);
        tempPath.cubicTo(controlPoint1.x - 400,controlPoint1.y,controlPoint2.x - 400,controlPoint2.y,curveEndPoint.x - 400,curveEndPoint.y);
        tempPath.cubicTo(controlPoint1.x,controlPoint1.y,controlPoint2.x,controlPoint2.y,curveEndPoint.x,curveEndPoint.y);
        tempPath.cubicTo(controlPoint1.x + 400,controlPoint1.y,controlPoint2.x + 400,controlPoint2.y,curveEndPoint.x + 400,curveEndPoint.y);
        tempPath.cubicTo(controlPoint1.x + 800,controlPoint1.y,controlPoint2.x + 800,controlPoint2.y,curveEndPoint.x + 800,curveEndPoint.y);
        tempPath.rLineTo(0,300);
        tempPath.rLineTo(-2000,0);
        tempPath.close();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
        canvas.drawPath(tempPath,mPaint);
        canvas.drawPoints(points,mPaint);
        startAnimation();
    }


    ValueAnimator animator;
    boolean isStartAnimator = false;
    private void startAnimation(){
        if(!isStartAnimator){
            isStartAnimator = true;
            if(animator == null) {
                animator = ValueAnimator.ofFloat(-200F, 200F);
                animator.setInterpolator(new LinearInterpolator());
                animator.addUpdateListener( a ->{
                    waveOffset = (float) a.getAnimatedValue();
                    postInvalidate();
                });
                animator.setDuration(1000l);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.start();
            }
        }
    }

    private void drawContent(Canvas canvas,String txt) {
         float txtWidth = mPaint.measureText(txt);
         PointF txtBaseLinePoint = new PointF();
         PointF startPoint = new PointF();
         PointF endPoint = new PointF();
         switch (txt){
             case BUTT_MODD:
                 mPaint.setStrokeCap(Paint.Cap.BUTT);
                 txtBaseLinePoint.x = buttTxtPoint.x - txtWidth/2f;
                 txtBaseLinePoint.y = buttTxtPoint.y + Tools.getBaseLine(mPaint);
                 startPoint.x = buttTxtPoint.x - DEFAULT_LINE_LENGTH/2;
                 startPoint.y = buttTxtPoint.y + DEFAULT_TXT_DISATANCE_TO_X;
                 endPoint.x = startPoint.x + DEFAULT_LINE_LENGTH;
                 endPoint.y = startPoint.y;
                 break;
             case ROUND_MODE:
                 mPaint.setStrokeCap(Paint.Cap.ROUND);
                 txtBaseLinePoint.x = roundTxtPoint.x - txtWidth/2f;
                 txtBaseLinePoint.y = roundTxtPoint.y + Tools.getBaseLine(mPaint);
                 startPoint.x = roundTxtPoint.x - DEFAULT_LINE_LENGTH/2;
                 startPoint.y = roundTxtPoint.y + DEFAULT_TXT_DISATANCE_TO_X;
                 endPoint.x = startPoint.x + DEFAULT_LINE_LENGTH;
                 endPoint.y = startPoint.y;
                 break;
             case SQUARE_MODE:
                 mPaint.setStrokeCap(Paint.Cap.SQUARE);
                 txtBaseLinePoint.x = squareTxtPoint.x - txtWidth/2f;
                 txtBaseLinePoint.y = squareTxtPoint.y + Tools.getBaseLine(mPaint);
                 startPoint.x = squareTxtPoint.x - DEFAULT_LINE_LENGTH/2;
                 startPoint.y = squareTxtPoint.y + DEFAULT_TXT_DISATANCE_TO_X;
                 endPoint.x = startPoint.x + DEFAULT_LINE_LENGTH;
                 endPoint.y = startPoint.y;
                 break;
         }
         float redLinOffset = 25;
         canvas.drawText(txt,txtBaseLinePoint.x,txtBaseLinePoint.y,mPaint);
         canvas.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y,mPaint);
         canvas.drawLine(startPoint.x,startPoint.y + redLinOffset,startPoint.x,startPoint.y -redLinOffset , mRedPaint);
         canvas.drawLine(endPoint.x,endPoint.y + redLinOffset,endPoint.x,endPoint.y - redLinOffset , mRedPaint);

    }
}
