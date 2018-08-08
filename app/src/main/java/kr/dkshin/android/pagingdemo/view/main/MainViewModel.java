/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package kr.dkshin.android.pagingdemo.view.main;

import android.content.res.ColorStateList;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.viewpager.widget.ViewPager;
import kr.dkshin.android.pagingdemo.R;
import kr.dkshin.android.pagingdemo.data.DataManager;
import kr.dkshin.android.pagingdemo.util.rx.SchedulerProvider;
import kr.dkshin.android.pagingdemo.view.base.BaseViewModel;

/**
 * Created by amitshekhar on 07/07/17.
 */

public class MainViewModel extends BaseViewModel<MainNavigator> {

    public final ObservableField<ColorStateList> colorStateListObservableField = new ObservableField<>();
    public final ObservableInt currentPageObservableInt = new ObservableInt();

    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        colorStateListObservableField.set(null);
    }

    public ViewPager.SimpleOnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            currentPageObservableInt.set(position);
        }
    };

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                currentPageObservableInt.set(0);
                return true;
            case R.id.navigation_dashboard:
                currentPageObservableInt.set(1);
                return true;
        }
        return false;
    }


}
