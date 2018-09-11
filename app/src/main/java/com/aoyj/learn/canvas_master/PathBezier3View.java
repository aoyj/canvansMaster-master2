package com.aoyj.learn.canvas_master;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by aoyuanjie on 2018/7/31.
 */

public class PathBezier3View extends View {
    //一个常量，用来计算圆形贝塞尔曲线控制点的位置
    private static final float C = 0.551915024494f;

    private Paint mPaint,mBluePaint;

    private int mCenterX,mCenterY;
    private float radius = 300;
    private float mDiffrence = radius * C;

    private Path mPath,mPath2;

    private int mWidth,mHeight;

    //圆的4个绘制点
    private PointF[] pointDraws = new PointF[4];
    //绘制贝塞尔圆的8个控制点
    private PointF[] pointControls = new PointF[8];


    public PathBezier3View(Context context) {
        this(context,null);
    }

    public PathBezier3View(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathBezier3View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
      //  setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
      //  mPaint.setStrokeWidth(6);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setDither(true);

        mBluePaint = new Paint();
        mBluePaint.setColor(Color.BLUE);
        mBluePaint.setAntiAlias(true);
        mBluePaint.setStrokeWidth(5);
        mBluePaint.setStyle(Paint.Style.FILL);

        mPath = new Path();
        mPath2 = new Path();
        mPath2.setFillType(Path.FillType.EVEN_ODD);
    }

