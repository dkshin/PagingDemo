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

package kr.dkshin.android.pagingdemo.view.user;


import androidx.databinding.ObservableField;
import kr.dkshin.android.pagingdemo.data.model.User;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class UserItemViewModel {

    public ObservableField<String> userTitle;
    public ObservableField<String> userThumbnail;

    private final User user;

    public UserItemViewModel(User value) {
        this.user = value;
        userTitle = new ObservableField<>(user.getLogin());
        userThumbnail = new ObservableField<>(user.getAvatarUrl());
    }
}
