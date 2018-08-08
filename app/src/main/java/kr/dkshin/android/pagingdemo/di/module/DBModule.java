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

package kr.dkshin.android.pagingdemo.di.module;

import android.content.Context;
import kr.dkshin.android.pagingdemo.data.db.AppDatabase;
import kr.dkshin.android.pagingdemo.data.db.AppDbHelper;
import kr.dkshin.android.pagingdemo.data.db.DbHelper;
import kr.dkshin.android.pagingdemo.di.DatabaseInfo;
import kr.dkshin.android.pagingdemo.util.AppConstants;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;


/**
 * Created by amitshekhar on 07/07/17.
 */
@Module
public class DBModule {

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

}
