package xprinter.xpos.market.myapplication.Home;

import android.app.Activity;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
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
import xprinter.xpos.market.myapplication.Base.model.BaseApk;
import xprinter.xpos.market.myapplication.BuildConfig;
import xprinter.xpos.market.myapplication.CoolMarket.model.Apk;
import xprinter.xpos.market.myapplication.Detail.AppDetailActivity;
import xprinter.xpos.market.myapplication.MyApplication;
import xprinter.xpos.market.myapplication.R;
import xprinter.xpos.market.myapplication.ViewModelFactory;
import xprinter.xpos.market.myapplication.adapter.ApkListAdapter;
import xprinter.xpos.market.myapplication.widget.LoadMoreDecoration;

/**
 * Created by Administrator on 2017-08-22.
 */

public class HomeFragment extends LifecycleFragment implements HomeFragmentContract.HomeView {

    private final String TAG = HomeFragment.class.getSimpleName();

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

    private Runnable mLoadTask;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG,"onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG,"onCreateView");
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);
        initInject();
        //recycleview
        final int insets = (int) getResources().getDimension(R.dimen.card_insets);
        apkList.setLayoutManager(new LinearLayoutManager(mContext));
        apkList.setItemAnimator(new DefaultItemAnimator());
        apkList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(insets,insets,insets,insets);
            }
        });
        //decoration
        mDecoration = new LoadMoreDecoration(null);
        mDecoration.setListener(new LoadMoreDecoration.LoadDataListener() {
            @Override
            public void LoadData() {
                mViewModel.refreshApkList();
            }
        });
        apkList.addItemDecoration(mDecoration);
        //adapter
        mAdapter = new ApkListAdapter(mContext);
        mAdapter.setListener(new ApkListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int apkid = mViewModel.getApkList().getValue().get(position).getId();
                Intent i = new Intent(mContext, AppDetailActivity.class);
                i.putExtra("id",apkid);
                startActivity(i);
            }
        });
        apkList.setAdapter(mAdapter);
        //swipeRefreshview
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDecoration.setLoadComplete(false);
                mViewModel.InitPage();
                mViewModel.refreshApkList();
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG,"onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e(TAG,"onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        //init viewmodel
        mViewModel = ViewModelProviders.of(this, mModelFactory).get(HomeViewModel.class);
        mViewModel.getApkList().observe(this, new Observer<List<BaseApk>>() {
            @Override
            public void onChanged(@Nullable List<BaseApk> apks) {
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
        //start load
        mLoadTask = new Runnable() {
            @Override
            public void run() {
                mDecoration.setLoadComplete(false);
                mViewModel.InitPage();
                swipeRefresh.setRefreshing(true);
                mViewModel.refreshApkList();
            }
        };
        apkList.postDelayed(mLoadTask,100);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start(null);
    }

    @Override
    public void onPause() {
        super.onPause();
        apkList.removeCallbacks(mLoadTask);
        mPresenter.stop();
    }

    @Override
    public void onDestroyView() {
        Log.e(TAG,"onDestroyView");
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG,"onDestroy");
        super.onDestroy();
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
        Toast.makeText(mContext, "buildtype:" + BuildConfig.BUILD_TYPE, Toast.LENGTH_SHORT).show();
    }
}
