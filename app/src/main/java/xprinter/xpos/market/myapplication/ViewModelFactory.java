package xprinter.xpos.market.myapplication;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

/**
 * Created by Administrator on 2017-08-24.
 */

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private MyApplication mApplication;

    public ViewModelFactory(MyApplication app) {
        mApplication = app;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        T t = super.create(modelClass);
        if(t instanceof ApplicationComponent.injectable)
            ((ApplicationComponent.injectable) t).inject(mApplication.getApplicationComponent());
        return t;
    }
}
