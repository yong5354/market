package xprinter.xpos.market.myapplication.Query;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xprinter.xpos.market.myapplication.Base.BaseActivity;
import xprinter.xpos.market.myapplication.Base.model.BaseMarketApi;
import xprinter.xpos.market.myapplication.Base.model.BaseTag;
import xprinter.xpos.market.myapplication.Home.HomeFragment;
import xprinter.xpos.market.myapplication.R;
import xprinter.xpos.market.myapplication.Result.ResultActivity;
import xprinter.xpos.market.myapplication.adapter.TagListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class QueryFragment extends Fragment {

    private final String TAG = QueryFragment.class.getSimpleName();

    @Inject
    Activity mContext;

    @Inject
    BaseMarketApi mMarketApi;
    @Bind(R.id.tagList)
    RecyclerView tagList;

    private TagListAdapter mAdapter;

    public static QueryFragment newInstance() {
        return new QueryFragment();
    }

    public static QueryFragment newInstance(String arg) {
        QueryFragment fragment = new QueryFragment();
        Bundle b = new Bundle();
        b.putString("key", arg);
        fragment.setArguments(b);
        return fragment;
    }

    public QueryFragment() {
        Log.e(TAG,"onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG,"onCreateView");
        View v = inflater.inflate(R.layout.fragment_query, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e(TAG,"onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        initInject();
        mAdapter = new TagListAdapter(mContext);
        mAdapter.setListener(new TagListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(String name, String query) {
                Intent i = new Intent(mContext, ResultActivity.class);
                i.putExtra("query",query);
                i.putExtra("title",name);
                mContext.startActivity(i);
            }
        });
        tagList.setLayoutManager(new LinearLayoutManager(mContext));
        tagList.setAdapter(mAdapter);
        mMarketApi.obtainTag()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BaseTag>>() {
                    @Override
                    public void accept(@NonNull List<BaseTag> baseTags) throws Exception {
                        mAdapter.setTagList(baseTags);
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
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
            DaggerQueryComponent.builder()
                    .activityComponent(((BaseActivity) activity).getActivityComponent())
                    .build()
                    .inject(this);
        }
    }
}
