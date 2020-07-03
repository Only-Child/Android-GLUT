package com.example.wechat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/18.
 */
public class Extend_Fragment extends Fragment{
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_extend__fragment,null);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int[] imageId = new int[]{R.drawable.eimg01, R.drawable.eimg02, R.drawable.eimg03,R.drawable.eimg04,R.drawable.eimg05,
                R.drawable.eimg06,R.drawable.eimg07,R.drawable.eimg08,R.drawable.eimg09,R.drawable.eimg10

        }; // 定义并初始化保存图片id的数组
//        int[] imageId = new int[]{R.mipmap.eimg01, R.mipmap.eimg02, R.mipmap.eimg03,R.mipmap.eimg04,R.mipmap.eimg05,
//                R.mipmap.eimg06,R.mipmap.eimg07,R.mipmap.eimg08,R.mipmap.eimg09,R.mipmap.eimg10,
//
//        }; // 定义并初始化保存图片id的数组
//
//        String[] title = new String[]{"可乐", "森马", "安踏","naike","阿迪达斯","1","2","3","4","5"};
//        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>(); // 创建一个l
//        for (int i=0;i<imageId.length;i++){
//            Map<String,Object>map=new HashMap<String, Object>(); // 实例化Map对象
//            map.put("image",imageId[i]);
//            map.put("店铺",title[i]);
//            listItems.add(map);
//        }
//        SimpleAdapter adapter=new SimpleAdapter(getContext(),listItems,
//                R.layout.main,new String[]{"image"},new int[]{R.id.image}
//        );
//        listView.setAdapter(adapter);
/********************新加***************/
List<Map<String,Object>>listitem=new ArrayList<Map<String,Object>>();
for (int i=0;i<imageId.length;i++)
    {
          Map<String,Object>map=new HashMap<String, Object>();
            map.put("image",imageId[i]);
            listitem.add(map);
    }

    SimpleAdapter adapter=new SimpleAdapter(getActivity().getApplicationContext(),listitem,R.layout.extend,new String[]{"image"},new int[]{R.id.image});
        listView=getActivity().findViewById(R.id.list);
        listView.setAdapter(adapter);

    }


}
