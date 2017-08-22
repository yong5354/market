package xprinter.xpos.market.myapplication.Base;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import xprinter.xpos.market.myapplication.Util.PerActivity;

/**
 * Created by Administrator on 2017-08-22.
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @PerActivity
    @Provides
    Activity provideActivity() {
        return mActivity;
    }
}
