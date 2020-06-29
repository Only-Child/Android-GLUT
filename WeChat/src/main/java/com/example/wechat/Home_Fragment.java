package com.example.wechat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.wechat.info.Info_1Activity;
import com.example.wechat.info.Info_2Activity;
import com.example.wechat.info.Info_3Activity;
import com.example.wechat.info.Info_4Activity;
import com.example.wechat.info.Info_5Activity;
import com.example.wechat.utils.HorAdapter;
import com.example.wechat.utils.StaggeredGridAdapter;
import com.example.wechat.utils.UiUtils;



/**
 * Created by Administrator on 2016/2/18.
 */
public class Home_Fragment extends Fragment {
    private ViewFlipper flipper;
    private int[] images = new int[]{R.drawable.img1, R.drawable.img2, R.drawable.img3,
            R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8};  //定义图片数组
    private Animation[] animations = new Animation[2];  //定义两个切换动画动画

    private RecyclerView minfo, msort;
    private  EditText tvSearch;
    private LinearLayout llSearch;
    private Toolbar toolbar;
    boolean isExpand = false;
    private TransitionSet mSet;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home__fragment, null);//获取视图
        tvSearch=view.findViewById(R.id.tv_search);
        llSearch=view.findViewById(R.id.ll_search);
        toolbar=view.findViewById(R.id.toolbar);

//        设置全屏透明状态栏

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //设置toolbar初始透明度为0
        toolbar.getBackground().mutate().setAlpha(0);
        //搜索框点击事件
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isExpand) {
                    expand();
                    isExpand = true;
                } else if (isExpand) {
                    reduce();
                    isExpand = false;
                }

            }
        });

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isExpand) {
                    expand();
                    isExpand = true;
                } else if (isExpand) {
                    reduce();
                    isExpand = false;
                }

            }
        });





        minfo=view.findViewById(R.id.pu);
        msort=view.findViewById(R.id.grd);
        //声明布局启动器
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        //设置布局启动器
        msort.setLayoutManager(linearLayoutManager);
        //添加装饰--设置四周间隔
        msort.addItemDecoration(new MyDecoration2());
        //设置适配器
        msort.setAdapter(new HorAdapter(getActivity(), new HorAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(getActivity(),"click..."+pos,Toast.LENGTH_SHORT).show();
            }
        }));

        //设置布局启动器
        minfo.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //添加装饰--设置四周间隔
        minfo.addItemDecoration(new MyDecoration());
        //设置适配器
        minfo.setAdapter(new StaggeredGridAdapter(getActivity(), new StaggeredGridAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(getActivity(),"click..."+pos,Toast.LENGTH_SHORT).show();
                Intent intent=null;
                switch (pos){
                    case 0:
                        intent=new Intent(getActivity(), Info_1Activity.class);
                        break;
                    case 1:
                        intent=new Intent(getActivity(), Info_2Activity.class);
                        break;
                    case 2:
                        intent=new Intent(getActivity(), Info_3Activity.class);
                        break;

                    case 3:
                        intent=new Intent(getActivity(), Info_4Activity.class);
                        break;

                    case 4:
                        intent=new Intent(getActivity(), Info_5Activity.class);
                        break;
                    default :
                        break;
                }if(intent!=null) {
                    startActivity(intent);
                }
            }
        }));

        return view;
    }

    //自定义装饰1
    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int gap=getResources().getDimensionPixelSize(R.dimen.dividerHeight2);
            int gap1=getResources().getDimensionPixelSize(R.dimen.dividerHeight3);
            outRect.set(gap,gap,gap,gap);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        flipper = getActivity().findViewById(R.id.flipper);
        for (int i=0;i<images.length;i++){    //遍历图片
            ImageView imageView=new ImageView(getContext());
            imageView.setImageResource(images[i]);
            flipper.addView(imageView);
        }
        flipper.setMinimumWidth(UiUtils.getScreenWidth(getActivity()));

        /**************初始化动画*********************/
        animations[0]= AnimationUtils.loadAnimation(getContext(),R.anim.slide_in_right);  //右侧平移进入动画
        animations[1]=AnimationUtils.loadAnimation(getContext(),R.anim.slide_out_left);  //左侧平移退出动画
        flipper.setInAnimation(animations[0]);  //为flipper设置进入动画
        flipper.setOutAnimation(animations[1]);  //为flipper设置退出动画
        flipper.startFlipping();
    }
    //自定义装饰2
    class MyDecoration2 extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int gap1=getResources().getDimensionPixelSize(R.dimen.dividerHeight4);
            outRect.set(0,0,gap1,0);
        }
    }
    private void reduce() {
        //设置收缩状态时的布局
        tvSearch.setText("搜索");
        tvSearch.setHint("");
        RelativeLayout.LayoutParams LayoutParams = (RelativeLayout.LayoutParams) llSearch.getLayoutParams();
        LayoutParams.width = dip2px(80);
        LayoutParams.setMargins(dip2px(10), dip2px(10), dip2px(10), dip2px(10));
        llSearch.setLayoutParams(LayoutParams);
        //开始动画
        beginDelayedTransition(llSearch);
    }

    private void beginDelayedTransition(ViewGroup view) {
        mSet = new AutoTransition();
        mSet.setDuration(300);
        TransitionManager.beginDelayedTransition(view, mSet);
    }

    private int dip2px(float dpVale) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpVale * scale + 0.5f);
    }

    private void expand() {
        //设置伸展状态时的布局
        tvSearch.setText("");
        tvSearch.setHint("搜索你想搜索的内容");
        RelativeLayout.LayoutParams LayoutParams = (RelativeLayout.LayoutParams) llSearch.getLayoutParams();
        LayoutParams.width = LayoutParams.MATCH_PARENT;
        LayoutParams.setMargins(dip2px(10), dip2px(10), dip2px(10), dip2px(10));
        llSearch.setLayoutParams(LayoutParams);
        //开始动画
        beginDelayedTransition(llSearch);
    }
}
