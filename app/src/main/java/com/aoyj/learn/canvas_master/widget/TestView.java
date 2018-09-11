package com.aoyj.learn.canvas_master.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.aoyj.learn.canvas_master.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by aoyuanjie on 2018/7/26.
 */

public class TestView extends View {
    private static String TAG = TestView.class.getSimpleName();

    private static final int TOTAL_PROGRESS = 100;
    private static final String WHITE_COLOR = "#FFFDE399";
    private static final String ORANGE_COLOR = "#FFFFA800";
    private static int LEFT_MARGIN = 9;
    private static int RIGHT_MARGIN = 25;


    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        postInvalidate();
    }

    //当前的进度
    private int currentProgress = 0;
    //当前进度所对应的长度
    private int currentProgressWidth;
    //进度条对应的总长度
    private int totalProgressWidth;

    //当前View的大小
    private int mWidth,mHeight;
    //图片画笔，白色矩形画笔，橘色画笔
    private Paint mBitmapPaint,mWhitePaint,mOrangePaint;
    //白色矩形，橘色矩形，橘色圆弧所对应的矩形
    private RectF mWhiteRectF,mOrangeRect,mArcRectF,mInitWhiteRect;

    private Resources mResources;
    private int mLeftMargin,mRightMargin;

    private Bitmap bgBitmap;
    private int bgImgWidth,bgImgHeight;
    private Rect mBgSrcRect,mBgDestRect;

    private int mArcRadius;
    private int mArcRightLocation;

    //叶子相关
    //叶子飘动的周期
    private static final int LEAF_FLOAT_PERIOD = 500;
    //叶子旋转的周期
    private static final int LEAF_ROTATE_PERIOD = 2000;

    //正常振幅
    private static final int MIDDLE_AMPLITUDE = 20;
    //振幅差
    private static final int AMPLITUDE_DISPARITY = 15;
    private long mLeafFloatPeriod = LEAF_FLOAT_PERIOD;
    private long mLeafRoratePeriod = LEAF_ROTATE_PERIOD;
    private int mNormalAmplitude = MIDDLE_AMPLITUDE;
    private int mAmplitudeDisparity = AMPLITUDE_DISPARITY;
    private Bitmap mLeafBitmap;
    private int mLeafImgWidth,mLeafImgHeight;
    private long mAddTime;


    private LeafFactory leafFactory;
    private List<LeafInfo> mLeafs;

    public TestView(Context context) {
        this(context,null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mResources = getResources();
        mLeftMargin = Tools.dip2Px(context,LEFT_MARGIN);
        mRightMargin = Tools.dip2Px(context,RIGHT_MARGIN);
        
        initBitmap();
        initPaint();
        initLeafInfo();
    }

    private void initPaint() {
        mBitmapPaint = new Paint();
        //设置图片抗锯齿，是边缘模糊化
        mBitmapPaint.setAntiAlias(true);
        //设置图片防抖动，使其显示更柔和
        mBitmapPaint.setDither(true);

        mWhitePaint = new Paint();
        mWhitePaint.setColor(Color.parseColor(WHITE_COLOR));
        mWhitePaint.setStyle(Paint.Style.FILL);
        mWhitePaint.setAntiAlias(true);

        mOrangePaint = new Paint();
        mOrangePaint.setAntiAlias(true);
        mOrangePaint.setStyle(Paint.Style.FILL);
        mOrangePaint.setColor(Color.parseColor(ORANGE_COLOR));
    }

    private void initBitmap() {
        bgBitmap = ((BitmapDrawable)(mResources.getDrawable(R.mipmap.leaf_kuang))).getBitmap();
        bgImgWidth = bgBitmap.getWidth();
        bgImgHeight = bgBitmap.getHeight();

        mLeafBitmap = ((BitmapDrawable)(mResources.getDrawable(R.mipmap.leaf,null))).getBitmap();
        mLeafImgWidth = mLeafBitmap.getWidth();
        mLeafImgHeight = mLeafBitmap.getHeight();
    }

    private void initLeafInfo() {
        leafFactory = new LeafFactory();
        mLeafs = leafFactory.generateLeafs();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    private void initLocation(int w,int h){
        mWidth = w;
        mHeight = h;
        totalProgressWidth = mWidth - mLeftMargin - mRightMargin;

        mArcRadius = (mHeight - 2 * mLeftMargin) /2;

        mBgSrcRect = new Rect(0,0,bgImgWidth,bgImgHeight);

        mBgDestRect = new Rect(0,0,mWidth,mHeight);

        mWhiteRectF = new RectF(mLeftMargin + currentProgressWidth,mLeftMargin,mWidth - mRightMargin,mHeight - mLeftMargin);

        mOrangeRect = new RectF(mLeftMargin + mArcRadius , mLeftMargin,currentProgressWidth,mHeight - mLeftMargin);
        mArcRectF = new RectF(mLeftMargin,mLeftMargin,mLeftMargin + 2 * mArcRadius,mHeight - mLeftMargin);

        mArcRightLocation = mLeftMargin + mArcRadius;
        mInitWhiteRect = new RectF(mArcRightLocation,mLeftMargin,mWidth - mRightMargin, mHeight - mLeftMargin);

        Log.i(TAG,"totalProgressWidth:" + totalProgressWidth + " mArcRadius: " + mArcRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initLocation(getMeasuredWidth(),getMeasuredHeight());
        drawProgress(canvas);

        //绘制背景图
        canvas.drawBitmap(bgBitmap,mBgSrcRect,mBgDestRect,mBitmapPaint);
        postInvalidate();

    }

    private void drawProgress(Canvas canvas) {
        if (currentProgress >= TOTAL_PROGRESS)
            currentProgress = 0;

        currentProgressWidth = totalProgressWidth * currentProgress / TOTAL_PROGRESS;
        if (currentProgressWidth < mArcRadius) {
            Log.i(TAG,"the currentProgress in left half circle");
            Log.i(TAG, "currentProgressWidth:" + currentProgressWidth + " currentProgress :" + currentProgress);

            //绘制左边白色的半圆
            canvas.drawArc(mArcRectF, 90, 180, false, mWhitePaint);
            //绘制初始的白色矩形
            canvas.drawRect(mInitWhiteRect,mWhitePaint);
            drawLeafs(canvas);
            //绘制橘色的 圆弧
            int angle = (int)Math.toDegrees(Math.acos((mArcRadius - currentProgressWidth) / (float) mArcRadius));

            //圆弧的起始位置
            int startAngle = 180 - angle;
            //扫描过的角度
            int sweepAngle = 2 * angle;
            Log.i(TAG,"startAngel :" + startAngle + "  sweepAngle:" + sweepAngle);
            canvas.drawArc(mArcRectF,startAngle,sweepAngle,false,mOrangePaint);
        }else{
            Log.i(TAG,"the currentProgress out left half circle");
            Log.i(TAG, "currentProgressWidth:" + currentProgressWidth + " currentProgress :" + currentProgress);

            //绘制白色矩形
            canvas.drawRect(mInitWhiteRect,mWhitePaint);

            //绘制橘色 半圆
            drawLeafs(canvas);
            canvas.drawArc(mArcRectF,90,180,false,mOrangePaint);
            //绘制橘色矩形
            mOrangeRect.left = mArcRightLocation;
            mOrangeRect.right = currentProgressWidth;
            canvas.drawRect(mOrangeRect,mOrangePaint);
        }
    }

    private void drawLeafs(Canvas canvas){
        long currentTime = System.currentTimeMillis();
        for(LeafInfo leafInfo : mLeafs){
            if(currentTime > leafInfo.startTime && leafInfo.startTime != 0){
                getLeafCurrentLocation(leafInfo,currentTime);
                canvas.save();
                Matrix matrix = new Matrix();
                float transX = mLeftMargin + leafInfo.x;
                float transY = mLeftMargin + leafInfo.y;
                matrix.postTranslate(transX,transY);
                float rotateFraction = ((currentTime - leafInfo.startTime) % mLeafRoratePeriod) / (float)mLeafRoratePeriod;
                int angle = (int) (rotateFraction * 360);
                int rotate = leafInfo.rotateDirection == 0 ? angle + leafInfo.rotateAngle : -angle + leafInfo.rotateAngle;
                Log.i(TAG,"rotateFraction:" + rotateFraction);
                matrix.postRotate(rotate,transX + mLeafImgWidth / 2,transY + mLeafImgHeight / 2);
                matrix.postScale(2f,2f,transX + mLeafImgWidth / 2,transY + mLeafImgHeight / 2);
                canvas.drawBitmap(mLeafBitmap,matrix,mBitmapPaint);
                canvas.restore();
            }
        }

    }

    /**
     * 获取当前叶子的位置
     * @param leafInfo 叶子信息
     * @param currentTime 时间
     */
    private void getLeafCurrentLocation(LeafInfo leafInfo,long currentTime){
        long intervalTime = currentTime - leafInfo.startTime;
        if(intervalTime > mLeafRoratePeriod){
            leafInfo.startTime = System.currentTimeMillis() + new Random().nextInt((int) mLeafFloatPeriod);
        }

        float fraction = (float) intervalTime / mLeafRoratePeriod;
        leafInfo.x = totalProgressWidth * (1f - fraction);
        leafInfo.y = getLeafLocationYByX(leafInfo);
        Log.i(TAG,"leaf.X = " + leafInfo.x + "  leaf.Y= " + leafInfo.y);
    }

    /**
     * 根据叶子X的坐标确定Y的坐标
     * 根据叶子的运动轨迹可以决定 Y = A*sin(w*x) + b;
     * w与运动周期T的关系为： T = 2π/w
     * A为振幅
     * @param leafInfo
     */
    private int getLeafLocationYByX(LeafInfo leafInfo){
        float w = (float) (2 * Math.PI / totalProgressWidth);
        float a = mNormalAmplitude;
        switch (leafInfo.type){
            case LITTLE:
                a -= mAmplitudeDisparity;
                break;
            case BIG:
                a += mAmplitudeDisparity;
                break;
        }
        return (int) (a * Math.sin(w * leafInfo.x) + mArcRadius * 2 /3);
    }


    private enum StartType{
        LITTLE(0),MIDDLE(1),BIG(2);
        public int value;

        StartType(int value) {
            this.value = value;
        }

        public static StartType getType(int value){
            StartType type = null;
            StartType[] startTypes = StartType.values();
            for(StartType tempType : startTypes){
                if(tempType.value == value){
                    type = tempType;
                    break;
                }
            }
            return type;
        }
    }

    private class LeafInfo{
        //叶子图片绘制的位置
        float x,y;
        //叶子的振幅
        StartType type;
        //旋转的角度
        int rotateAngle;
        //旋转的方向 0代表顺时针，1代表逆时针；
        int rotateDirection;
        //起始时间
        long startTime;
    }

    private class LeafFactory{
        //一次最多产生的叶子的数量
        private static final int MAX_LEAFS = 8;
        Random random = new Random();


        private LeafInfo generateLeaf(){
            LeafInfo leafInfo = new LeafInfo();
            int randomType = random.nextInt(3);
            leafInfo.type = StartType.getType(randomType);
            leafInfo.rotateAngle = random.nextInt(360);
            leafInfo.rotateDirection = random.nextInt(2);
            mAddTime += random.nextInt((int) (mLeafFloatPeriod * 2));
            leafInfo.startTime = System.currentTimeMillis() + mAddTime;
            return leafInfo;
        }

        private List<LeafInfo> generateLeafs(){return generateLeafs(MAX_LEAFS);}

        private List<LeafInfo> generateLeafs(int size){
            List<LeafInfo> mList = new ArrayList<>();
            for(int i = 0; i < size; i++)
                mList.add(generateLeaf());
            return mList;
        }
    }
}
