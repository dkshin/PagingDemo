package kr.dkshin.android.pagingdemo.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by SHIN on 2018. 8. 8..
 */
@Entity(tableName = "users")
public class User {

    @Expose
    @SerializedName("id")
    @PrimaryKey
    public long id;

    @Expose
    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    public String avatarUrl;

    @Expose
    @SerializedName("login")
    @ColumnInfo(name = "login")
    public String login;

    public long getId() {
        return id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(avatarUrl, user.avatarUrl) &&
                Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, avatarUrl, login);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", login='" + login + '\'' +
                '}';
    }

    public static DiffUtil.ItemCallback<User> UserDiffCallback = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };
}
