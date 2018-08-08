package kr.dkshin.android.pagingdemo.view.main.room;

import android.os.Bundle;
import android.view.View;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;
import kr.dkshin.android.pagingdemo.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import kr.dkshin.android.pagingdemo.R;
import kr.dkshin.android.pagingdemo.data.model.User;
import kr.dkshin.android.pagingdemo.databinding.FragmentRoomwithapiBinding;
import kr.dkshin.android.pagingdemo.view.user.UserAdapter;
import kr.dkshin.android.pagingdemo.view.user.UserItemViewHolder;
import kr.dkshin.android.pagingdemo.view.base.BaseFragment;

/**
 * Created by SHIN on 2018. 8. 8..
 */
public class RoomWithApiFragment extends BaseFragment<FragmentRoomwithapiBinding, RoomWithApiViewModel> implements RoomWithApiNavigator, UserItemViewHolder.UserItemViewListener {

    public static final String TAG = "RoomWithApiFragment";

    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    UserAdapter userAdapter;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private RoomWithApiViewModel roomWithApiViewModel;
    private FragmentRoomwithapiBinding fragmentRoomwithapiBinding;

    public static RoomWithApiFragment newInstance() {
        Bundle args = new Bundle();
        RoomWithApiFragment fragment = new RoomWithApiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_roomwithapi;
    }

    @Override
    public RoomWithApiViewModel getViewModel() {
        roomWithApiViewModel = ViewModelProviders.of(this, viewModelFactory).get(RoomWithApiViewModel.class);
        return roomWithApiViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomWithApiViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentRoomwithapiBinding = getViewDataBinding();
        initAdapter();
    }

    private void initAdapter() {
        fragmentRoomwithapiBinding.recyclerView.setLayoutManager(linearLayoutManager);
        fragmentRoomwithapiBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentRoomwithapiBinding.recyclerView.setAdapter(userAdapter);
        roomWithApiViewModel.getUsers().observe(this, users -> userAdapter.submitList(users));
        roomWithApiViewModel.getRefreshState().observe(this, networkState -> {
            Logger.e("observe networkState : " + networkState);
            switch (networkState) {
                case LOADING:
                    fragmentRoomwithapiBinding.swipeRefreshLayout.setRefreshing(true);
                case ERROR:
                case LOADED:
                    fragmentRoomwithapiBinding.swipeRefreshLayout.setRefreshing(false);
                    break;
                default:
                    Logger.e("Unexpected NetworkState value: $it");
                    break;
            }
        });
        Logger.e("init onLoadUsers");
        onLoadUsers();
    }

    @Override
    public void onLoadUsers() {
        roomWithApiViewModel.loadUsers(this);
    }

    @Override
    public void handleError(Throwable throwable) {
        Logger.e("throwable = " + throwable);
    }

    @Override
    public void onClickUserItemView(User user) {
        Logger.w("user = " + user);
    }
}