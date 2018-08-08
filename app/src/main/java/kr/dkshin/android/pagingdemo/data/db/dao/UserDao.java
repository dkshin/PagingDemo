package kr.dkshin.android.pagingdemo.data.db.dao;

import kr.dkshin.android.pagingdemo.data.model.User;

import java.util.List;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * Created by SHIN on 2018. 8. 8..
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    DataSource.Factory<Integer, User> getUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(List<User> entities);
}
