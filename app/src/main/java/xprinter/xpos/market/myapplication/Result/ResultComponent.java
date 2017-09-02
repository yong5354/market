package xprinter.xpos.market.myapplication.Result;

import dagger.Component;
import xprinter.xpos.market.myapplication.Base.ActivityComponent;
import xprinter.xpos.market.myapplication.Util.PerFragment;

/**
 * Created by Administrator on 2017-09-01.
 */

@PerFragment
@Component(dependencies = ActivityComponent.class)
public interface ResultComponent {
    void inject(ResultFragment f);
}
