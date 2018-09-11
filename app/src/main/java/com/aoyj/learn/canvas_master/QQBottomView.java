package com.aoyj.learn.canvas_master;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.math.MathUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by aoyuanjie on 2018/8/3.
 */

public class QQBottomView extends View {
    private static final String TAG = QQBottomView.class.getSimpleName();

    private static final int DEFAULT_PAINT_WIDTH = 5;
    private static final int DEFAULT_MAIN_CIRCLE_SIZE = 200;

    private static float DEFAULT_CONTOUR_CENTER_LIMIT = 10;
    private static final int DEFAULT_SECOND_CIRCLE_Height_RADIU = 240;

    private static final float DEFAULT_EYE_LENGTH = 30;
    private static final float DEFAULT_EYE_DISTANCE = 120;
    private static final float DEFAULT_EYE_TO_ORIGIN_DISTANCE = 40;


    private Path mOriginalPath,mPath,mOriginalSencondPath,mEyePath,mMouseOrinalePath,mMousePath,mGesturePath;
    private Paint mPaint,mEyePaint,mMousepaint;
    private float mWidth,mHeight;
    private PathMeasure mPathMeasure;

    private PointF mainCircleStartPoint,mainCircleEndPoint;
    private float[] mainStartPoints = new float[2];
    private float[] mainEndPoints = new float[2];
    private float[] mainTans = new float[2];


    private PointF mEyeCenterPoint = new PointF();
    private PointF mContourCenterPoint = new PointF();
    private float mEeyLength = DEFAULT_EYE_LENGTH;
    private float mEeyDistance = DEFAULT_EYE_DISTANCE;
    private float mEeyCenterPointToOriginalDistance = DEFAULT_EYE_TO_ORIGIN_DISTANCE;

    private PointF centerPoint = new PointF();

    private PointF mMouseCenterPoint = new PointF();
    private float mMouseCircleRadiu = mEeyDistance / 2;


    private float XMaxOffset = (float) (DEFAULT_MAIN_CIRCLE_SIZE / Math.sqrt(2f) - DEFAULT_EYE_DISTANCE / 2 - 20);
    private float XMinOffset = -XMaxOffset;
    private float YMinOffset = (float) (-DEFAULT_MAIN_CIRCLE_SIZE / Math.sqrt(2f)) + DEFAULT_EYE_LENGTH + 20 ;
    private float YMaxOffset = -YMinOffset;
    private float currentTanValue;


    private float contourCenterXMaxOffset = DEFAULT_CONTOUR_CENTER_LIMIT;
    private float contourCenterYMaxOffset = DEFAULT_CONTOUR_CENTER_LIMIT;
    private float contourCenterXMinOffset = -DEFAULT_CONTOUR_CENTER_LIMIT;
    private float contourCenterYMinOffset = -DEFAULT_CONTOUR_CENTER_LIMIT;


    private boolean isSelected = false;
    private RectF contourRectF = new RectF();

    ObjectAnimator xValueAnimator,yValueAnimator;
    AnimatorSet mAnimationSet;

    public QQBottomView(Context context) {
        this(context,null);
    }

    public QQBottomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QQBottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        //外部轮廓相关Paint
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(DEFAULT_PAINT_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        //眼睛鼻子相关Paint;
        mEyePaint = new Paint();
        mEyePaint.setStyle(Paint.Style.STROKE);
        mEyePaint.setColor(Color.GRAY);
        mEyePaint.setAntiAlias(true);
        mEyePaint.setStrokeCap(Paint.Cap.ROUND);
        mEyePaint.setStrokeWidth(10);

        mMousepaint = new Paint();
        mMousepaint.setStyle(Paint.Style.STROKE);
        mMousepaint.setAntiAlias(true);
        mMousepaint.setColor(mEyePaint.getColor());
        mMousepaint.setStrokeWidth(mEyePaint.getStrokeWidth());
        mMousepaint.setStrokeCap(mEyePaint.getStrokeCap());

        //外部轮廓相关Path
        mPath = new Path();
        mPath.setFillType(Path.FillType.EVEN_ODD);
        mOriginalPath = new Path();
        mOriginalSencondPath = new Path();

        //眼睛，嘴巴相关Path
        mEyePath = new Path();
        mMousePath = new Path();
        mMousePath.setFillType(Path.FillType.EVEN_ODD);
        mMouseOrinalePath = new Path();

        mPathMeasure = new PathMeasure();
        mainCircleStartPoint = new PointF();
        mainCircleEndPoint = new PointF();

        //手势角度检测path
        mGesturePath = new Path();

        initDefaultContourCenterPoint();
    }

    private void initWidthAndHeight(int width,int height){
        mWidth = width;
        mHeight = height;
    }

