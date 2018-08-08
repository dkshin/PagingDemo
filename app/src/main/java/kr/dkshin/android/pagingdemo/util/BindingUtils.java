package kr.dkshin.android.pagingdemo.util;///*

import android.content.res.ColorStateList;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.databinding.BindingAdapter;
import kr.dkshin.android.pagingdemo.util.fresco.FrescoHelper;

/**
 * Created by amitshekhar on 11/07/17.
 */

public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter("setItemIconTint")
    public static void setItemIconTint(BottomNavigationView bottomNavigationView, ColorStateList colorStateList) {
        bottomNavigationView.setItemIconTintList(colorStateList);
    }

    @BindingAdapter("onNavigationItemSelected")
    public static void setOnNavigationItemSelectedListener(BottomNavigationView view, BottomNavigationView.OnNavigationItemSelectedListener listener) {
        view.setOnNavigationItemSelectedListener(listener);
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(SimpleDraweeView draweeView, String url) {
        FrescoHelper.loadImage(draweeView, url);
    }

}
