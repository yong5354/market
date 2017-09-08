package xprinter.xpos.market.myapplication.Manager;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import xprinter.xpos.market.myapplication.Base.model.BaseApk;
import xprinter.xpos.market.myapplication.Base.model.BaseApkField;
import xprinter.xpos.market.myapplication.Detail.AppDetailActivity;
import xprinter.xpos.market.myapplication.DownloadViewModel;
import xprinter.xpos.market.myapplication.MyApplication;
import xprinter.xpos.market.myapplication.R;
import xprinter.xpos.market.myapplication.Util.ContextType;
import xprinter.xpos.market.myapplication.Util.DownLoadTask;
import xprinter.xpos.market.myapplication.ViewModelFactory;
import xprinter.xpos.market.myapplication.adapter.UpdatedApkListAdapter;

/**
 * Created by Administrator on 2017-09-05.
 */

public class UpdatedAppFragment extends LifecycleFragment{

    @Inject
    ViewModelFactory mModelFactory;
    @Inject
    @ContextType("application")
    Context mContext;
    @Inject
    DownLoadTask mDownloadTask;

    @Bind(R.id.content)
    RecyclerView content;

    private DownloadViewModel mViewModel;
    private UpdatedApkListAdapter mAdapter;

    public UpdatedAppFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manager, container, false);
        ButterKnife.bind(this, root);
        content.setLayoutManager(new LinearLayoutManager(mContext));
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initInject();
        mAdapter = new UpdatedApkListAdapter(mContext);
        mAdapter.setListener(new UpdatedApkListAdapter.OnItemClickListener() {
            @Override
            public void showDetail(int packageid) {
                Intent i = new Intent(mContext, AppDetailActivity.class);
                i.putExtra("id",packageid);
                startActivity(i);
            }

            @Override
            public void download(BaseApk apk) {
                try {
                    mDownloadTask.addDownload(apk, apk.getDownloadUrl());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        content.setAdapter(mAdapter);
        mViewModel = ViewModelProviders.of(this, mModelFactory).get(DownloadViewModel.class);
        mViewModel.mUpdateList.observe(this, new Observer<List<BaseApkField>>() {
            @Override
            public void onChanged(@Nullable List<BaseApkField> downloadApks) {
                Log.e("FANGUOYONG","update apk change");
                Log.e("FANGUOYONG","DownloadTask in Updated : " + mDownloadTask);
                mAdapter.setList(downloadApks);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initInject() {
        DaggerManagerComponent.builder()
                .applicationComponent(((MyApplication)(getActivity().getApplication())).getApplicationComponent())
                .build()
                .inject(this);
    }
}
