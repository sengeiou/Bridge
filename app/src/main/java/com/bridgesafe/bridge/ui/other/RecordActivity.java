package com.bridgesafe.bridge.ui.other;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.bridgesafe.bridge.R;
import com.bridgesafe.bridge.network.model.FalseModel;
import com.bridgesafe.bridge.ui.adapter.RecordAdapter;
import com.bridgesafe.bridge.ui.adapter.WarningListAdapter;
import com.bridgesafe.bridge.ui.base.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 航行记录
 */
public class RecordActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.titleBar)
    RelativeLayout titleBar;
    private RecordAdapter mAdapter;
    private ArrayList<FalseModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        setBackView();
        initData();
        initUI();
    }

    private void initUI() {
        View header = LayoutInflater.from(mContext).inflate(R.layout.record_list_header, null);
        setTitle("航行记录", getResources().getColor(R.color.title_color));
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        initAdapter();
        mAdapter.addHeaderView(header);
    }

    private void initAdapter() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecordAdapter(R.layout.record_list_view, data);
        mAdapter.setEnableLoadMore(false);
       // mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
//        mAdapter.disableLoadMoreIfNotFullPage();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()

        {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                String id = data.get(position).getId();
//                Bundle bundle = new Bundle();
//                bundle.putString("id", id);
//                toActivity(GoodDescActivity.class, bundle);
               toActivity(RecordDetailActivity.class);
            }
        });
    }

    public void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FalseModel falseModel = new FalseModel();
            falseModel.setTitle("" + i);
            data.add(falseModel);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (mCurrentCounter >= TOTAL_COUNTER) {
//                    //数据全部加载完毕
//                    mAdapter.loadMoreEnd();
//                } else {
//                    if (isErr) {
//                        //成功获取更多数据
//                        //  mQuickAdapter.addData(DataServer.getSampleData(PAGE_SIZE));
//                        pageNum += 1;
//                        initData(pageNum, true);
//                        mCurrentCounter = mAdapter.getData().size();
//                        mAdapter.loadMoreComplete();
//                    } else {
//                        //获取更多数据失败
//                        isErr = true;
//                        mAdapter.loadMoreFail();
//
//                    }
//                }
                mAdapter.loadMoreEnd();
                mAdapter.loadMoreComplete();
            }

        }, 3000);
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);//禁止加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                // mAdapter.setNewData(data);
//                isErr = false;
//                mCurrentCounter = PAGE_SIZE;
//                pageNum = 1;//页数置为1 才能继续重新加载
//                initData(pageNum, false);
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.setEnableLoadMore(false);//启用加载
            }
        }, 2000);
    }
}
