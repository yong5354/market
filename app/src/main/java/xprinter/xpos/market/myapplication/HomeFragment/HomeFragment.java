package xprinter.xpos.market.myapplication.HomeFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xprinter.xpos.market.myapplication.Base.BaseActivity;
import xprinter.xpos.market.myapplication.CoolMarket.CoolMarketApi;
import xprinter.xpos.market.myapplication.CoolMarket.model.Apk;
import xprinter.xpos.market.myapplication.R;

/**
 * Created by Administrator on 2017-08-22.
 */

public class HomeFragment extends Fragment implements HomeFragmentContract.HomeView {

    @Inject
    Activity mContext;

    @Inject
    HomePresenter mPresenter;

    @Inject
    CoolMarketApi mMarketApi;

    @Bind(R.id.testbutton)
    Button testbutton;

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
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initInject();
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

    @OnClick(R.id.testbutton)
    public void onClick(View view) {
        Toast.makeText(mContext, "onRetrofitTest", Toast.LENGTH_LONG).show();
        Observable<List<Apk>> observable = mMarketApi.obtainHomepageApkList(0);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Apk>>() {
                    @Override
                    public void accept(@NonNull List<Apk> apks) throws Exception {
                        for(Apk apk : apks)
                            Log.e("FANGUOYONG","get apk:" + apk.getApkname());
                    }
                });
    }

    @Override
    public void debug() {
        Toast.makeText(mContext, "start", Toast.LENGTH_LONG).show();
    }
}
