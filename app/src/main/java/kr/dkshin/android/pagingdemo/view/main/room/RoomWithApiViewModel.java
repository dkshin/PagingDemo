package kr.dkshin.android.pagingdemo.view.main.room;


import androidx.arch.core.util.Function;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;
import kr.dkshin.android.pagingdemo.data.DataManager;
import kr.dkshin.android.pagingdemo.data.model.NetworkState;
import kr.dkshin.android.pagingdemo.data.model.User;
import kr.dkshin.android.pagingdemo.data.repository.room.UserModel;
import kr.dkshin.android.pagingdemo.data.repository.room.UserRepository;
import kr.dkshin.android.pagingdemo.util.rx.SchedulerProvider;
import kr.dkshin.android.pagingdemo.view.base.BaseViewModel;

public class RoomWithApiViewModel extends BaseViewModel<RoomWithApiNavigator> {

    private UserRepository userRepository;
    private MutableLiveData<String> poke = new MutableLiveData<>();

    public RoomWithApiViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        userRepository = new UserRepository(dataManager, schedulerProvider, getCompositeDisposable());
    }

    public LiveData<PagedList<User>> getUsers() {
        return Transformations.switchMap(userModelResult, input -> input.getUsers());
    }

    private LiveData<UserModel> userModelResult = Transformations.map(poke, new Function<String, UserModel>() {
        @Override
        public UserModel apply(String input) {
            return userRepository.loadUserList();
        }
    });

    public LiveData<NetworkState> getRefreshState() {
        return Transformations.switchMap(userModelResult, input -> input.getRefreshState());
    }

    public void loadUsers(LifecycleOwner lifecycleOwner) {
        userModelResult.removeObservers(lifecycleOwner);
        poke.setValue(null);
    }

    public void refresh() {
        userModelResult.getValue().getRefresh().invoke();
    }

}
