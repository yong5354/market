package xprinter.xpos.market.myapplication.HomeFragment;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017-08-22.
 */

public class HomePresenter implements HomeFragmentContract.HomePresenter{

    private HomeFragmentContract.HomeView mView;

    @Inject
    public HomePresenter(HomeFragmentContract.HomeView view) {
        mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
