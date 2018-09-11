package com.aoyj.learn.canvas_master.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by aoyuanjie on 2018/7/23.
 */

public class MyTextView extends View {
    String TAG = View.class.getSimpleName();
    private Paint mPaint;
    private String txt = "天下霸道之剑";
    private String decTxt = "基准点";
    int height;
    int width;
    int centerY;
    int centerX;

    public MyTextView(Context context) {
        this(context,null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        //初始化画笔
        initPaint();
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(150f);
        //测量文字的宽度
       // mPaint.measureText(txt);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(hasWindowFocus)
            mPaint.setTextSize(150f);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Log.i(TAG,"onRestoreInstanceState");
        super.onRestoreInstanceState(state);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Log.i(TAG,"onSaveInstanceState执行");
        return super.onSaveInstanceState();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 绘制图片时需要确定BaseLine的位置，{@link Tools#getBaseLine(Paint)}
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        height = getMeasuredHeight();
        width = getMeasuredWidth();
        centerY = height /2;
        centerX = width /2;
        //commonDrawHorizentalTxt(canvas);
       // drawPositionText(canvas);
        drawTxtFormPath(canvas);
    }

    private void drawTxtFormPath(Canvas canvas){
        mPaint.setTextSize(60f);
        mPaint.setStyle(Paint.Style.STROKE);
        String txt = "天下霸道之剑";
        float circleRadius = 250f;
        Path path = new Path();
        Path tempPath = new Path();
        PathMeasure pathMeasure = new PathMeasure();
        path.addCircle(centerX,centerY,circleRadius, Path.Direction.CCW);
        pathMeasure.setPath(path,false);
        pathMeasure.getSegment(0.125f * pathMeasure.getLength(),pathMeasure.getLength() * 0.375f,tempPath,true);
        mPaint.setColor(Color.RED);
        canvas.save();
        canvas.rotate(180,centerX,centerY);
        canvas.drawPath(tempPath,mPaint);
        canvas.drawTextOnPath(txt,tempPath,0f,50f,mPaint);
        canvas.restore();
    }

    private void drawPositionText(Canvas canvas){
        float verticalPeriodDistance = 100f;
        mPaint.setTextSize(100f);
        String txt = "天下霸道之剑";
        float[] postions = new float[txt.length() * 2];
        Rect rect = new Rect();
        mPaint.getTextBounds(txt,0,txt.length() -1,rect);
        float periodTxtHeight = Math.abs(rect.bottom - rect.top);
        for(int i = 0; i < txt.length(); i++){
            postions[i * 2] =  centerX - mPaint.measureText(String.valueOf(txt.charAt(i))) /2;
            postions[i * 2 + 1] = periodTxtHeight * i + verticalPeriodDistance * (i + 1) + Tools.getBaseLine(mPaint);
        }
        canvas.drawPosText(txt,postions,mPaint);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);
        canvas.drawPoints(postions,mPaint);
    }

    private void commonDrawHorizentalTxt(Canvas canvas){
        mPaint.setTextSize(150f);
        int baseLineY = centerY + (int)Tools.getBaseLine(mPaint);
        //  mPaint.setTextAlign(Paint.Align.RIGHT);
        int baseLineX = centerX - (int)mPaint.measureText(txt) /2;
        //int baseLineX = centerX;
        //  int baseLineX = centerX + (int)mPaint.measureText(txt) /2;

        drawTxtTopLine(canvas,baseLineY);
        drawTextAscentLine(canvas,baseLineY);
        drawTxtBaseLine(canvas,baseLineY);
        drawDescent(canvas,baseLineY);
        drawBottomLine(canvas,baseLineY);
        drawTxt(canvas,baseLineX,baseLineY);
        drawCenterLine(canvas,baseLineX,baseLineY);
        drawBaseLineCircle(canvas,baseLineX,baseLineY);
        drawArrow(canvas,baseLineX,baseLineY,100,"基准点",true);
    }


    private static final float DESC_TXT_OFFSET_TO_BASELIN_Y = 600;
    private static final float DESC_TXT_PERIOD_DISTANCE = 80;
    private static final float DESC_TXT_OFFSET_TO_BASELIN_X = 50;
    private static final float DESC_TXT_COLOR_LINE_LENGTH = 120;
    private static final float DESC_TXT_TO_LINE_HORIZONTAL_DISTANCE = 20;
    /**
     * draw txt Top line
     * @param canvas 画布
     * @param baseLineY 基准点的Y坐标
     */
    private void drawTxtTopLine(Canvas canvas,float baseLineY){
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6);
        float currentY = baseLineY + mPaint.getFontMetrics().top;
        canvas.drawLine(0,currentY,width,currentY,paint);
        paint.setTextSize(40);
        float descY = baseLineY -DESC_TXT_OFFSET_TO_BASELIN_Y;
        canvas.drawLine(DESC_TXT_OFFSET_TO_BASELIN_X,descY,DESC_TXT_OFFSET_TO_BASELIN_X + DESC_TXT_COLOR_LINE_LENGTH,descY,paint);
        canvas.drawText("Top线",DESC_TXT_OFFSET_TO_BASELIN_X + DESC_TXT_COLOR_LINE_LENGTH + DESC_TXT_TO_LINE_HORIZONTAL_DISTANCE,descY,paint);
    }

