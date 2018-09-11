package com.aoyj.learn.canvas_master.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * path boolean 操作
 * Created by aoyuanjie on 2018/8/2.
 */

public class PathBooleanOpreatorView extends View {
    private static final String ORIGINAL_DESC = "无布尔运算的原始的path1和path2";
    private static final String DIFFERENCE_DESC = "差集：path1减去path2之后的部分";
    private static final String REVERSE_DIFFERENCE_DESC = "差集：path2减去path1之后的地方";
    private static final String INTERSECT_DESC = "交集：path1与path2相交的地方";
    private static final String UNION_DESC = "并集：包含path1与path2的地方";
    private static final String XOR_DESC = "异或：包含path1与path2，但是不包含两者交集的地方";

    private static final String[] descS = {DIFFERENCE_DESC,
                                          INTERSECT_DESC,
                                          UNION_DESC,
                                          XOR_DESC,
                                          REVERSE_DIFFERENCE_DESC};

    private static final int DEFAULT_SIZE = 150;
    private static final int DEFAULT_LEFT_PADDING = 50;
    private static final int DEFAULT_SECOND_PATH_OFFSET = 40;

    private Paint mBluePaint,mRedPaint;
    private Path mPath1,mPath2,mPath3;
    private int mWidth,mHeight;
    private float periodHeight;

    public PathBooleanOpreatorView(Context context) {
        this(context,null);
    }

    public PathBooleanOpreatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathBooleanOpreatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBluePaint = new Paint();
        mRedPaint = new Paint();
        mBluePaint.setColor(Color.BLUE);
        mRedPaint.setColor(Color.RED);
        mBluePaint.setAntiAlias(true);
        mRedPaint.setAntiAlias(true);
        mBluePaint.setStyle(Paint.Style.FILL);
        mRedPaint.setStyle(Paint.Style.STROKE);
        mRedPaint.setTextSize(30);

        mPath1 = new Path();
        mPath2 = new Path();
        mPath3 = new Path();
        mPath3.setFillType(Path.FillType.EVEN_ODD);
    }

    private void setLocationData(int width,int height){
        mWidth = width;
        mHeight = height;

        periodHeight = mHeight / 6f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLocationData(getMeasuredWidth(),getMeasuredHeight());
        mPath1.addCircle(DEFAULT_LEFT_PADDING + DEFAULT_SIZE /2,periodHeight/2,DEFAULT_SIZE /2, Path.Direction.CW);
        //将Path1设置X轴 和Y轴偏移量  将偏移过后的Path赋值给path2,path1还将保留原来的状态
        mPath1.offset(DEFAULT_SECOND_PATH_OFFSET,0,mPath2);
        canvas.drawPath(mPath1,mRedPaint);
        canvas.drawPath(mPath2,mRedPaint);
        drawDesc(canvas,ORIGINAL_DESC,DEFAULT_LEFT_PADDING + 2* DEFAULT_SIZE, (int) (periodHeight/2));
        for(int i = 0; i < descS.length; i++){
            canvas.translate(0,periodHeight);
            pathBooleanOperation(canvas,Path.Op.values()[i],descS[i]);
        }
    }

    private void pathBooleanOperation(Canvas canvas,Path.Op op,String desc){
        mPath3.reset();
        mPath3.op(mPath1,mPath2,op);
        canvas.drawPath(mPath3,mBluePaint);
        drawDesc(canvas,desc,DEFAULT_LEFT_PADDING + 2*DEFAULT_SIZE, (int) (periodHeight/2));
    }

    private void drawDesc(Canvas canvas,String desc,int baselineLeftX,int centerY){
        int baseLineY = (int) (centerY + Tools.getBaseLine(mRedPaint));
        canvas.drawText(desc,baselineLeftX,baseLineY,mRedPaint);
    }
}
