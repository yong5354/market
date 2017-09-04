package xprinter.xpos.market.myapplication.Manager;


import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import xprinter.xpos.market.myapplication.Data.DownloadApk;
import xprinter.xpos.market.myapplication.DownloadViewModel;
import xprinter.xpos.market.myapplication.MyApplication;
import xprinter.xpos.market.myapplication.R;
import xprinter.xpos.market.myapplication.Util.ContextType;
import xprinter.xpos.market.myapplication.ViewModelFactory;
import xprinter.xpos.market.myapplication.adapter.DownloadListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFragment extends LifecycleFragment {

    @Inject
    ViewModelFactory mModelFactory;
    @Inject
    @ContextType("application") Context mContext;

    @Bind(R.id.content)
    RecyclerView content;

    private DownloadViewModel mViewModel;
    private DownloadListAdapter mAdapter;

    public DownloadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_manager, container, false);
        ButterKnife.bind(this, root);
        content.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new DownloadListAdapter(getActivity());
        content.setAdapter(mAdapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initInject();
        mViewModel = ViewModelProviders.of(this, mModelFactory).get(DownloadViewModel.class);
        mViewModel.mDownloadList.observe(this, new Observer<List<DownloadApk>>() {
            @Override
            public void onChanged(@Nullable List<DownloadApk> downloadApks) {
                Log.e("FANGUOYONG","database change");
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
