package com.aoyj.learn.canvas_master.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 针对画布的操作
 * 参考：
 * https://github.com/GcsSloop/AndroidNote/blob/master/CustomView/Advance/%5B03%5DCanvas_Convert.md
 * Created by aoyuanjie on 2018/7/24.
 * 合理的使用画布操作可以帮助我们使用更容易理解的方式创作我们想要的效果，这也是画布操作存在的原因。（应用以上博客参考）
 * ps:所有的画布操作只会影响后续的绘制，对之前已经绘制过的内容没有影响。
 */

public class CanvasOperationView extends View {
    private static final float OFFSET = 300;


    private static final int TRANSLATE_OPERATION_HEIGHT = 280;
    private static final String TRANSLATE_OPERATION_DEC = "画布平移操作：X轴平移(蓝色圆为正常坐标系，黑色圆为坐标系平移之后效果)";

    private static final int SCALE_OPERATION_HEIGHT = 680;
    private static final String SCALE_OPERATION_DEC = "画布缩放操作：以缩放中心缩小0.5倍,再以中心轴翻转";

    private static final int ROTATE_OPERATION_HEIGHT = 1080;
    private static final String ROTATE_OPERATION_DEC = "画布旋转操作：以圆心为中心点，每次旋转10度，总过旋转360度";

    private static final int SKEW_OPERATION_HEIGHT = 1480;
    private static final String SKEW_OPERATION_DEC = "蓝色矩形为正常坐标系效果，灰色四边形为坐标系错切之后的效果";

    private static final float DEC_TXT_SIZE = 30;
    private static final float DEFAULT_DESC_OFFSET = 140;

    private Paint mPaint;

    public CanvasOperationView(Context context) {
        this(context,null);
    }

    public CanvasOperationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CanvasOperationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(DEC_TXT_SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画布平移操作
        translateOperation(canvas);
        //画布缩放操作
        scaleOperation(canvas);
        //画布旋转操作
        rotateOperation(canvas);
        //错切操作
        skewOperation(canvas);
    }

    private void drawDecTxt(Canvas canvas,float txtCenterY,String dec){
        mPaint.setTextSize(DEC_TXT_SIZE);
        mPaint.setColor(Color.RED);
        PointF decBaseLine = new PointF();
        decBaseLine.x = OFFSET  + 250 - mPaint.measureText(dec)/2;
        decBaseLine.y = txtCenterY + Tools.getBaseLine(mPaint);
        canvas.drawText(dec,decBaseLine.x,decBaseLine.y,mPaint);
        mPaint.setColor(Color.BLUE);
    }

    private void drawDecTxt(Canvas canvas,float txtCenterY,float txtCenterX,String dec){
        mPaint.setTextSize(DEC_TXT_SIZE);
        mPaint.setColor(Color.RED);
        PointF decBaseLine = new PointF();
        decBaseLine.x = txtCenterX;
        decBaseLine.y = txtCenterY + Tools.getBaseLine(mPaint);
        canvas.drawText(dec,decBaseLine.x,decBaseLine.y,mPaint);
        mPaint.setColor(Color.BLUE);
    }

    /**
     * 画布位移操作，每次位移都是相对以当前canvas位置，而不是初始的坐标原点位置。
     * @param canvas 画布
     */
    private void translateOperation(Canvas canvas) {
        canvas.save();
        drawDecTxt(canvas,40,TRANSLATE_OPERATION_DEC);
        RectF rectF = new RectF();

        float width  =200;
        float height = 200;

        rectF.bottom = TRANSLATE_OPERATION_HEIGHT;
        rectF.left = OFFSET;
        rectF.top = rectF.bottom  - height;
        rectF.right = rectF.left + width;
        canvas.drawCircle((rectF.left +  rectF.right) /2,(rectF.bottom + rectF.top)/2,(rectF.bottom - rectF.top) /2,mPaint);
        canvas.translate(200,0);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle((rectF.left +  rectF.right) /2,(rectF.bottom + rectF.top)/2,(rectF.bottom - rectF.top) /2,mPaint);
        canvas.restore();
    }

