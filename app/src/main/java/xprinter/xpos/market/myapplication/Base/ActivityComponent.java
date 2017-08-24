package xprinter.xpos.market.myapplication.Base;

import android.app.Activity;
import android.content.Context;

import dagger.Component;
import xprinter.xpos.market.myapplication.ApplicationComponent;
import xprinter.xpos.market.myapplication.CoolMarket.CoolMarketApi;
import xprinter.xpos.market.myapplication.Util.ContextType;
import xprinter.xpos.market.myapplication.Util.PerActivity;
import xprinter.xpos.market.myapplication.ViewModelFactory;

/**
 * Created by Administrator on 2017-08-22.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
    @ContextType("application") Context getContext();
    CoolMarketApi getCoolMarketApi();
    ViewModelFactory getViewModelFactory();
}
