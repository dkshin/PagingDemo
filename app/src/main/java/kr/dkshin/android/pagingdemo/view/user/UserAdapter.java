package kr.dkshin.android.pagingdemo.view.user;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import kr.dkshin.android.pagingdemo.R;
import kr.dkshin.android.pagingdemo.data.model.User;
import kr.dkshin.android.pagingdemo.databinding.ItemUserViewBinding;

/**
 * Created by SHIN on 2018. 6. 20..
 */
public class UserAdapter extends PagedListAdapter<User, RecyclerView.ViewHolder> {

    private UserItemViewHolder.UserItemViewListener userItemViewListener;

    public UserAdapter( UserItemViewHolder.UserItemViewListener userItemViewListener) {
        super(User.UserDiffCallback);
        this.userItemViewListener = userItemViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case R.layout.item_user_view:
                ItemUserViewBinding itemUserViewBinding = ItemUserViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new UserItemViewHolder(itemUserViewBinding, userItemViewListener);
            default:
                throw new IllegalArgumentException("unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.item_user_view:
                ((UserItemViewHolder) holder).bindTo(getItem(position));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_user_view;
    }

}