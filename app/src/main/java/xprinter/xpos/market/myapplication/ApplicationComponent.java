package xprinter.xpos.market.myapplication;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import xprinter.xpos.market.myapplication.CoolMarket.CoolMarketApi;
import xprinter.xpos.market.myapplication.Util.ContextType;

/**
 * Created by Administrator on 2017-08-18.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    @ContextType("application")  Context getContext();
    CoolMarketApi getCoolMarketApi();
}