    /**
     * 画布缩放操作
     *
     * @param canvas 画布
     */
    private void scaleOperation(Canvas canvas) {
        canvas.save();
        drawDecTxt(canvas,TRANSLATE_OPERATION_HEIGHT + DEFAULT_DESC_OFFSET,SCALE_OPERATION_DEC);
        RectF rectF = new RectF();

        float width  =200;
        float height = 200;

        rectF.bottom = SCALE_OPERATION_HEIGHT;
        rectF.left = OFFSET;
        rectF.top = rectF.bottom  - height;
        rectF.right = rectF.left + width;

        canvas.drawCircle((rectF.left +  rectF.right) /2,(rectF.bottom + rectF.top)/2,(rectF.bottom - rectF.top) /2,mPaint);
    /*    //设置缩放比例 和缩放中心点，若果不设置缩放中心点，当前的坐标原点就是缩放中心点。
        canvas.scale(0.5f,0.5f,(rectF.left +  rectF.right) /2 ,(rectF.bottom + rectF.top)/2);
        canvas.drawCircle((rectF.left +  rectF.right) /2,(rectF.bottom + rectF.top)/2,(rectF.bottom - rectF.top) /2,mPaint);
        canvas.restore();
        canvas.save();*/
        //缩放参数为负数含义是: 在缩放之后还要按照中心轴翻转，不指定缩放中心点，则默认的中心轴为X轴，Y轴

       /* float centerX = (rectF.left +  rectF.right) /2;
        float centerY = (rectF.bottom + rectF.top)/2;
        float radius = (rectF.bottom - rectF.top) /2;*/

        float centerX = rectF.right;
        float centerY = (rectF.bottom + rectF.top)/2;
        float radius = (rectF.bottom - rectF.top) /2;
        float centerLineY = centerY;

        Paint tempPaint  = new Paint();
        tempPaint.setAntiAlias(true);
        tempPaint.setColor(Color.RED);
        tempPaint.setTextSize(30f);
        tempPaint.setStrokeCap(Paint.Cap.ROUND);
        tempPaint.setStrokeWidth(10f);
        canvas.drawPoint(centerX,centerY,tempPaint);
        canvas.drawPoint(centerX + radius * 2 - 15,centerY - 20,tempPaint);
        tempPaint.setStrokeWidth(1f);
       // canvas.drawLine(0,centerLineY,540f,centerLineY,tempPaint);
        //canvas.drawLine(centerX,rectF.top - 30,centerX,getMeasuredHeight(),tempPaint);
        canvas.drawLine(centerX + radius * 2 - 15,centerY + 20,centerX + radius * 2 + 30 ,centerY + 20,tempPaint);
        drawDecTxt(canvas,centerY - 20,centerX + radius * 2,"缩放中心点");
        drawDecTxt(canvas,centerY + 20,centerX + radius * 2 + 40,"翻转中心轴");


        mPaint.setColor(Color.BLACK);
        canvas.scale(-0.5f,-0.5f,rectF.right,(rectF.bottom + rectF.top)/2);
        canvas.drawCircle((rectF.left +  rectF.right) /2,(rectF.bottom + rectF.top)/2,(rectF.bottom - rectF.top) /2,mPaint);
        canvas.restore();
    }

    /**
     * 画布旋转操作
     * @param canvas 画布
     */
    private void rotateOperation(Canvas canvas) {
        canvas.save();
        drawDecTxt(canvas,SCALE_OPERATION_HEIGHT + DEFAULT_DESC_OFFSET,ROTATE_OPERATION_DEC);
        RectF rectF = new RectF();

        float width  =200;
        float height = 200;

        rectF.bottom = ROTATE_OPERATION_HEIGHT;
        rectF.left = OFFSET;
        rectF.top = rectF.bottom  - height;
        rectF.right = rectF.left + width;
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle((rectF.left +  rectF.right) /2,(rectF.bottom + rectF.top)/2,(rectF.bottom - rectF.top) /2,mPaint);
        canvas.drawCircle((rectF.left +  rectF.right) /2,(rectF.bottom + rectF.top)/2,(rectF.bottom - rectF.top) /2 - 15,mPaint);
        String testStr = "1";
        for(int i = 0 ; i<= 360 ; i += 10){
            canvas.drawLine((rectF.left +  rectF.right) /2,(rectF.bottom + rectF.top)/2 -85,(rectF.left +  rectF.right) /2,(rectF.bottom + rectF.top)/2 -100,mPaint);
            mPaint.setTextSize(15);
            float testStrBaseLineX = (rectF.left +  rectF.right) /2 - mPaint.measureText(testStr);
            float testStrBaseLineY = (rectF.bottom + rectF.top)/2 - 70 + Tools.getBaseLine(mPaint);
            canvas.drawText(testStr,testStrBaseLineX,testStrBaseLineY,mPaint);
            canvas.rotate(10,(rectF.left +  rectF.right) /2,(rectF.bottom + rectF.top)/2);
        }
        canvas.restore();
    }

    /**
     * 错切操作
     * @param canvas 画布
     */
    private void skewOperation(Canvas canvas) {
        canvas.save();
        drawDecTxt(canvas,ROTATE_OPERATION_HEIGHT + DEFAULT_DESC_OFFSET,SKEW_OPERATION_DEC);
        RectF rectF = new RectF();

        float width  =200;
        float height = 200;

        rectF.bottom = SKEW_OPERATION_HEIGHT;
        rectF.left = OFFSET;
        rectF.top = rectF.bottom  - height;
        rectF.right = rectF.left + width;
        canvas.translate((rectF.left +  rectF.right) /2,(rectF.bottom + rectF.top)/2);
        RectF rect1 = new RectF(-100,-100,100,100);   // 矩形区域
        canvas.drawRect(rect1,mPaint);
        canvas.skew(1,0);
        mPaint.setColor(Color.BLACK);

        RectF rect = new RectF(-100,-100,100,100);   // 矩形区域
        canvas.drawRect(rect,mPaint);
    }
}
