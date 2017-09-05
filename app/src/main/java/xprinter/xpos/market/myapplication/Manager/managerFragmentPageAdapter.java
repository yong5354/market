package xprinter.xpos.market.myapplication.Manager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import xprinter.xpos.market.myapplication.R;

/**
 * Created by Administrator on 2017-09-04.
 */

public class managerFragmentPageAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private String[] mTitle = new String[3];

    public managerFragmentPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        mTitle[0] = mContext.getString(R.string.tab_download);
        mTitle[1] = mContext.getString(R.string.tab_update);
        mTitle[2] = mContext.getString(R.string.tab_install);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DownloadFragment();
            case 1:
                return new UpdatedAppFragment();
            case 2:
                return new InstalledAppFragment();
            default:
                return new DownloadFragment();
        }
    }

    @Override
    public int getCount() {
        return mTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
