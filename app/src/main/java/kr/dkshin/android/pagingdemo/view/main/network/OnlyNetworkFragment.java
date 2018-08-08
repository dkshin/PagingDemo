package kr.dkshin.android.pagingdemo.view.main.network;

import android.os.Bundle;
import android.view.View;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import kr.dkshin.android.pagingdemo.BR;
import kr.dkshin.android.pagingdemo.R;
import kr.dkshin.android.pagingdemo.data.model.User;
import kr.dkshin.android.pagingdemo.databinding.FragmentOnlynetworkBinding;
import kr.dkshin.android.pagingdemo.view.user.UserAdapter;
import kr.dkshin.android.pagingdemo.view.user.UserItemViewHolder;
import kr.dkshin.android.pagingdemo.view.base.BaseFragment;

/**
 * Created by SHIN on 2018. 8. 8..
 */
public class OnlyNetworkFragment extends BaseFragment<FragmentOnlynetworkBinding, OnlyNetworkViewModel> implements OnlyNetworkNavigator, UserItemViewHolder.UserItemViewListener {

    public static final String TAG = "OnlyNetworkFragment";

    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    UserAdapter userAdapter;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private OnlyNetworkViewModel onlyNetworkViewModel;
    private FragmentOnlynetworkBinding fragmentOnlynetworkBinding;

    public static OnlyNetworkFragment newInstance() {
        Bundle args = new Bundle();
        OnlyNetworkFragment fragment = new OnlyNetworkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_onlynetwork;
    }

    @Override
    public OnlyNetworkViewModel getViewModel() {
        onlyNetworkViewModel = ViewModelProviders.of(this, viewModelFactory).get(OnlyNetworkViewModel.class);
        return onlyNetworkViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onlyNetworkViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentOnlynetworkBinding = getViewDataBinding();
        initAdapter();
    }

    private void initAdapter() {
        fragmentOnlynetworkBinding.recyclerView.setLayoutManager(linearLayoutManager);;
        fragmentOnlynetworkBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentOnlynetworkBinding.recyclerView.setAdapter(userAdapter);
        onlyNetworkViewModel.userList.observe(this, users -> userAdapter.submitList(users));
        onlyNetworkViewModel.getRefreshState().observe(this, networkState -> {
            Logger.e("observe networkState : " + networkState);
            switch (networkState) {
                case LOADING:
                    fragmentOnlynetworkBinding.swipeRefreshLayout.setRefreshing(true);
                case ERROR:
                case LOADED:
                    fragmentOnlynetworkBinding.swipeRefreshLayout.setRefreshing(false);
                    break;
                default:
                    Logger.e("Unexpected NetworkState value: $it");
                    break;
            }
        });
        Logger.e("init onLoadUsers");
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
