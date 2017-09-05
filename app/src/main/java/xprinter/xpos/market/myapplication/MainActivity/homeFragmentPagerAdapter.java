package xprinter.xpos.market.myapplication.MainActivity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import xprinter.xpos.market.myapplication.Home.HomeFragment;
import xprinter.xpos.market.myapplication.Query.QueryFragment;
import xprinter.xpos.market.myapplication.R;

/**
 * Created by Administrator on 2017-08-28.
 */

public class homeFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[2];
    private Context mContext;

    public homeFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        mTitles[0] = mContext.getString(R.string.tab_suggest);
        mTitles[1] = mContext.getString(R.string.tab_type);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return QueryFragment.newInstance();
            default:
                return HomeFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
