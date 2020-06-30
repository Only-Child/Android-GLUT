package com.example.wechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wechat.utils.LinearAdapter;

public class SearchResultActivity extends AppCompatActivity {

        private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        recyclerView=findViewById(R.id.result);
        //得到搜索结果
        Bundle extras = getIntent().getExtras();
        String result = extras.getString("result");
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));
        recyclerView.addItemDecoration(new MyDecoration());
        recyclerView.setAdapter(new LinearAdapter(SearchResultActivity.this, new LinearAdapter.OnItemClickListener(){
            @Override
            public void onClick(int pos) {
                Toast.makeText(SearchResultActivity.this,"click..."+pos,Toast.LENGTH_SHORT).show();

            }
        },result));

    }
    //自定义装饰2
    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int gap1=getResources().getDimensionPixelSize(R.dimen.dividerHeight4);
            outRect.set(0,0,0,gap1);
        }
    }
}
