package com.example.wechat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.content.DialogInterface;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Person_Fragment extends Fragment {
    SharedPreferences sp;
    SimpleAdapter adapter;




    int[] imageId = new int[]{R.drawable.img01, R.drawable.img02, R.drawable.img03,
            R.drawable.img04

    }; // 定义并初始化保存图片id的数组

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_person__fragment,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sp=getContext().getSharedPreferences("mrsoft",getContext().MODE_PRIVATE);
         final String getUser=sp.getString("username","");

        ListView listView=getActivity().findViewById(R.id.listview);

        String[] title = new String[]{"账号："+getUser, "修改密码", "浏览历史", "退出账号"}; // 定义并初始化保存列表项文字的数组
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>(); // 创建一个list集合
        // 通过for循环将图片id和列表项文字放到Map中，并添加到list集合中
        for (int i = 0; i < imageId.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>(); // 实例化Map对象
            map.put("image", imageId[i]);
            map.put("item", title[i]);
            listItems.add(map); // 将map对象添加到List集合中
        }

        adapter = new SimpleAdapter(getContext(), listItems,
                R.layout.main, new String[] { "item", "image" }, new int[] {
                R.id.title, R.id.image }); // 创建SimpleAdapter

        listView.setAdapter(adapter); // 将适配器与ListView关联
/*************************点击事件******************/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = ( Map<String, Object> )parent.getItemAtPosition(position); 	//获取选择项的值
                switch (position){
                    case 0:
                        Toast.makeText(getActivity(),map.get("item").toString(),Toast.LENGTH_SHORT).show();
                        System.out.println(sp.getString("username","")+" "+sp.getString("password",""));
                        break;

                    case 1:
                        Intent intent=new Intent(getActivity(),AlterPassword.class);
                        startActivity(intent);
                        break;

                    case 2:
                        Intent intent2=new Intent(getActivity(),Browsing_history.class);
                        startActivity(intent2);
                        break;

                    case 3:
                        AlertDialog alertDialog=new AlertDialog.Builder(getContext()).create();
                        alertDialog.setIcon(R.drawable.out);
                        alertDialog.setTitle("警告");
                        alertDialog.setMessage("退出账号会清空您的登入信息，您确定要退出登入吗？");
                        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });//取消按钮
                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = sp.edit();
                        editor.remove("username");
                                editor.remove("password");
                                editor.commit();
                                refresh();
                            }
                        });//确定按钮
                        alertDialog.show();//显示对话框
                        /************/
//                        Toast.makeText(getActivity(),map.get("名字").toString(),Toast.LENGTH_SHORT).show();
//                        SharedPreferences.Editor editor = sp.edit();
//                        editor.remove("username");
//                        editor.remove("password");
//                        editor.commit();
//                        refresh();
                        //System.out.println(sp.getString("username","")+" "+sp.getString("password",""));
                        break;

                    default:
                        break;
                }



            }
        });


    }
    // 刷新fragment
    private void refresh() {
        ShoppingCart_Fragment shoppingCart_fragment=new ShoppingCart_Fragment();
        System.out.println("refresh...");
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, shoppingCart_fragment).addToBackStack(null).commitAllowingStateLoss();
    }
}

