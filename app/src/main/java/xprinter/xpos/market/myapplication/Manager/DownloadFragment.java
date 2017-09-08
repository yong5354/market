package xprinter.xpos.market.myapplication.Manager;


import android.app.AlertDialog;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
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
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import xprinter.xpos.market.myapplication.Data.DownloadApk;
import xprinter.xpos.market.myapplication.DownloadViewModel;
import xprinter.xpos.market.myapplication.MyApplication;
import xprinter.xpos.market.myapplication.R;
import xprinter.xpos.market.myapplication.Util.ContextType;
import xprinter.xpos.market.myapplication.Util.DownLoadTask;
import xprinter.xpos.market.myapplication.ViewModelFactory;
import xprinter.xpos.market.myapplication.adapter.DownloadListAdapter;

public class DownloadFragment extends LifecycleFragment {

    @Inject
    ViewModelFactory mModelFactory;
    @Inject
    DownLoadTask mDownLoadTask;
    @Inject
    @ContextType("application") Context mContext;

    @Bind(R.id.content)
    RecyclerView content;

    private DownloadViewModel mViewModel;
    private DownloadListAdapter mAdapter;
    private Disposable mDownloadDisposable;

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
        mAdapter.setListener(new DownloadListAdapter.ItemClickListener() {
            @Override
            public void delete(final DownloadApk apk) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setMessage("是否删除任务和安装包")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mDownLoadTask.delDownload(apk);
                            }
                        })
                        .setNegativeButton("否",null)
                        .create();
                dialog.show();
            }

            @Override
            public void Install(String fileUrl) {
                mDownLoadTask.startInstall(fileUrl);
            }
        });
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
                Log.e("FANGUOYONG","download database change");
                mAdapter.setList(downloadApks);
                mAdapter.notifyDataSetChanged();
                startObserver(downloadApks);
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void startObserver(final List<DownloadApk> apks) {
        if(mDownloadDisposable != null && !mDownloadDisposable.isDisposed())
            mDownloadDisposable.dispose();
        mDownloadDisposable = mDownLoadTask.observerDownloadProgress(apks)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer change) throws Exception {
                        Log.e("FANGUOYONG","update progress : " + change);
                        if(change != -1)
                            mAdapter.notifyItemChanged(change,apks.get(change).percent);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    private void initInject() {
        DaggerManagerComponent.builder()
                .applicationComponent(((MyApplication)(getActivity().getApplication())).getApplicationComponent())
                .build()
                .inject(this);
    }
}
