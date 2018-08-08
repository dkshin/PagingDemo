package kr.dkshin.android.pagingdemo.view.main.network;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import kr.dkshin.android.pagingdemo.data.DataManager;
import kr.dkshin.android.pagingdemo.data.model.NetworkState;
import kr.dkshin.android.pagingdemo.data.model.User;
import kr.dkshin.android.pagingdemo.data.repository.network.UserDataSource;
import kr.dkshin.android.pagingdemo.data.repository.network.UserDataSourceFactory;
import kr.dkshin.android.pagingdemo.data.repository.room.UserRepository;
import kr.dkshin.android.pagingdemo.util.rx.SchedulerProvider;
import kr.dkshin.android.pagingdemo.view.base.BaseViewModel;

public class OnlyNetworkViewModel extends BaseViewModel<OnlyNetworkNavigator> {

    LiveData<PagedList<User>> userList;
    private UserDataSourceFactory userDataSourceFactory;

    public OnlyNetworkViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        userDataSourceFactory = new UserDataSourceFactory(getDataManager(), getSchedulerProvider(), getCompositeDisposable());
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(UserRepository.Companion.getDEFAULT_NETWORK_PAGE_SIZE())
                .setInitialLoadSizeHint(UserRepository.Companion.getDEFAULT_NETWORK_PAGE_SIZE() * 2)
                .setEnablePlaceholders(false)
                .build();

        userList = new LivePagedListBuilder<>(userDataSourceFactory, config).build();
    }

    public void retry() {
        userDataSourceFactory.getUserDataSourceMutableLiveData().getValue().retry();
    }

    public void refresh() {
        userDataSourceFactory.getUserDataSourceMutableLiveData().getValue().invalidate();
    }

    public LiveData<NetworkState> getNetworkState() {
        return Transformations.switchMap(userDataSourceFactory.getUserDataSourceMutableLiveData(), UserDataSource::getNetworkState);
    }

    public LiveData<NetworkState> getRefreshState() {
        return Transformations.switchMap(userDataSourceFactory.getUserDataSourceMutableLiveData(), UserDataSource::getInitialLoad);
    }

}
