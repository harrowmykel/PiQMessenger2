package ng.com.coursecode.piqmessenger.ExtLib;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.ExtLib.staggeredgridviewdemo.views.ScaleImageView;
import ng.com.coursecode.piqmessenger.ExtLib.staggeredgridviewdemo.views.ScaleImageView2;
import ng.com.coursecode.piqmessenger.GifReplace.mViewer.PlaceHolder;
import ng.com.coursecode.piqmessenger.R;

import static android.R.attr.thumbnail;

/**
 * Created by harro on 02/11/2017.
 */

public class Piccassa {

    public static void load(Context context, String image_to_load, int placeholder, ImageView view){
        load(context, image_to_load, placeholder, placeholder, view);
    }

    public static void load(Context context, Uri image_to_load, ImageView view){
        Picasso.with(context)
                .load(image_to_load)
                .into(view);
    }

    public static void loadStatus(final Context context, final String image_to_load, final String placeholder, final ImageView view){
        Picasso.with(context)
                .load(image_to_load)
                .into(view, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(context)
                                .load(placeholder)
                                .into(view, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
                                        Picasso.with(context)
                                                .load(R.drawable.user_sample)
                                                .into(view);
                                    }
                                });
                    }
                });
    }

    public static void load(Context context, String image, int placeholder, int error, ImageView view) {
        view.setImageResource(placeholder);
        if(image!=null){
            if(!image.isEmpty()){
                Picasso.with(context).load(image).placeholder(placeholder).error(error).into(view);
            }
        }
    }

    public static void load(Context context, String image, int placeholder, int error, ImageView view, final ProgressBar prog) {
        view.setImageResource(placeholder);
//        prog.setVisibility(View.VISIBLE);
        prog.setVisibility(View.GONE);
        if(image!=null){
            if(!image.isEmpty()){
                Picasso.with(context).load(image).error(error).into(view);
            }
        }
    }

    public static void load(Context context, int image, ImageView view) {
        Picasso.with(context).load(image).into(view);
    }

    public static void load(Context context, Uri tempUri, int image, ImageView view) {
        Picasso.with(context).load(tempUri).placeholder(image).into(view);
    }

    public static void loadGlide(Context context_, String item, int nosong, ImageView imageView) {

        Glide.with(context_) // replace with 'this' if it's in activity
                .load(item)
                .into(imageView);
    }

    public static void loadGlide(Context context_, Uri item, int nosong, ImageView imageView) {
        Glide.with(context_) // replace with 'this' if it's in activity
                .load(item)
                .into(imageView);
    }

    public static void loadGlide(Context context_, Uri item, ImageView imageView) {
        Glide.with(context_) // replace with 'this' if it's in activity
                .load(item)
                .into(imageView);
    }

    public static void loadStatusFrag(Context context, String image_to_load, int placeholder, ImageView view, Callback callback){
        Picasso.with(context)
                .load(image_to_load)
                .into(view, callback);

    }
}
