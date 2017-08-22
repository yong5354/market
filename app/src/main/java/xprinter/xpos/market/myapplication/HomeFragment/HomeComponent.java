package xprinter.xpos.market.myapplication.HomeFragment;

import dagger.Component;
import xprinter.xpos.market.myapplication.Base.ActivityComponent;
import xprinter.xpos.market.myapplication.Util.PerFragment;

/**
 * Created by Administrator on 2017-08-22.
 */

@PerFragment
@Component(dependencies = ActivityComponent.class,modules = HomeModule.class)
public interface HomeComponent {
    void inject(HomeFragment fragment);
}
