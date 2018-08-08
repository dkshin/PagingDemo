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

package kr.dkshin.android.pagingdemo.view.main.room;


import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;
import kr.dkshin.android.pagingdemo.ViewModelProviderFactory;
import kr.dkshin.android.pagingdemo.data.DataManager;
import kr.dkshin.android.pagingdemo.util.rx.SchedulerProvider;
import kr.dkshin.android.pagingdemo.view.user.UserAdapter;

/**
 * Created by amitshekhar on 14/09/17.
 */
@Module
public class RoomWithApiFragmentModule {

    @Provides
    RoomWithApiViewModel roomWithApiViewModel(DataManager dataManager,
                                       SchedulerProvider schedulerProvider) {
        return new RoomWithApiViewModel(dataManager, schedulerProvider);
    }
    @Provides
    UserAdapter provideRoomWithApiAdapter(RoomWithApiFragment fragment){
        return new UserAdapter(fragment);
    }
    @Provides
    ViewModelProvider.Factory provideRoomWithApiViewModel(RoomWithApiViewModel roomWithApiViewModel) {
        return new ViewModelProviderFactory<>(roomWithApiViewModel);
    }

    @Provides
    LinearLayoutManager provideRoomWithApiLinearLayoutManager(RoomWithApiFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
