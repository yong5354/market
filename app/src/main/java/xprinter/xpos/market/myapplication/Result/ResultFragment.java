package xprinter.xpos.market.myapplication.Result;


import android.app.Activity;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import xprinter.xpos.market.myapplication.Base.BaseActivity;
import xprinter.xpos.market.myapplication.Base.model.BaseApk;
import xprinter.xpos.market.myapplication.Detail.AppDetailActivity;
import xprinter.xpos.market.myapplication.R;
import xprinter.xpos.market.myapplication.ViewModelFactory;
import xprinter.xpos.market.myapplication.adapter.ApkListAdapter;
import xprinter.xpos.market.myapplication.widget.LoadMoreDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends LifecycleFragment {

    private final String TAG = ResultFragment.class.getSimpleName();

    @Inject
    ViewModelFactory mModelFactory;
    @Inject
    Activity mContext;

    @Bind(R.id.apkResultList)
    RecyclerView apkResultList;

    private LoadMoreDecoration mDecoration;
    private ApkListAdapter mAdapter;
    private Runnable mLoadTask;
    private QueryViewModel mViewModel;
    private String mQuery;

    public static ResultFragment newInstance() {
        return new ResultFragment();
    }

    public static ResultFragment newInstance(String query) {
        ResultFragment fragment = new ResultFragment();
        Bundle b = new Bundle();
        b.putString("query", query);
        fragment.setArguments(b);
        return fragment;
    }

    public ResultFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if(b != null) {
            mQuery = b.getString("query");
        }
        Log.e(TAG, "onCreate : argment=" + mQuery);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        View root = inflater.inflate(R.layout.fragment_result, container, false);
        ButterKnife.bind(this, root);
        initInject();
        //recycleview
        final int insets = (int) getResources().getDimension(R.dimen.card_insets);
        apkResultList.setLayoutManager(new LinearLayoutManager(mContext));
        apkResultList.setItemAnimator(new DefaultItemAnimator());
        apkResultList.addItemDecoration(new RecyclerView.ItemDecoration() {
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
                mViewModel.getQueryResult();
            }
        });
        apkResultList.addItemDecoration(mDecoration);
        //adapter
        mAdapter = new ApkListAdapter(mContext);
        mAdapter.setListener(new ApkListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int packageid) {
                Intent i = new Intent(mContext, AppDetailActivity.class);
                i.putExtra("id",packageid);
                startActivity(i);
            }
        });
        apkResultList.setAdapter(mAdapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        //init viewmodel
        mViewModel = ViewModelProviders.of(this, mModelFactory).get(QueryViewModel.class);
        mViewModel.setQuery(mQuery);
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
                    ;
                else if(integer == 2)
                    ;
            }
        });
        //start load
        mLoadTask = new Runnable() {
            @Override
            public void run() {
                mDecoration.setLoadComplete(false);
                mViewModel.InitPage();
                mViewModel.getQueryResult();
            }
        };
        apkResultList.postDelayed(mLoadTask,100);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        apkResultList.removeCallbacks(mLoadTask);
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
            DaggerResultComponent.builder()
                    .activityComponent(((BaseActivity) activity).getActivityComponent())
                    .build()
                    .inject(this);
        }
    }
}
