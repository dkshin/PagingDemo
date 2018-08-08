package kr.dkshin.android.pagingdemo.data;

import android.content.Context;

import com.google.gson.Gson;
import kr.dkshin.android.pagingdemo.data.db.DbHelper;
import kr.dkshin.android.pagingdemo.data.db.dao.UserDao;
import kr.dkshin.android.pagingdemo.data.model.User;
import kr.dkshin.android.pagingdemo.data.pref.PreferencesHelper;
import kr.dkshin.android.pagingdemo.data.remote.ApiHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by SHIN on 2018. 8. 8..
 */
public class AppDataManager implements DataManager {

    private final ApiHelper mApiHelper;

    private final Context mContext;

    private final DbHelper mDbHelper;

    private final Gson mGson;

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper, ApiHelper apiHelper, Gson gson) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
        mGson = gson;
    }

    @Override
    public UserDao getUserDao() {
        return mDbHelper.getUserDao();
    }

    @Override
    public Single<Boolean> insertUserList(List<User> collectionList) {
        return mDbHelper.insertUserList(collectionList);
    }

    @Override
    public Single<List<User>> requestUserList(long userId, int perPage) {
        return mApiHelper.requestUserList(userId, perPage);
    }
}
