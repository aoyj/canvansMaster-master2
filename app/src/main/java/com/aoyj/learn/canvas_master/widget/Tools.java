package com.aoyj.learn.canvas_master.widget;

import android.content.Context;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by aoyuanjie on 2018/7/23.
 */

public class Tools {


    /**
     * 获取BaseLine 与Text中轴线的距离
     * @param paint
     * @return
     */
    public static float getBaseLine(Paint paint){
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (fontMetrics.descent - fontMetrics.ascent)/2 - fontMetrics.descent;
    }

    public static int dip2Px(Context context, int dip){
        return (int)(dip * getScreenDensity(context));
    }

    public static float getScreenDensity(Context context){
        try{
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            return dm.density;
        }catch (Exception e){
            return DisplayMetrics.DENSITY_DEFAULT;
        }
    }

}
