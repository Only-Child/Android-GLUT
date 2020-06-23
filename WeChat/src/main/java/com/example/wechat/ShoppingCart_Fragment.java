package com.example.wechat;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

/**
 * Created by Administrator on 2016/2/18.
 */
public class ShoppingCart_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_shoppingcart__fragment,null);
        return view;
    }

//public void onActivityCreated(@Nullable Bundle save) {
//    super.onActivityCreated(save);
////    定义一个按钮
//    Button button=getActivity().findViewById(R.id.btn);
//    button.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
////            设置单击显示文本
//            Toast.makeText(getActivity(),"你点击了小可爱",Toast.LENGTH_SHORT).show();
//        }
//    });
//}
}
