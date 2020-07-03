package com.example.wechat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.wechat.info.Info_1Activity;
import com.example.wechat.pojo.Goods;
import com.example.wechat.utils.DataBaseOperate;
import com.example.wechat.utils.HorAdapter;
import com.example.wechat.utils.StaggeredGridAdapter;
import com.example.wechat.utils.UiUtils;
/**
 * Created by Administrator on 2016/2/18.
 */
public class Home_Fragment extends Fragment {
    //左右滑动时手指松开的X坐标
    private float touchUpX;
    //左右滑动时手指按下的X坐标
    private float touchDownX;
    //左右滑动时手指松开的X坐标
    final int FLAG_MSG=0x001;     //消息代码
    private Message message;    //发送的消息对象
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
    private ScrollView mscrollView;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home__fragment, null);//获取视图
        tvSearch=view.findViewById(R.id.tv_search);
        llSearch=view.findViewById(R.id.ll_search);
        toolbar=view.findViewById(R.id.toolbar);
        mscrollView=view.findViewById(R.id.msceollview);
        imageView=view.findViewById(R.id.iv_search);
        minfo=view.findViewById(R.id.pu);
        msort=view.findViewById(R.id.grd);



            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    //得到搜索结果
                    String result = String.valueOf(tvSearch.getText());
                    //跳到结果页面
                    Bundle bundle = new Bundle();
                    bundle.putString("result", result);
                    intent = new Intent(getActivity(), SearchResultActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });

        //scrollview滚动状态监听
        mscrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                //改变toolbar的透明度
                changeToolbarAlpha();
                //滚动距离>=大图高度-toolbar高度 即toolbar完全盖住大图的时候 且不是伸展状态 进行伸展操作
                if (mscrollView.getScrollY() >=flipper.getHeight() - toolbar.getHeight() && !isExpand) {
                    expand();
                    isExpand = true;
                }
                //滚动距离<=0时 即滚动到顶部时 且当前伸展状态 进行收缩操作
                else if (mscrollView.getScrollY()<=0&& isExpand) {
                    reduce();
                    isExpand = false;
                }
            }
        });


         //设置全屏透明状态栏
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
                //将选中的分类位置信息传递过去，0服装，1家电，2食品
                Bundle bundle=new Bundle();
                bundle.putInt("pos",pos);
                Intent intent=new Intent(getActivity(),SortResultActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
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

                //从数据库中获取商品数据
                SQLiteDatabase database = DataBaseOperate.create(getContext());
                //设置搜索结果
                //查询商品并保存
                Cursor cursor = database.rawQuery("select * from goods where id =?",new String[]{String.valueOf(pos+1)});
                cursor.moveToFirst();
                Goods goods1=new Goods();
                goods1.setName(cursor.getString(cursor.getColumnIndex("name")));
                goods1.setCategory(cursor.getString(cursor.getColumnIndex("category")));
                goods1.setPrice( cursor.getDouble(cursor.getColumnIndex("price")));
                goods1.setSrc(cursor.getString(cursor.getColumnIndex("src")));
                goods1.setStorage(cursor.getInt(cursor.getColumnIndex("storage")));
                cursor.close();
                database.close();
                //将商品信息传到详情页
                Intent intent=null;
                Bundle bundle=new Bundle();
                bundle.putString("info",goods1.getName());
                intent=new Intent(getActivity(), Info_1Activity.class);
                intent.putExtras(bundle);
                startActivity(intent);

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
//        flipper.startFlipping();
        message=Message.obtain();      //获取Message对象
        message.what=FLAG_MSG;        //设置消息代码
        handler.sendMessage(message);  //发送消息
        /**********拖动广告****************/
        flipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //取得左右滑动时手指按下的X坐标
                    touchDownX = event.getX();
                    return true;
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //取得左右滑动时手指松开的X坐标
                    touchUpX = event.getX();
                    //从左往右，看下一张
                    if (touchUpX - touchDownX > 100) {

                        //设置图片切换的动画
                        flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left));
                        flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right));
                        //设置当前要看的图片
                        flipper.showPrevious();
                        //从右往左，看上一张
                    } else if (touchDownX - touchUpX > 100) {

                        //设置切换动画
                        flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_left));
                        flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right));
                        //设置要看的图片
                        flipper.showNext();
                    }
                    return true;
                }

                return false;
            }
        });

       /**************拖动广告结束**********************/
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

    private void changeToolbarAlpha() {
        int scrollY = mscrollView.getScrollY();
        //快速下拉会引起瞬间scrollY<0
        if(scrollY<0){
            toolbar.getBackground().mutate().setAlpha(0);
            return;
        }
        //计算当前透明度比率
        float radio= Math.min(1,scrollY/(flipper.getHeight()-toolbar.getHeight()*1f));
        //设置透明度
        toolbar.getBackground().mutate().setAlpha( (int)(radio * 0xFF));
    }

    /******************创建Handler实现3秒更新一次图片**************/
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==FLAG_MSG){
                flipper.showPrevious();  //切换到下一张图片
                message=handler.obtainMessage(FLAG_MSG); //获取Message
                handler.sendMessageDelayed(message,3000);  //延迟3秒发送消息
            }
        }
    };
}
