package xprinter.xpos.market.myapplication.Manager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import xprinter.xpos.market.myapplication.Base.BaseActivity;
import xprinter.xpos.market.myapplication.R;

/**
 * Created by Administrator on 2017-09-04.
 */

public class ManagerActivity extends BaseActivity {

    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.container)
    ViewPager container;
    private FragmentPagerAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);
        ButterKnife.bind(this);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setElevation(0);
            bar.setDisplayHomeAsUpEnabled(true);
            setActionBarTitle(R.string.title_section2);
        }
        initTab();
    }

    private void initTab() {
        mPageAdapter = new managerFragmentPageAdapter(getSupportFragmentManager(),this);
        container.setAdapter(mPageAdapter);
        tabs.setupWithViewPager(container);
        tabs.getTabAt(0).select();
    }
}
