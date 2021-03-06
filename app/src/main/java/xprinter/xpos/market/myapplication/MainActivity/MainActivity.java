package xprinter.xpos.market.myapplication.MainActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import xprinter.xpos.market.myapplication.Base.BaseActivity;
import xprinter.xpos.market.myapplication.Home.HomeFragment;
import xprinter.xpos.market.myapplication.Manager.ManagerActivity;
import xprinter.xpos.market.myapplication.R;
import xprinter.xpos.market.myapplication.Search.SearchActivity;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.search)
    EditText search;

    private View mContainer;
    private boolean mTabMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setActionBarTitle(R.string.title_section1);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
        navView.setCheckedItem(R.id.nav_home);

        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        checkPermission(null, permission);

        search.setFocusable(false);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });

        mContainer = findViewById(R.id.container);
        if (mContainer instanceof ViewPager) {
            initTab();
            mTabMode = true;
        } else {
            mTabMode = false;
            tabs.setVisibility(View.GONE);
            setFragment(HomeFragment.newInstance());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                if (mTabMode) {
                    tabs.getTabAt(0).select();
                } else
                    setFragment(HomeFragment.newInstance());
                break;
            case R.id.nav_manager:
                startActivity(new Intent(this, ManagerActivity.class));
                break;
            case R.id.nav_settings:
                break;
            default:
                break;
        }
        return false;
    }

    private void initTab() {
        tabs.setVisibility(View.VISIBLE);
        //tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        //tabs.setTabMode(TabLayout.MODE_FIXED);
        FragmentPagerAdapter adapter = new homeFragmentPagerAdapter(getSupportFragmentManager(), this);
        ((ViewPager) mContainer).setAdapter(adapter);
        tabs.setupWithViewPager((ViewPager) mContainer);
        tabs.getTabAt(0).select();
    }
}
