package xprinter.xpos.market.myapplication.Query;

import dagger.Component;
import xprinter.xpos.market.myapplication.Base.ActivityComponent;
import xprinter.xpos.market.myapplication.Util.PerFragment;

/**
 * Created by Administrator on 2017-08-31.
 */

@PerFragment
@Component(dependencies = ActivityComponent.class)
public interface QueryComponent {
    void inject(QueryFragment f);
}
