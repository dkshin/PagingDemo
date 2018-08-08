package kr.dkshin.android.pagingdemo.data.repository.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import io.reactivex.disposables.CompositeDisposable;
import kr.dkshin.android.pagingdemo.data.DataManager;
import kr.dkshin.android.pagingdemo.data.model.User;
import kr.dkshin.android.pagingdemo.util.rx.SchedulerProvider;

/**
 * Created by SHIN on 2018. 8. 8..
 */
public class UserDataSourceFactory extends DataSource.Factory<Integer, User> {

    private final DataManager dataManager;
    private final SchedulerProvider schedulerProvider;
    private final CompositeDisposable compositeDisposable;

    private MutableLiveData<UserDataSource> userDataSourceMutableLiveData = new MutableLiveData<>();

    public UserDataSourceFactory(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public DataSource<Integer, User> create() {
        UserDataSource userDataSource = new UserDataSource(getDataManager(), getSchedulerProvider(), getCompositeDisposable());
        userDataSourceMutableLiveData.postValue(userDataSource);
        return userDataSource;
    }

    @NonNull
    public MutableLiveData<UserDataSource> getUserDataSourceMutableLiveData() {
        return userDataSourceMutableLiveData;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }


}
