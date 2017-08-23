package xprinter.xpos.market.myapplication.HomeFragment;

import dagger.Module;
import dagger.Provides;
import xprinter.xpos.market.myapplication.Util.PerFragment;

/**
 * Created by Administrator on 2017-08-22.
 */

@Module
public class HomeModule {

    private HomeFragmentContract.HomeView mView;

    public HomeModule(HomeFragmentContract.HomeView view) {
        mView = view;
    }

    @PerFragment
    @Provides
    HomeFragmentContract.HomeView provideHomeView() {
        return mView;
    }

    @PerFragment
    @Provides
    HomeViewModel provideHomeViewModel() {

    }
}
