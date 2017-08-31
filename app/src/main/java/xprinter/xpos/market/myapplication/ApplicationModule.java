package xprinter.xpos.market.myapplication;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import xprinter.xpos.market.myapplication.Base.model.BaseMarketApi;
import xprinter.xpos.market.myapplication.CoolMarket.CoolMarketApi;
import xprinter.xpos.market.myapplication.CoolpadMarket.CoolpadMarketApi;
import xprinter.xpos.market.myapplication.Data.AppDatabase;
import xprinter.xpos.market.myapplication.Util.ContextType;
import xprinter.xpos.market.myapplication.Util.DownLoadTask;

/**
 * Created by Administrator on 2017-08-18.
 */

@Module
public class ApplicationModule {

    private final Context mContext;
    //private ViewModelFactory mFactory;
    //private AppDatabase mDatabase;

    ApplicationModule(Context context) {
        mContext = context;
        //mFactory = new ViewModelFactory((MyApplication) mContext);
        //mDatabase = Room.databaseBuilder(mContext,AppDatabase.class,"database-downloadapk").build();
    }

    @Provides
    @Singleton
    @ContextType("application")
    Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    BaseMarketApi provideBaseMarketApi() { //返回不同实例，切换服务器源
        //return CoolMarketApi.getInstance();
        //return new CoolMarketApi(); //coolmarket
        return new CoolpadMarketApi(); //coolpad
    }

    @Provides
    @Singleton
    ViewModelFactory provideViewModelFactory() {
        return new ViewModelFactory((MyApplication) mContext);
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase() {
        return Room.databaseBuilder(mContext,AppDatabase.class,"database-downloadapk").build();
    }

    @Provides
    @Singleton
    DownLoadTask provideDownLoadTask() {
        return new DownLoadTask(mContext,provideAppDatabase());
    }
}
