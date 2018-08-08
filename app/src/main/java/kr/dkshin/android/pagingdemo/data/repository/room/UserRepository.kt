package kr.dkshin.android.pagingdemo.data.repository.room

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import kr.dkshin.android.pagingdemo.data.DataManager
import kr.dkshin.android.pagingdemo.data.model.NetworkState
import kr.dkshin.android.pagingdemo.util.rx.SchedulerProvider

/**
 * Created by SHIN on 2018. 8. 8..
 */
class UserRepository(
        private val dataManager: DataManager,
        private val schedulerProvider: SchedulerProvider,
        private val compositeDisposable: CompositeDisposable) {

    companion object {
        val DEFAULT_NETWORK_PAGE_SIZE = 10
    }

    @MainThread
    fun loadUserList(): UserModel {
        Logger.e("getUserList")

        var boundaryCallback = UserBoundaryCallback(dataManager, schedulerProvider, compositeDisposable)
        val dataSourceFactory = dataManager.userDao.users
        val pagedList = LivePagedListBuilder(dataSourceFactory, DEFAULT_NETWORK_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()

        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = Transformations.switchMap(refreshTrigger) { refresh() }

        return UserModel(
                users = pagedList,
                networkState = boundaryCallback.mutableNetworkState, // todo: boundaryCallback.networkState
                refreshState = refreshState,
                refresh = { refreshTrigger.postValue(null) }
        )
    }

    @MainThread
    private fun refresh(): LiveData<NetworkState> {
        Logger.e("refresh")

        val networkState = MutableLiveData<NetworkState>().apply {
            value = NetworkState.LOADING
        }

        compositeDisposable.add(
                dataManager.requestUserList(0, DEFAULT_NETWORK_PAGE_SIZE)
                        .flatMap { dataManager.insertUserList(it) }
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.io())
                        .subscribe(
                                { o ->
                                    Logger.d("o = $o")
                                    networkState.postValue(NetworkState.LOADED)
                                },
                                { throwable ->
                                    // publish the error
                                    Logger.d("throwable = $throwable")
                                    networkState.postValue(NetworkState.ERROR)
                                })

        )

        return networkState
    }


}
