package kr.dkshin.android.pagingdemo.view.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import kr.dkshin.android.pagingdemo.BR;
import kr.dkshin.android.pagingdemo.R;
import kr.dkshin.android.pagingdemo.databinding.ActivityMainBinding;
import kr.dkshin.android.pagingdemo.view.base.BaseActivity;
import kr.dkshin.android.pagingdemo.view.main.network.OnlyNetworkFragment;
import kr.dkshin.android.pagingdemo.view.main.room.RoomWithApiFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    MainViewModel mainViewModel;
    @Inject
    MainFragmentPagerAdapter mainFragmentPagerAdapter;

    private ActivityMainBinding activityMainBinding;

    public static OnlyNetworkFragment onlyNetworkFragment;
    public static RoomWithApiFragment roomWithApiFragment;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        return mainViewModel;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
        Logger.e("throwable = " + throwable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = getViewDataBinding();
        mainViewModel.setNavigator(this);
        initBottomNavigationAndViewPager();
    }

    private void initBottomNavigationAndViewPager() {
        onlyNetworkFragment = OnlyNetworkFragment.newInstance();
        roomWithApiFragment = RoomWithApiFragment.newInstance();

        mainFragmentPagerAdapter.addFragment(onlyNetworkFragment, OnlyNetworkFragment.TAG);
        mainFragmentPagerAdapter.addFragment(roomWithApiFragment, RoomWithApiFragment.TAG);
        activityMainBinding.mainViewPager.setAdapter(mainFragmentPagerAdapter);
        activityMainBinding.mainViewPager.setOffscreenPageLimit(2);
    }

}
