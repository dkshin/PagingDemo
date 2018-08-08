package kr.dkshin.android.pagingdemo.data.repository.network;

import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.ItemKeyedDataSource;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import kr.dkshin.android.pagingdemo.data.DataManager;
import kr.dkshin.android.pagingdemo.data.model.NetworkState;
import kr.dkshin.android.pagingdemo.data.model.User;
import kr.dkshin.android.pagingdemo.util.rx.SchedulerProvider;

/**
 * Created by SHIN on 2018. 8. 8..
 */
public class UserDataSource extends ItemKeyedDataSource<Integer, User> {

    private final DataManager dataManager;
    private final SchedulerProvider schedulerProvider;
    private final CompositeDisposable compositeDisposable;

    private MutableLiveData<NetworkState> networkState = new MutableLiveData<>();
    private MutableLiveData<NetworkState> initialLoad = new MutableLiveData<>();

    private Completable retryCompletable;

    public UserDataSource(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = compositeDisposable;
    }

    public void retry() {
        if (retryCompletable != null) {
            compositeDisposable
                    .add(retryCompletable
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.io())
                            .subscribe(() -> {
                            }, throwable -> Logger.e(throwable.getMessage()))
                    );
        }
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<User> callback) {
        networkState.postValue(NetworkState.LOADING);
        initialLoad.postValue(NetworkState.LOADING);

        compositeDisposable.add(dataManager.requestUserList(0, params.requestedLoadSize)
                .subscribe(userList -> {
                            // clear retry since last request succeeded
                            setRetry(null);
                            networkState.postValue(NetworkState.LOADED);
                            initialLoad.postValue(NetworkState.LOADED);
                            callback.onResult(userList);
                        },
                        throwable -> {
                            // keep a Completable for future retry
                            setRetry(() -> loadInitial(params, callback));
                            // publish the error
                            networkState.postValue(NetworkState.ERROR);
                            initialLoad.postValue(NetworkState.ERROR);
                        }));
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<User> callback) {
        networkState.postValue(NetworkState.LOADING);

        //get the users from the api after id
        compositeDisposable.add(dataManager.requestUserList(params.key, params.requestedLoadSize)
                .subscribe(userList -> {
                            // clear retry since last request succeeded
                            setRetry(null);
                            networkState.postValue(NetworkState.LOADED);
                            callback.onResult(userList);
                        },
                        throwable -> {
                            // keep a Completable for future retry
                            setRetry(() -> loadAfter(params, callback));
                            // publish the error
                            networkState.postValue(NetworkState.ERROR);
                        }));
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<User> callback) {
        // ignored, since we only ever append to our initial load
    }

    /**
     * The id field is a unique identifier for users.
     */
    @NonNull
    @Override
    public Integer getKey(@NonNull User item) {
        return (int) item.getId();
    }

    @NonNull
    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    @NonNull
    public MutableLiveData<NetworkState> getInitialLoad() {
        return initialLoad;
    }

    private void setRetry(final Action action) {
        if (action == null) {
            this.retryCompletable = null;
        } else {
            this.retryCompletable = Completable.fromAction(action);
        }
    }
}