    private void initDefaultEyeAndMouseCenterPoint() {
        mMouseCenterPoint.x = centerPoint.x;
        mMouseCenterPoint.y = centerPoint.y;

        mEyeCenterPoint.x = mMouseCenterPoint.x;
        mEyeCenterPoint.y = mMouseCenterPoint.y - mEeyCenterPointToOriginalDistance;
    }

    private void initDefaultContourCenterPoint(){
        mContourCenterPoint.x = 0;
        mContourCenterPoint.y = 0;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        initWidthAndHeight(getMeasuredWidth(),getMeasuredHeight());
        canvas.translate(mWidth/2,mHeight/2);
        initDefaultEyeAndMouseCenterPoint();
        canvas.save();
        drawContour(canvas);
        canvas.restore();
        drawEye(canvas);
        drawMouse(canvas);
      //  canvas.drawRect(contourRectF,mPaint);

    }

    /**
     * 画嘴巴
     * @param canvas 画布
     */
    private void drawMouse(Canvas canvas) {
        mMouseOrinalePath.reset();
        mMousePath.reset();
        mMouseOrinalePath.addCircle(mMouseCenterPoint.x,mMouseCenterPoint.y + 20,mMouseCircleRadiu, Path.Direction.CW);
        mPathMeasure.setPath(mMouseOrinalePath,false);
        if(!isSelected)
            mPathMeasure.getSegment(mPathMeasure.getLength() * (0.25f - 0.125f),mPathMeasure.getLength() * (0.25f + 0.125f),mMousePath,true);
        else {
            mPathMeasure.getSegment(0, mPathMeasure.getLength() * 0.5f, mMousePath, true);
            mMousePath.close();
        }

       // mMousePath.offset(totalOffsetX,totalOffsetY);
        canvas.drawPath(mMousePath,mMousepaint);
    }

    /**
     * 画眼睛
     * @param canvas 画布
     */
    private void drawEye(Canvas canvas) {
        mEyePath.reset();
        float leftEeyStartPointX = mEyeCenterPoint.x - mEeyDistance / 2;
        float leftEeyStartPointY = mEyeCenterPoint.y + mEeyLength / 2;
        float leftEeyEndPointX = leftEeyStartPointX;
        float leftEeyEndPointY = leftEeyStartPointY - mEeyLength;
        mEyePath.moveTo(leftEeyStartPointX,leftEeyStartPointY);
        mEyePath.lineTo(leftEeyEndPointX,leftEeyEndPointY);

        float rightEeyStartPointX = mEyeCenterPoint.x + mEeyDistance / 2;
        float rigthEeyStartPointY = mEyeCenterPoint.y + mEeyLength / 2;
        float rightEeyEndPointX = rightEeyStartPointX;
        float rightEeyEndPointY = rigthEeyStartPointY - mEeyLength;

        mEyePath.moveTo(rightEeyStartPointX,rigthEeyStartPointY);
        mEyePath.lineTo(rightEeyEndPointX,rightEeyEndPointY);
    //    mEyePath.offset(totalOffsetX,totalOffsetY);
        canvas.drawPath(mEyePath,mEyePaint);
    }



    /**
     * 画外部轮廓
     * @param canvas 画布
     */
    private void drawContour(Canvas canvas){
        mOriginalPath.reset();
        mPath.reset();
        mOriginalSencondPath.reset();
        canvas.rotate(45,0,0);
        mOriginalPath.addCircle(mContourCenterPoint.x,mContourCenterPoint.y,DEFAULT_MAIN_CIRCLE_SIZE, Path.Direction.CCW);
        mPathMeasure.setPath(mOriginalPath,false);
        float originalLength = mPathMeasure.getLength();
        mPathMeasure.getSegment(0,originalLength * 0.75f,mPath,true);
        mPathMeasure.getPosTan(originalLength * 0.75f,mainStartPoints,mainTans);
        mainCircleStartPoint.x = mainStartPoints[0];
        mainCircleStartPoint.y = mainStartPoints[1];
        mOriginalSencondPath.addCircle(mContourCenterPoint.x  - DEFAULT_SECOND_CIRCLE_Height_RADIU + DEFAULT_MAIN_CIRCLE_SIZE  ,mContourCenterPoint.y,DEFAULT_SECOND_CIRCLE_Height_RADIU,Path.Direction.CW);
        mPathMeasure.setPath(mOriginalSencondPath,false);
        float originalSecondLength = mPathMeasure.getLength();
        mPathMeasure.getSegment( 0f,originalSecondLength * 0.3f,mPath,true);
        mPathMeasure.getPosTan(originalSecondLength*0.3f,mainEndPoints,mainTans);
        mainCircleEndPoint.x = mainEndPoints[0];
        mainCircleEndPoint.y = mainEndPoints[1];
        //如果这里moveTo回到之path 判断内部有问题
        //   mPath1.moveTo(mainCircleStartPoint.x,mainCircleStartPoint.y);
        float controlPointTempX = (mainCircleStartPoint.x + mainCircleEndPoint.x) / 2;
        float controlPointTempY = (mainCircleStartPoint.y + mainCircleEndPoint.y) / 2 + 30;
        mPath.quadTo(controlPointTempX,controlPointTempY,mainCircleStartPoint.x,mainCircleStartPoint.y);
        canvas.drawPath(mPath,mPaint);
        mPath.computeBounds(contourRectF,true);
    }

