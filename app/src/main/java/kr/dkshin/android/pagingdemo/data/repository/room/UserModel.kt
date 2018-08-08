package kr.dkshin.android.pagingdemo.data.repository.room

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import kr.dkshin.android.pagingdemo.data.model.NetworkState
import kr.dkshin.android.pagingdemo.data.model.User

/**
 * Created by SHIN on 2018. 7. 26..
 */
data class UserModel(
        val users: LiveData<PagedList<User>>,
        val networkState: LiveData<NetworkState>,
        val refreshState: LiveData<NetworkState>,
        val refresh: () -> Unit
)
