package xprinter.xpos.market.myapplication;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import xprinter.xpos.market.myapplication.Base.model.BaseMarketApi;
import xprinter.xpos.market.myapplication.CoolMarket.CoolMarketApi;
import xprinter.xpos.market.myapplication.Data.AppDatabase;
import xprinter.xpos.market.myapplication.Home.HomeViewModel;
import xprinter.xpos.market.myapplication.Util.ContextType;
import xprinter.xpos.market.myapplication.Util.DownLoadTask;

/**
 * Created by Administrator on 2017-08-18.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    @ContextType("application")  Context getContext();
    BaseMarketApi getBaseMarketApi();
    ViewModelFactory getViewModelFactory();
    AppDatabase getAppDatabase();
    DownLoadTask getDownLoadTask();

    void inject(HomeViewModel viewmodel);

    interface injectable {
        void inject(ApplicationComponent component);
    }
}
