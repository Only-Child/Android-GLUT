package com.example.wechat;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Administrator on 2016/2/18.
 */
public class Home_Fragment extends Fragment {


    private  ViewFlipper flipper;
    final int FLAG_MSG=0X001; //消息代码
    private Message message;    //发送消息对象
    private int[] images = new int[]{R.drawable.img1, R.drawable.img2, R.drawable.img3,
            R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8};  //定义图片数组
    private Animation[] animations=new Animation[2];  //定义两个切换动画动画
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_home__fragment,null);//获取视图
        return view;
    }
/********************重写onActivityCreated方法*************************/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        flipper = getActivity().findViewById(R.id.viewFlipper);
        for (int i=0;i<images.length;i++){    //遍历图片
         ImageView imageView=new ImageView(getContext());
         imageView.setImageResource(images[i]);
         flipper.addView(imageView);
        }


        /**************初始化动画*********************/
        animations[0]= AnimationUtils.loadAnimation(getContext(),R.anim.slide_in_right);  //右侧平移进入动画
        animations[1]=AnimationUtils.loadAnimation(getContext(),R.anim.slide_out_left);  //左侧平移退出动画
        flipper.setInAnimation(animations[0]);  //为flipper设置进入动画
        flipper.setOutAnimation(animations[1]);  //为flipper设置退出动画
       flipper.startFlipping();
    }

}
