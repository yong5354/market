package xprinter.xpos.market.myapplication.HomeFragment;

import xprinter.xpos.market.myapplication.BasePresenter;
import xprinter.xpos.market.myapplication.BaseView;

/**
 * Created by Administrator on 2017-08-22.
 */

public interface HomeFragmentContract {

    interface HomeView extends BaseView {
        void debug();
    }

    interface HomePresenter extends BasePresenter {

    }
}
