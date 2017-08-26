package xprinter.xpos.market.myapplication.Detail;

import dagger.Module;
import dagger.Provides;
import xprinter.xpos.market.myapplication.Util.PerFragment;

/**
 * Created by Administrator on 2017-08-26.
 */

@Module
public class DetailModule {

    private DetailFragmentContract.DetailView mView;

    public DetailModule(DetailFragmentContract.DetailView v) {
        mView = v;
    }

    @PerFragment
    @Provides
    DetailFragmentContract.DetailView provideDetailView() {
        return mView;
    }
}
