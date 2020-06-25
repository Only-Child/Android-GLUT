package com.example.wechat;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.wechat.info.Info_1Activity;
import com.example.wechat.info.Info_2Activity;
import com.example.wechat.info.Info_3Activity;
import com.example.wechat.info.Info_4Activity;
import com.example.wechat.info.Info_5Activity;
import com.example.wechat.utils.StaggeredGridAdapter;

/**
 * Created by Administrator on 2016/2/18.
 */
public class Home_Fragment extends Fragment {


    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_home__fragment,null);//获取视图

        recyclerView=view.findViewById(R.id.pu);

        //设置布局启动器
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //添加装饰--设置四周间隔
        recyclerView.addItemDecoration(new MyDecoration());
        //设置适配器
        recyclerView.setAdapter(new StaggeredGridAdapter(getActivity(), new StaggeredGridAdapter.OnItemClickListener() {
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
    //自定义装饰
    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int gap=getResources().getDimensionPixelSize(R.dimen.dividerHeight2);
            int gap1=getResources().getDimensionPixelSize(R.dimen.dividerHeight3);
            outRect.set(gap,gap,gap,gap);
        }
    }
}
