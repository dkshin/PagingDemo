package kr.dkshin.android.pagingdemo.data.repository.room;

import android.annotation.SuppressLint;

import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import kr.dkshin.android.pagingdemo.data.DataManager;
import kr.dkshin.android.pagingdemo.data.model.NetworkState;
import kr.dkshin.android.pagingdemo.data.model.User;
import kr.dkshin.android.pagingdemo.util.rx.SchedulerProvider;

/**
 * Created by SHIN on 2018. 8. 8..
 */
public class UserBoundaryCallback extends PagedList.BoundaryCallback<User> {

    private final DataManager dataManager;
    private final SchedulerProvider schedulerProvider;
    private final CompositeDisposable compositeDisposable;
    private MutableLiveData<NetworkState> mutableNetworkState = new MutableLiveData<>();

    private AtomicBoolean running = new AtomicBoolean(false);

    public UserBoundaryCallback(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = compositeDisposable;
        mutableNetworkState.setValue(NetworkState.LOADING);
    }

    public MutableLiveData<NetworkState> getMutableNetworkState() {
        return mutableNetworkState;
    }

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        if (!running.compareAndSet(false, true)) {
            // already loading
            Logger.w("onZeroItemsLoaded(): Already active");
            return;
        }

        mutableNetworkState.setValue(NetworkState.LOADING);

        loadAndSave(0);
    }

    @Override
    public void onItemAtEndLoaded(@NonNull User itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        if (!running.compareAndSet(false, true)) {
            Logger.w("onItemAtEndLoaded(): Already active");
            return;
        }
        loadAndSave(itemAtEnd.getId());
    }

    @SuppressLint("CheckResult")
    private void loadAndSave(long id) {
        compositeDisposable.add(
                dataManager.requestUserList(id, UserRepository.Companion.getDEFAULT_NETWORK_PAGE_SIZE())
                        .flatMap((Function<List<User>, SingleSource<?>>) users -> dataManager.insertUserList(users))
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.io())
                        .subscribe((o, throwable) -> {
                            try {
                                if (throwable == null) {
                                    mutableNetworkState.postValue(NetworkState.LOADED);
                                } else {
                                    mutableNetworkState.postValue(NetworkState.ERROR);
                                }
                            } catch (Exception e) {
                                mutableNetworkState.postValue(NetworkState.ERROR);
                                throw e;
                            } finally {
                                Logger.e("onItemAtEndLoaded(): Exiting, setting inactive");
                                running.set(false);
                            }
                        }));

    }
}
