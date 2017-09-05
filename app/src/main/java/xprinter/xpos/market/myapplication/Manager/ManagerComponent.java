package xprinter.xpos.market.myapplication.Manager;

import dagger.Component;
import xprinter.xpos.market.myapplication.ApplicationComponent;
import xprinter.xpos.market.myapplication.Base.ActivityComponent;
import xprinter.xpos.market.myapplication.Util.PerActivity;

/**
 * Created by Administrator on 2017-09-04.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class)
public interface ManagerComponent {
    void inject(DownloadFragment f);
    void inject(InstalledAppFragment f);
    void inject(UpdatedAppFragment f);
}
