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
 * Created by aoyuanjie on 2018/7/23.
 */

public class BaseGraphView extends View {
    private static final float OFFSET = 400;
    private static final float DEC_TXT_SIZE = 40;

    private static final float DRAW_POINT_HEIGHT = 100;
    private static final String DRAW_PONT_DEC = "点：";

    private static final float DRAW_LINE_HEIGHT = 180;
    private static final String DRAW_LINE_DEC = "线：";

    private static final float DRAW_RECT_HEIGHT = 430;
    private static final String DRAW_RECT_DEC = "矩形：";

    private static final float DRAW_ROUND_RECT_HEIGHT = 680;
    private static final String DRAW_ROUND_RECT_DEC = "圆角矩形：";

    private static final float DRAW_OVAL_HEIGHT = 930;
    private static final String DRAW_OVAL_DEC = "椭圆：";

    private static final float DRAW_CIRCLE_HEIGHT = 1180;
    private static final String DRAW_CIRCLE_DEC = "圆：";

    private static final float DRAW_ANGLE_HEIGHT = 1430;
    private static final String DRAW_ANGLE_DEC = "圆弧：";



    Paint mPaint;
    int width,height;

    private Paint.Cap mCap = Paint.Cap.values()[1];

    public BaseGraphView(Context context) {
        this(context,null);
    }

    public BaseGraphView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint(){
        mPaint = new Paint();
        //设置画笔颜色
        mPaint.setColor(Color.RED);
        //设置画笔为填充模式
        mPaint.setStyle(Paint.Style.FILL);
        //设置画笔的宽度
        mPaint.setStrokeWidth(5f);
        mPaint.setTextSize(DEC_TXT_SIZE);
    }

    /**
     * canvas是以当前布局的坐上角为坐标原点
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        //绘制点
        drawPoint(canvas);
        //绘制线
        drawLine(canvas);
        //绘制矩形
        drawRect(canvas);
        //绘制圆角矩形
        drawRoundRect(canvas);
        //绘制椭圆
        drawOval(canvas);
        //绘制圆
        drawCircle(canvas);
        //绘制圆弧
        drawAngle(canvas);
    }

    private void drawDecTxt(Canvas canvas,float txtCenterY,String dec){
        mPaint.setColor(Color.RED);
        txtCenterY = txtCenterY + 20;
        PointF decBaseLine = new PointF();
        decBaseLine.x = OFFSET;
        decBaseLine.y = txtCenterY + Tools.getBaseLine(mPaint);
        canvas.drawText(dec,decBaseLine.x,decBaseLine.y,mPaint);

        mPaint.setColor(Color.BLUE);
    }

    /**
     * 绘制点
     */
    private void drawPoint(Canvas canvas) {
        drawDecTxt(canvas,20,DRAW_PONT_DEC);

        float pointOffset = 20;
        float[] points = {
                OFFSET, DRAW_POINT_HEIGHT ,
                OFFSET + pointOffset,DRAW_POINT_HEIGHT,
                OFFSET + 2*pointOffset,DRAW_POINT_HEIGHT,
                OFFSET + 3*pointOffset,DRAW_POINT_HEIGHT,
                OFFSET + 4*pointOffset,DRAW_POINT_HEIGHT
        };
        // 绘制多个点
        canvas.drawPoints(points,mPaint);
        // 绘制一个点
        canvas.drawPoint(OFFSET + 5*pointOffset,DRAW_POINT_HEIGHT,mPaint);

    }

    /**
     * 绘制线
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        float decTxtCenterY = DRAW_POINT_HEIGHT + 20;
        drawDecTxt(canvas,decTxtCenterY,DRAW_LINE_DEC);

        float lineWidth = 100;
        canvas.drawLine(OFFSET,DRAW_LINE_HEIGHT,OFFSET + lineWidth,DRAW_LINE_HEIGHT,mPaint);
    }

    /**
     * 绘制矩形
     * @param canvas
     */
    private void drawRect(Canvas canvas){
        float decTxtCenterY = DRAW_LINE_HEIGHT + 20;
        drawDecTxt(canvas,decTxtCenterY,DRAW_RECT_DEC);

        float rectWidth = 200;
        float rectHeight = 160;

        RectF rect = new RectF();
        rect.bottom = DRAW_RECT_HEIGHT;
        rect.top = rect.bottom - rectHeight;
        rect.left = OFFSET;
        rect.right = rect.left + rectWidth;
        canvas.drawRect(rect,mPaint);
    }

    /**
     * 绘制圆角矩形
     * @param canvas
     */
    private void drawRoundRect(Canvas canvas){
        float decTxtCenterY = DRAW_RECT_HEIGHT + 20;
        drawDecTxt(canvas,decTxtCenterY,DRAW_ROUND_RECT_DEC);

        float rectWidth = 200;
        float rectHeight = 160;
        float rx = 8;
        float ry = 10;

        RectF rect = new RectF();
        rect.bottom = DRAW_ROUND_RECT_HEIGHT;
        rect.top = rect.bottom - rectHeight;
        rect.left = OFFSET;
        rect.right = rect.left + rectWidth;
        canvas.drawRoundRect(rect,rx,ry,mPaint);
    }

    /**
     * 绘制椭圆
     * @param canvas
     */
    private void drawOval(Canvas canvas){
        float decTxtCenterY = DRAW_ROUND_RECT_HEIGHT + 20;
        drawDecTxt(canvas,decTxtCenterY,DRAW_OVAL_DEC);

        float rectWidth = 200;
        float rectHeight = 160;

        RectF rect = new RectF();
        rect.bottom = DRAW_OVAL_HEIGHT;
        rect.top = rect.bottom - rectHeight;
        rect.left = OFFSET;
        rect.right = rect.left + rectWidth;
        canvas.drawOval(rect,mPaint);
    }

    /**
     * 绘制圆
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        float decTxtCenterY = DRAW_OVAL_HEIGHT + 20;
        drawDecTxt(canvas,decTxtCenterY,DRAW_CIRCLE_DEC);

        float rectWidth = 200;
        float rectHeight = 200;

        RectF rect = new RectF();
        rect.bottom = DRAW_CIRCLE_HEIGHT;
        rect.top = rect.bottom - rectHeight;
        rect.left = OFFSET;
        rect.right = rect.left + rectWidth;
        canvas.drawCircle((rect.left + rect.right) /2,(rect.top + rect.bottom) /2,(rect.bottom - rect.top) /2,mPaint);
    }

    /**
     * 绘制圆弧
     * @param canvas
     */
    private void drawAngle(Canvas canvas) {
        float decTxtCenterY = DRAW_CIRCLE_HEIGHT + 20;
        drawDecTxt(canvas,decTxtCenterY,DRAW_ANGLE_DEC);

        float rectWidth = 200;
        float rectHeight = 180;

        RectF rect = new RectF();
        rect.bottom = DRAW_ANGLE_HEIGHT;
        rect.top = rect.bottom - rectHeight;
        rect.left = OFFSET;
        rect.right = rect.left + rectWidth;

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        //useCenter:true
        canvas.drawArc(rect,-180,120f,true,mPaint);

        RectF rect1 = new RectF();
        rect1.bottom = DRAW_ANGLE_HEIGHT;
        rect1.top = rect1.bottom - rectHeight;
        rect1.left = rect.right + 50 ;
        rect1.right = rect1.left + rectWidth;

        mPaint.setStrokeCap(mCap);
        mPaint.setAntiAlias(true);
        //设置圆形盖帽
        mPaint.setStyle(Paint.Style.STROKE);
        //useCenter:true
        canvas.drawArc(rect1,-90,80f,false,mPaint);
    }
}
