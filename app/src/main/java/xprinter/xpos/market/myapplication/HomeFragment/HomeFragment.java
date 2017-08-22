package xprinter.xpos.market.myapplication.HomeFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import xprinter.xpos.market.myapplication.Base.BaseActivity;
import xprinter.xpos.market.myapplication.R;

/**
 * Created by Administrator on 2017-08-22.
 */

public class HomeFragment extends Fragment implements HomeFragmentContract.HomeView{

    @Inject
    Activity mContext;

    @Inject
    HomePresenter mPresenter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public static HomeFragment newInstance(String arg) {
        HomeFragment fragment = new HomeFragment();
        Bundle b = new Bundle();
        b.putString("key",arg);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main,container,false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initInject();
        mPresenter.start();
    }

    private void initInject() {
        Activity activity = getActivity();
        if(activity instanceof BaseActivity) {
            DaggerHomeComponent.builder().activityComponent(((BaseActivity)activity).getActivityComponent())
                    .homeModule(new HomeModule(this))
                    .build()
                    .inject(this);
        }
    }

    @Override
    public void test() {
        Toast.makeText(mContext,"MVP dagger2 test!",Toast.LENGTH_LONG).show();
    }
}
