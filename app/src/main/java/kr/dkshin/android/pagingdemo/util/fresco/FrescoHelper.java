package kr.dkshin.android.pagingdemo.util.fresco;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.HashSet;
import java.util.Set;

import androidx.databinding.BindingAdapter;

/**
 * Created by SHIN on 2018. 7. 5..
 */
public class FrescoHelper {

    public static void frescoInitialize(Application application) {
        Set<RequestListener> requestListeners = new HashSet<>();
        requestListeners.add(new RequestLoggingListener());

        DiskCacheConfig cacheConfig = DiskCacheConfig
                .newBuilder(application.getApplicationContext())
                .setBaseDirectoryPath(application.getApplicationContext().getExternalCacheDir())
                .build();

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(application.getApplicationContext())
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .setDownsampleEnabled(true)
                .setResizeAndRotateEnabledForNetwork(true)
                .setMainDiskCacheConfig(cacheConfig)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setRequestListeners(requestListeners)
                .build();
        Fresco.initialize(application, config);

//        if(BuildConfig.DEBUG)
//            FLog.setMinimumLoggingLevel(FLog.VERBOSE);
    }

    @BindingAdapter({"assets"})
    public static void showAssetsResource(SimpleDraweeView draweeView, String name) {
        Uri uri = Uri.parse("asset:///" + name);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        draweeView.setController(controller);
    }

    @BindingAdapter("drawable")
    public static void setImageResourceID(SimpleDraweeView simpleDraweeView, int resourceID) {
        simpleDraweeView.setBackgroundResource(resourceID);
    }

    //    @BindingAdapter({"url", "ratio"})
//    public static void showImage(SimpleDraweeView view, String url, float ratio) {
//        ImageHelper.loadImage(view, url, ratio);
//    }
    public static void loadImage(SimpleDraweeView view, String url) {
        loadImage(view, url, 1);
    }

    public static void loadImage(SimpleDraweeView view, String url, float aspectRatio) {
        if (url == null) {
            return;
        } else {
            Uri uri = Uri.parse(url);
            int width = view.getWidth();
            int height = view.getHeight();
            ResizeOptions resizeOptions;
            if (width <= 0 || height <= 0) {
                resizeOptions = null;
            } else {
                resizeOptions = new ResizeOptions(width, height);
            }

            ImageRequest imageRequest = ImageRequestBuilder
                    .newBuilderWithSource(uri)
                    .setProgressiveRenderingEnabled(true)
                    .setLocalThumbnailPreviewsEnabled(true)
                    .setResizeOptions(resizeOptions)
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(imageRequest)
                    .setOldController(view.getController())
                    .build();

            view.setLegacyVisibilityHandlingEnabled(true);
            view.setAspectRatio(aspectRatio);
            view.setController(controller);
        }
    }

}
