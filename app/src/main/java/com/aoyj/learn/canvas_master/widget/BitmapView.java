package com.aoyj.learn.canvas_master.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.aoyj.learn.canvas_master.R;

/**
 * Created by aoyuanjie on 2018/7/24.
 */

public class BitmapView extends View {
    private Context mContext;
    private float width,height;

    private Paint mPaint;
    private Bitmap mBitmap;

    private int currentPage = -1;
    private int totalPage = 13;
    private int animDuration = 500;

    private Handler mHandler;



    public BitmapView(Context context) {
        this(context,null);
    }

    public BitmapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BitmapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;

        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.checkmark);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getMeasuredWidth();
        height = getMeasuredHeight();

        canvas.drawCircle(width/2,height/2,240,mPaint);

        int sideLength = mBitmap.getHeight();

        //以图像本身为参考坐标系
        Rect src = new Rect(sideLength * currentPage,0,sideLength * (currentPage +1),sideLength);

        //以当前View为坐标参考系
        Rect dst = new Rect(-200,-200,200,200);
        canvas.drawBitmap(mBitmap,src,dst,mPaint);
    }
}