    private long startClickTime,endClickTime;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float correctionX = event.getX() - mWidth/2;
        float correctionY = event.getY() - mHeight/2;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mGesturePath.reset();
                mGesturePath.moveTo(correctionX,correctionY);
                startClickTime = System.currentTimeMillis();
                return true;
            case MotionEvent.ACTION_MOVE:
                calculateOffset(correctionX,correctionY);
                mGesturePath.lineTo(correctionX,correctionX);
                calculateDegree();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                endClickTime = System.currentTimeMillis();
                checkIsClick(correctionX,correctionY);
                retsetOffset();
                break;
        }
        return false;
    }

    private void checkIsClick(float currentClickX, float currentClickY){
        if(endClickTime - startClickTime < 200 && checkIsInnerClickRange(currentClickX,currentClickY) && !isSelected){
            startPerformClick();
        }
    }

    private boolean checkIsInnerClickRange(float currentClickX, float currentClickY){
        if(currentClickX > contourRectF.left && currentClickX < contourRectF.right
                && currentClickY > contourRectF.top && currentClickY < contourRectF.bottom){
            return true;
        }
        return false;
    }


    private void startPerformClick(){
        if(xValueAnimator == null) {
            xValueAnimator = ObjectAnimator.ofFloat(this,"scaleX",1.0f, 0.1f, 1.0f);
        }
        if(yValueAnimator == null){
            yValueAnimator = ObjectAnimator.ofFloat(this,"scaleY",1.0f,0f,1.0f);
        }
        if(mAnimationSet == null){
            mAnimationSet = new AnimatorSet();
        }
        mAnimationSet.setDuration(200l);
        mAnimationSet.setInterpolator(new LinearInterpolator());
        mAnimationSet.play(xValueAnimator).with(yValueAnimator);
        mAnimationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isSelected = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimationSet.removeAllListeners();
                performClickPaintHandler();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimationSet.start();

    }

    private void performClickPaintHandler(){
        mEyePaint.setColor(Color.WHITE);
        mMousepaint.setColor(mEyePaint.getColor());
        mMousepaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        postInvalidate();
    }

    /**
     * 计算手势轨迹的偏移角度
     */
    private void calculateDegree(){
        calculateCenterPositionByDegree();
    }

    /**
     * 计算偏移量
     * @param currentX 当前触摸点的X坐标
     * @param currentY 当前触摸点的Y坐标
     */
    private void calculateOffset(float currentX,float currentY){
        if(currentX != 0){
            currentTanValue = currentY / currentX;
        }
        centerPoint.x = currentX / 6.0f;
        centerPoint.x = MathUtils.clamp(centerPoint.x,XMinOffset,XMaxOffset);

        mContourCenterPoint.x = currentX / 15f;
        mContourCenterPoint.x = MathUtils.clamp(mContourCenterPoint.x,contourCenterXMinOffset,contourCenterXMaxOffset);
        Log.i(TAG,"the current mContourCenterPoint x value is " + mContourCenterPoint.x);
    }

    /**
     * 根据旋转角度获取眼睛，嘴巴的坐标
     */
    private void calculateCenterPositionByDegree(){
        Log.i(TAG,"the current center point x is " + centerPoint.x);
        centerPoint.y = centerPoint.x * currentTanValue;
        centerPoint.y = MathUtils.clamp(centerPoint.y,YMinOffset,YMaxOffset);

        mContourCenterPoint.y = mContourCenterPoint.x * currentTanValue;
        mContourCenterPoint.y = MathUtils.clamp(mContourCenterPoint.y,contourCenterYMinOffset,contourCenterYMaxOffset);
        Log.i(TAG,"the current mContourCenterPoint y value is " + mContourCenterPoint.y + "-----------------------");
        initDefaultEyeAndMouseCenterPoint();
        postInvalidate();
    }

    /**
     * 重设偏移量
     */
    private void retsetOffset(){
        centerPoint.x = 0;
        centerPoint.y = 0;
        initDefaultEyeAndMouseCenterPoint();
        initDefaultContourCenterPoint();
        postInvalidate();
    }
}

