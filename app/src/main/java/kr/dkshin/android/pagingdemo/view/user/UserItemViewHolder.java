package kr.dkshin.android.pagingdemo.view.user;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import kr.dkshin.android.pagingdemo.data.model.User;
import kr.dkshin.android.pagingdemo.databinding.ItemUserViewBinding;

/**
 * Created by SHIN on 2018. 7. 16..
 */
public class UserItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ItemUserViewBinding binding;
    private UserItemViewModel userItemViewModel;
    private User user;

    public UserItemViewListener userItemViewListener;

    public interface UserItemViewListener{
        void onClickUserItemView(User user);
    }

    public UserItemViewHolder(ItemUserViewBinding binding, UserItemViewListener userItemViewListener) {
        super(binding.getRoot());
        this.binding = binding;
        this.userItemViewListener = userItemViewListener;
    }

    public void bindTo(User user) {
        this.user = user;
        userItemViewModel = new UserItemViewModel(user);
        binding.setViewModel(userItemViewModel);

        // Immediate Binding
        // When a variable or observable changes, the binding will be scheduled to change before
        // the next frame. There are times, however, when binding must be executed immediately.
        // To force execution, use the executePendingBindings() method.
        binding.executePendingBindings();

        binding.userView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(userItemViewListener != null && user != null){
            userItemViewListener.onClickUserItemView(user);
        }

    }
}