    private void setPointData(int width, int height){
        mWidth = width;
        mHeight = height;
        mCenterX = mWidth/2;
        mCenterY = mHeight/2;
        for(int i = 0 ; i < pointDraws.length ; i++){
            pointDraws[i] = new PointF();
        }

        pointDraws[0].x = -radius;
        pointDraws[0].y = 0;

        pointDraws[1].x = 0;
        pointDraws[1].y = -radius;

        pointDraws[2].x = radius;
        pointDraws[2].y = 0;

        pointDraws[3].x = 0;
        pointDraws[3].y = radius;

        for(int i = 0 ; i < pointControls.length ; i++){
            pointControls[i] = new PointF();
        }

        pointControls[0].x = pointDraws[0].x;
        pointControls[0].y = pointDraws[0].y - mDiffrence;

        pointControls[1].x = pointDraws[1].x - mDiffrence;
        pointControls[1].y = pointDraws[1].y;

        pointControls[2].x = pointDraws[1].x + mDiffrence;
        pointControls[2].y = pointDraws[1].y;

        pointControls[3].x = pointDraws[2].x;
        pointControls[3].y = pointDraws[2].y - mDiffrence;

        pointControls[4].x = pointDraws[2].x;
        pointControls[4].y = pointDraws[2].y + mDiffrence;

        pointControls[5].x = pointDraws[3].x + mDiffrence;
        pointControls[5].y = pointDraws[3].y;

        pointControls[6].x = pointDraws[3].x - mDiffrence;
        pointControls[6].y = pointDraws[3].y;

        pointControls[7].x = pointDraws[0].x;
        pointControls[7].y = pointDraws[0].y + mDiffrence;

        pointDraws[1].y += 200;
        pointControls[5].y -= 80;
        pointControls[6].y -= 80;

        pointControls[4].x -= 30;
        pointControls[7].x += 30;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setPointData(w,h);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath.reset();
        canvas.translate(mCenterX,mCenterY);
        int saved =  canvas.saveLayer(null,null,Canvas.ALL_SAVE_FLAG);
        canvas.drawARGB(0, 0, 0, 0);
        setPointData(getMeasuredWidth(),getMeasuredHeight());
        getHeartPaht();
        mPath2.addRect(waveRect, Path.Direction.CW);
        getWavePath();
        canvas.clipRect(waveRect);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mPath2,mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawPath(mPath,mPaint);


       // canvas.drawCircle(0,0,200,mPaint);
       // drawLine(canvas);
        mPaint.setXfermode(null);
        canvas.restoreToCount(saved);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mPath,mPaint);
        startWaveAnimation();
    }

    boolean isStartAnimator = false;
    ValueAnimator animator;
    float percent = 0.5f;
    float offset;
    float curvePeriod;
    private void startWaveAnimation(){
        if(!isStartAnimator){
            isStartAnimator = true;
            if(animator == null){
                animator = ValueAnimator.ofFloat(0,curvePeriod);
                animator.setDuration(1000l);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setInterpolator(new LinearInterpolator());
                animator.addUpdateListener(a -> {
                    offset = (float) a.getAnimatedValue();
                //    percent = a.getAnimatedFraction();
                    postInvalidate();
                });
            }
            animator.start();
        }
    }

    private void getHeartPaht(){
        mPath.moveTo(pointDraws[0].x,pointDraws[0].y);
        mPath.cubicTo(pointControls[0].x,pointControls[0].y,pointControls[1].x,pointControls[1].y,pointDraws[1].x,pointDraws[1].y );
        mPath.cubicTo(pointControls[2].x,pointControls[2].y,pointControls[3].x,pointControls[3].y,pointDraws[2].x,pointDraws[2].y);
        mPath.cubicTo(pointControls[4].x,pointControls[4].y,pointControls[5].x,pointControls[5].y,pointDraws[3].x,pointDraws[3].y );
        mPath.cubicTo(pointControls[6].x,pointControls[6].y,pointControls[7].x,pointControls[7].y,pointDraws[0].x,pointDraws[0].y);
        mPath.close();
        mPath.computeBounds(waveRect,true);
        curvePeriod = waveRect.right - waveRect.left;
    }
    private RectF waveRect = new RectF();
    private void getWavePath(){
        mPath2.reset();
        mPaint.setStyle(Paint.Style.FILL);
        float amplitude = 50f;
        PointF startCurvePoint = new PointF();
        startCurvePoint.x = waveRect.left - curvePeriod + offset;
        startCurvePoint.y = waveRect.bottom - (waveRect.bottom - waveRect.top) * percent;
        mPath2.moveTo(startCurvePoint.x,startCurvePoint.y);
        for(int i = 0 ; i < 2 ; i++)
            drawPeriodCurve(curvePeriod,amplitude);
        mPath2.lineTo(startCurvePoint.x + 2*curvePeriod,waveRect.bottom);
        mPath2.lineTo(startCurvePoint.x,waveRect.bottom);
        mPath2.lineTo(startCurvePoint.x,startCurvePoint.y);
    }
    private void drawPeriodCurve(float priodLength,float amplitude){
        mPath2.rQuadTo(priodLength/4f, -amplitude,priodLength/2f,0f);
        mPath2.rQuadTo(priodLength/4f, amplitude,priodLength/2f,0f);
    }

    private void drawLine(Canvas canvas){
        canvas.drawLine(pointControls[7].x,pointControls[7].y,pointDraws[0].x,pointDraws[0].y,mPaint);
        canvas.drawLine(pointDraws[0].x,pointDraws[0].y,pointControls[0].x,pointControls[0].y,mPaint);

        canvas.drawLine(pointControls[1].x,pointControls[1].y,pointDraws[1].x,pointDraws[1].y,mPaint);
        canvas.drawLine(pointDraws[1].x,pointDraws[1].y,pointControls[2].x,pointControls[2].y,mPaint);

        canvas.drawLine(pointControls[3].x,pointControls[3].y,pointDraws[2].x,pointDraws[2].y,mPaint);
        canvas.drawLine(pointDraws[2].x,pointDraws[2].y,pointControls[4].x,pointControls[4].y,mPaint);

        canvas.drawLine(pointControls[5].x,pointControls[5].y,pointDraws[3].x,pointDraws[3].y,mPaint);
        canvas.drawLine(pointDraws[3].x,pointDraws[3].y,pointControls[6].x,pointControls[6].y,mPaint);


        for(int i = 0; i< pointControls.length; i++){
            mBluePaint.setStrokeCap(Paint.Cap.ROUND);
            mBluePaint.setStrokeWidth(20);
            mBluePaint.setColor(Color.GRAY);
            canvas.drawPoint(pointControls[i].x,pointControls[i].y,mBluePaint);
        }

        for(int i = 0; i< pointDraws.length; i++){
            mBluePaint.setStrokeCap(Paint.Cap.ROUND);
            mBluePaint.setStrokeWidth(20);
            mBluePaint.setColor(Color.BLUE);
            canvas.drawPoint(pointDraws[i].x,pointDraws[i].y,mBluePaint);
        }
    }
}

