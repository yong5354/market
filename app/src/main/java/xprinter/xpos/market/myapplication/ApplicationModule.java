package xprinter.xpos.market.myapplication;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import xprinter.xpos.market.myapplication.CoolMarket.CoolMarketApi;
import xprinter.xpos.market.myapplication.Data.AppDatabase;
import xprinter.xpos.market.myapplication.Util.ContextType;

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
    CoolMarketApi provideCoolMarketApi() {
        //return CoolMarketApi.getInstance();
        return new CoolMarketApi();
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
}
