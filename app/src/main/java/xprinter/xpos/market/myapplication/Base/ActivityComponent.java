package xprinter.xpos.market.myapplication.Base;

import android.app.Activity;

import dagger.Component;
import xprinter.xpos.market.myapplication.ApplicationComponent;
import xprinter.xpos.market.myapplication.Util.PerActivity;

/**
 * Created by Administrator on 2017-08-22.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
}