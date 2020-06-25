package com.example.wechat.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.wechat.R;

public class MyImageView extends androidx.appcompat.widget.AppCompatImageView {

    //宽高比，由我们自己设定
    private float ratio;
    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyImageView);
        //根据属性名称获取对应的值，属性名称的格式为类名_属性名
        ratio = typedArray.getFloat(R.styleable.MyImageView_ratio, 0.0f);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取屏幕宽度
        WindowManager m = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        m.getDefaultDisplay().getMetrics(outMetrics);
        int width = (outMetrics.widthPixels -5*2)/2;


//        float scale = 16f / 9f;

        //获取宽度的模式和尺寸
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //获取高度的模式和尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //宽确定，高不确定
        if(widthMode == MeasureSpec.EXACTLY&&heightMode!=MeasureSpec.EXACTLY&&ratio!=0){
            heightSize = (int) (width*ratio+0.5f);//根据宽度和比例计算高度
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }else if(widthMode!=MeasureSpec.EXACTLY&&heightMode==MeasureSpec.EXACTLY&ratio!=0){
            width = (int) (heightSize/ratio+0.5f);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize,MeasureSpec.EXACTLY);
        }else{
            throw new RuntimeException("无法设定宽高比");
        }

        setMeasuredDimension(width,heightSize);
    }
    /**
     * 设置宽高比
     * @param ratio
     */
    public void setRatio(float ratio){
        this.ratio = ratio;
    }








}