    /**
     * draw txt Ascent line
     * @param canvas
     * @param baseLineY
     */
    private void drawTextAscentLine(Canvas canvas,float baseLineY){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(6);
        paint.setAntiAlias(true);

        float currentY = baseLineY + mPaint.getFontMetrics().ascent;
        canvas.drawLine(0,currentY,width,currentY,paint);

        paint.setTextSize(40);
        float descY = baseLineY -DESC_TXT_OFFSET_TO_BASELIN_Y + DESC_TXT_PERIOD_DISTANCE;
        canvas.drawLine(DESC_TXT_OFFSET_TO_BASELIN_X,descY,DESC_TXT_OFFSET_TO_BASELIN_X + DESC_TXT_COLOR_LINE_LENGTH,descY,paint);
        canvas.drawText("Ascent线",DESC_TXT_OFFSET_TO_BASELIN_X + DESC_TXT_COLOR_LINE_LENGTH + DESC_TXT_TO_LINE_HORIZONTAL_DISTANCE,descY,paint);
    }

    /**
     * 绘制基准线
     * @param canvas 画布
     * @param baseLineY 基准线Y坐标
     */
    private void drawTxtBaseLine(Canvas canvas,float baseLineY){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(6);
        paint.setAntiAlias(true);

        float currentY = baseLineY;
        canvas.drawLine(0,currentY,width,currentY,paint);

        paint.setTextSize(40);
        float descY = baseLineY -DESC_TXT_OFFSET_TO_BASELIN_Y + 2 * DESC_TXT_PERIOD_DISTANCE;
        canvas.drawLine(DESC_TXT_OFFSET_TO_BASELIN_X,descY,DESC_TXT_OFFSET_TO_BASELIN_X + DESC_TXT_COLOR_LINE_LENGTH,descY,paint);
        canvas.drawText("基准线",DESC_TXT_OFFSET_TO_BASELIN_X + DESC_TXT_COLOR_LINE_LENGTH + DESC_TXT_TO_LINE_HORIZONTAL_DISTANCE,descY,paint);
    }

    /**
     * draw txt descent line
     * @param canvas 画布
     * @param baseLineY 基准线Y坐标
     */
    private void drawDescent(Canvas canvas,float baseLineY){
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(6);
        paint.setAntiAlias(true);

        float currentY = baseLineY + mPaint.getFontMetrics().descent;
        canvas.drawLine(0,currentY,width,currentY,paint);

        paint.setTextSize(40);
        float descY = baseLineY -DESC_TXT_OFFSET_TO_BASELIN_Y + 3 * DESC_TXT_PERIOD_DISTANCE;
        canvas.drawLine(DESC_TXT_OFFSET_TO_BASELIN_X,descY,DESC_TXT_OFFSET_TO_BASELIN_X + DESC_TXT_COLOR_LINE_LENGTH,descY,paint);
        canvas.drawText("descent线",DESC_TXT_OFFSET_TO_BASELIN_X + DESC_TXT_COLOR_LINE_LENGTH + DESC_TXT_TO_LINE_HORIZONTAL_DISTANCE,descY,paint);
    }

