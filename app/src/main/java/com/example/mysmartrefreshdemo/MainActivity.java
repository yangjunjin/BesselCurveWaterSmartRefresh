package com.example.mysmartrefreshdemo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.example.mysmartrefreshdemo.besselcurve.BesselCurveClassicsHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MainActivity extends Activity {
    private String TAG="MainActivity=ddd==";
    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<String> list;
    private BesselCurveClassicsHeader mGifHeader;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        mGifHeader = findViewById(R.id.refresh_header);
        refreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);

        mMyAdapter = new MyAdapter(list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMyAdapter);


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefresh();
                    }
                },1000);
            }
        });
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            list.add("Item " + i);
        }
    }
}
