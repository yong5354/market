package xprinter.xpos.market.myapplication.Detail;

/**
 * Created by Administrator on 2017-08-26.
 */

import dagger.Component;
import xprinter.xpos.market.myapplication.Base.ActivityComponent;
import xprinter.xpos.market.myapplication.Util.PerFragment;

@PerFragment
@Component(dependencies = ActivityComponent.class,modules = DetailModule.class)
public interface DetailComponent {
    void inject(DetailFragment f);
}
