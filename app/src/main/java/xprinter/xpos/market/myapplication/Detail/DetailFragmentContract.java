package xprinter.xpos.market.myapplication.Detail;

import xprinter.xpos.market.myapplication.Base.model.BaseApkField;
import xprinter.xpos.market.myapplication.BasePresenter;
import xprinter.xpos.market.myapplication.BaseView;
import xprinter.xpos.market.myapplication.CoolMarket.model.ApkField;

/**
 * Created by Administrator on 2017-08-26.
 */

public interface DetailFragmentContract {

    interface DetailView extends BaseView {
        void updateContent(BaseApkField apkfile);
    }

    interface DetailPresenter extends BasePresenter {

    }
}
