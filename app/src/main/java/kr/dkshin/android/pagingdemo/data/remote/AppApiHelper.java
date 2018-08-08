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

package kr.dkshin.android.pagingdemo.data.remote;


import android.content.Context;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import kr.dkshin.android.pagingdemo.data.model.User;

/**
 * Created by SHIN on 2018. 8. 8..
 */
@Singleton
public class AppApiHelper implements ApiHelper {

    private final Context mContext;

    @Inject
    public AppApiHelper(Context context) {
        this.mContext = context;
    }

    @Override
    public Single<List<User>> requestUserList(long userId, int perPage) {
        return Rx2AndroidNetworking
                .get(ApiEndPoint.ENDPOINT_USER_LIST)
                .addQueryParameter("since", String.valueOf(userId))
                .addQueryParameter("per_page",  String.valueOf(perPage))
                .build()
                .getObjectListSingle(User.class);
    }
}
