package xprinter.xpos.market.myapplication.Manager;


import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
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
import xprinter.xpos.market.myapplication.Base.model.BaseApkField;
import xprinter.xpos.market.myapplication.DownloadViewModel;
import xprinter.xpos.market.myapplication.MyApplication;
import xprinter.xpos.market.myapplication.R;
import xprinter.xpos.market.myapplication.Util.ContextType;
import xprinter.xpos.market.myapplication.ViewModelFactory;
import xprinter.xpos.market.myapplication.adapter.DownloadListAdapter;
import xprinter.xpos.market.myapplication.adapter.InstalledApkListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstalledAppFragment extends LifecycleFragment {

    @Inject
    ViewModelFactory mModelFactory;
    @Inject
    @ContextType("application")
    Context mContext;
    @Bind(R.id.content)
    RecyclerView content;

    private DownloadViewModel mViewModel;
    private InstalledApkListAdapter mAdapter;

    public InstalledAppFragment() {
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

        mAdapter = new InstalledApkListAdapter(mContext);
        mAdapter.setListener(new InstalledApkListAdapter.OnButtonClickListener() {
            @Override
            public void ButtonClick(String packagename) {
                Uri uri = Uri.parse("package:" + packagename);//获取删除包名的URI
                Intent i = new Intent();
                i.setAction(Intent.ACTION_DELETE);//设置我们要执行的卸载动作
                i.setData(uri);//设置获取到的URI
                startActivity(i);
            }
        });
        content.setAdapter(mAdapter);

        mViewModel = ViewModelProviders.of(this, mModelFactory).get(DownloadViewModel.class);
        mViewModel.mInstalledList.observe(this, new Observer<List<PackageInfo>>() {
            @Override
            public void onChanged(@Nullable List<PackageInfo> downloadApks) {
                Log.e("FANGUOYONG","install apk change");
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