    /**
     * draw txt bottom line
     * @param canvas 画布
     * @param baseLineY 基准线Y坐标
     */
    private void drawBottomLine(Canvas canvas,float baseLineY){
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setAntiAlias(true);

        float currentY = baseLineY + mPaint.getFontMetrics().bottom;
        paint.setStrokeWidth(6);
        canvas.drawLine(0,currentY,width,currentY,paint);

        paint.setTextSize(40);
        float descY = baseLineY -DESC_TXT_OFFSET_TO_BASELIN_Y + 4 * DESC_TXT_PERIOD_DISTANCE;
        canvas.drawLine(DESC_TXT_OFFSET_TO_BASELIN_X,descY,DESC_TXT_OFFSET_TO_BASELIN_X + DESC_TXT_COLOR_LINE_LENGTH,descY,paint);
        canvas.drawText("bottom线",DESC_TXT_OFFSET_TO_BASELIN_X + DESC_TXT_COLOR_LINE_LENGTH + DESC_TXT_TO_LINE_HORIZONTAL_DISTANCE,descY,paint);
    }

    private void drawTxt(Canvas canvas,int baseLineX,int baseLineY){
        mPaint.setColor(Color.BLUE);
        canvas.drawText(txt,baseLineX,baseLineY,mPaint);
    }

    private void drawCenterLine(Canvas canvas,int baseLineX,int baseLineY){
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
      //  canvas.drawLine(0f,centerY,width,centerY,mPaint);
       // canvas.drawLine(baseLineX,0f,baseLineX,height,mPaint);
        canvas.drawPoint(centerX,centerY,mPaint);
        mPaint.setStrokeWidth(2);
        drawArrow(canvas,centerX,centerY,150,"中心点",true);
    }

    private void drawBaseLineCircle(Canvas canvas,int baseLineX,int baseLineY){
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(3);
        float radius = 10;
        canvas.drawCircle(baseLineX,baseLineY,radius,mPaint);
    }

    private void drawArrow(Canvas canvas,int baseLineX,int baseLineY,int offset,String dec,boolean isCenter){
        int arrowOffset = offset;
        int smallLineOffset = 15;
        PointF arrowEnd = new PointF();
        if(isCenter)
         arrowEnd.x = baseLineX + arrowOffset;
        else
            arrowEnd.x = baseLineX - arrowOffset;
        arrowEnd.y = baseLineY + arrowOffset;
        canvas.drawLine(baseLineX,baseLineY,arrowEnd.x,arrowEnd.y,mPaint);

        PointF arrowRightLine = new PointF();
        arrowRightLine.x = arrowEnd.x;
        if(isCenter)
         arrowRightLine.y = arrowEnd.y - smallLineOffset;
        else
            arrowRightLine.y = arrowEnd.y - smallLineOffset;
        canvas.drawLine(arrowEnd.x,arrowEnd.y,arrowRightLine.x,arrowRightLine.y,mPaint);

        PointF arrowLeftLine = new PointF();
        if(isCenter)
         arrowLeftLine.x = arrowEnd.x - smallLineOffset;
        else
            arrowLeftLine.x = arrowEnd.x + smallLineOffset;
        arrowLeftLine.y = arrowEnd.y;
        canvas.drawLine(arrowEnd.x,arrowEnd.y,arrowLeftLine.x,arrowLeftLine.y,mPaint);

        drawDec(canvas,arrowEnd.x,arrowEnd.y,dec);
    }

    private void drawDec(Canvas canvas,float arrowEndX,float arrowEndY,String dec){
        int desTxtHeight = 30;

        mPaint.setTextSize(30);
        PointF decBaseLine = new PointF();
        decBaseLine.x = arrowEndX;
        decTxt = dec;
        float decCenterY = arrowEndY + desTxtHeight /2;
        decBaseLine.y = decCenterY + Tools.getBaseLine(mPaint);
        canvas.drawText(decTxt,decBaseLine.x,decBaseLine.y,mPaint);
    }


}
