package kr.dkshin.android.pagingdemo.view.main;


import kr.dkshin.android.pagingdemo.data.DataManager;
import kr.dkshin.android.pagingdemo.util.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    MainViewModel provideMainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new MainViewModel(dataManager, schedulerProvider);
    }

    @Provides
    MainFragmentPagerAdapter provideMainFragmentPagerAdapter(MainActivity mainActivity) {
        return new MainFragmentPagerAdapter(mainActivity.getSupportFragmentManager());
    }

}