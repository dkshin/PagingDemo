package kr.dkshin.android.pagingdemo.data;

import kr.dkshin.android.pagingdemo.data.db.DbHelper;
import kr.dkshin.android.pagingdemo.data.pref.PreferencesHelper;
import kr.dkshin.android.pagingdemo.data.remote.ApiHelper;

/**
 * Created by SHIN on 2018. 8. 8..
 */
public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper {
}
