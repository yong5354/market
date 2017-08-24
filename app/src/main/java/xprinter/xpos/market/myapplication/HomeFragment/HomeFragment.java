package xprinter.xpos.market.myapplication.HomeFragment;

import android.app.Activity;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import xprinter.xpos.market.myapplication.Base.BaseActivity;
import xprinter.xpos.market.myapplication.CoolMarket.model.Apk;
import xprinter.xpos.market.myapplication.R;
import xprinter.xpos.market.myapplication.ViewModelFactory;
import xprinter.xpos.market.myapplication.adapter.ApkListAdapter;
import xprinter.xpos.market.myapplication.widget.LoadMoreDecoration;

/**
 * Created by Administrator on 2017-08-22.
 */

public class HomeFragment extends LifecycleFragment implements HomeFragmentContract.HomeView {
    @Inject
    ViewModelFactory mModelFactory;
    @Inject
    Activity mContext;
    @Inject
    HomePresenter mPresenter;

    @Bind(R.id.apkList)
    RecyclerView apkList;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private HomeViewModel mViewModel;
    private ApkListAdapter mAdapter;
    private LoadMoreDecoration mDecoration;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public static HomeFragment newInstance(String arg) {
        HomeFragment fragment = new HomeFragment();
        Bundle b = new Bundle();
        b.putString("key", arg);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);
        final int insets = (int) getResources().getDimension(R.dimen.card_insets);
        apkList.setLayoutManager(new LinearLayoutManager(mContext));
        apkList.setItemAnimator(new DefaultItemAnimator());
        apkList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(insets,insets,insets,insets);
            }
        });
        mDecoration = new LoadMoreDecoration(null);
        apkList.addItemDecoration(mDecoration);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initInject();
        //init adapter
        mAdapter = new ApkListAdapter(mContext);
        mAdapter.setApkList(new ArrayList<Apk>());
        apkList.setAdapter(mAdapter);
        //init viewmodel
        mViewModel = ViewModelProviders.of(this, mModelFactory).get(HomeViewModel.class);
        mViewModel.getApkList().observe(this, new Observer<List<Apk>>() {
            @Override
            public void onChanged(@Nullable List<Apk> apks) {
                Log.e("FANGUOYONG","get apk list:" + apks.size());
                mAdapter.setApkList(apks);
                mAdapter.notifyDataSetChanged();
            }
        });
        mViewModel.getStatus().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                Log.e("FANGUOYONG","get apk status:" + integer);
                if(integer == 0)
                    mDecoration.setLoadComplete(true);
                else if(integer == 1)
                    swipeRefresh.setRefreshing(true);
                else if(integer == 2)
                    swipeRefresh.setRefreshing(false);
            }
        });
        mDecoration.setListener(new LoadMoreDecoration.LoadDataListener() {
            @Override
            public void LoadData() {
                mViewModel.refreshApkList();
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDecoration.setLoadComplete(false);
                mViewModel.InitPage();
                mViewModel.refreshApkList();
            }
        });
        //start load
        apkList.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDecoration.setLoadComplete(false);
                mViewModel.InitPage();
                swipeRefresh.setRefreshing(true);
                mViewModel.refreshApkList();
            }
        },100);
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    private void initInject() {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            DaggerHomeComponent.builder().activityComponent(((BaseActivity) activity).getActivityComponent())
                    .homeModule(new HomeModule(this))
                    .build()
                    .inject(this);
        }
    }

    @Override
    public void debug() {
        Toast.makeText(mContext, "start", Toast.LENGTH_LONG).show();
    }
}
