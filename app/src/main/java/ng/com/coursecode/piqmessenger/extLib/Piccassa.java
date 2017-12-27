package ng.com.coursecode.piqmessenger.extLib;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 02/11/2017.
 */

public class Piccassa {

    public static void load(Context context, String image_to_load, int placeholder, ImageView view){
        load(context, image_to_load, view);
    }

    public static void loadDp(Context context, String image_to_load, ImageView view){
        //0 = all, 1=only prof_pic, only posts
        loadDp(context, image_to_load, view, false);
    }

    public static void loadDp(final Context context, final String image_to_load, ImageView view, boolean b){
        setUpLongView(context, image_to_load, view, true);
        //0 = all, 1=only prof_pic, only posts
        int bae=Stores.parseInt(Prefs.getString("load_images1", "0"));
        if(image_to_load.trim().isEmpty()){
            Picasso.with(context)
                    .load(R.drawable.user_sample)
                    .into(view);
        }else if(!((bae==0) || bae==1)){
            if(!b) {
                Picasso.with(context)
                        .load(R.drawable.user_sample)
                        .into(view);
                Toasta.makeText(context, R.string.load_warning, Toast.LENGTH_SHORT);
            }
        }else {
            Picasso.with(context)
                    .load(image_to_load)
                    .placeholder(R.drawable.user_sample)
                    .error(R.drawable.user_sample)
                    .into(view);
        }
//        setUpLongView(context, image_to_load, view, true);
    }

    public static void load(Context context, Uri image_to_load, ImageView view){
        loadGlide(context, image_to_load, view);
    }

    public static void loadStatus(final Context context, final String image_to_load, final String placeholder, final ImageView view){
        setUpLongView(context, image_to_load, view, true);
        //0 = all, 1=only prof_pic, only posts
        int bae=Stores.parseInt(Prefs.getString("load_images1", "0"));
        if(image_to_load.trim().isEmpty()){
            Picasso.with(context)
                    .load(R.drawable.user_sample)
                    .into(view);
            return;
        }else if(!((bae==0) || bae==1)){
            Picasso.with(context)
                    .load(R.drawable.user_sample)
                    .into(view);
            Toasta.makeText(context, R.string.load_warning, Toast.LENGTH_SHORT);
            return;
        }
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
                                .error(R.drawable.user_sample)
                                .into(view);
                    }
                });
    }

    private static void setUpLongView(final Context context, final String image_to_load, ImageView view, final boolean b) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent=new Intent(context, PiccImageFull.class);
                intent.putExtra(PiccImageFull.PICCMAQ_IMAGE_URL, image_to_load);
                intent.putExtra(PiccImageFull.PICCMAQ_IS_DP, b);
                context.startActivity(intent);
                return false;
            }
        });
    }

    public static void load(Context context, String image, int placeholder, int error, ImageView view) {
        loadGlide(context, Uri.parse(image), view);
    }

    public static void load(Context context, String image, int placeholder, int error, ImageView view, final ProgressBar prog) {
        prog.setVisibility(View.GONE);
        loadGlide(context, Uri.parse(image), view);
    }

    public static void load(Context context, int image, ImageView view) {
        Picasso.with(context).load(image).into(view);
    }

    public static void load(Context context, Uri tempUri, int image, ImageView view) {
        loadGlide(context, tempUri, view);
    }

    public static void loadGlide(Context context_, String item, int nosong, ImageView view) {
        loadGlide(context_, Uri.parse(item), view);
    }

    public static void loadGlide(Context context, Uri item, int nosong, ImageView view) {
        loadGlide(context, item, view);
    }

    public static void loadGlide(Context context, Uri item, ImageView view) {
        String img=item.toString().trim();
        if(img.isEmpty()){
            return;
        }
        int thumbnail= Stores.getLoader(context);//.with(imageView
        loadGlide(context, item, thumbnail, view, false);
    }

    public static void loadGlide(Context context, Uri item, int thumbnail, ImageView view, boolean b) {
        //0 = all, 1=only prof_pic, only posts
        setUpLongView(context, item.toString(), view, false);
        if(thumbnail==R.drawable.going_out_add_status_plus){
            thumbnail=R.drawable.ic_forward_screen_mystory;
        }
        int bae=Stores.parseInt(Prefs.getString("load_images1", "0"));
        if((!((bae==0) || bae==2))){
            if(!b) {
                Glide.with(context)
                        .load(thumbnail)
                        .into(view);
                Toasta.makeText(context, R.string.load_warning, Toast.LENGTH_SHORT);
                return;
            }
        }
        Glide.with(context).load(item)
                .thumbnail(Glide.with(context).load(thumbnail))
                .into(view);
    }

    public static void loadStatusFrag(Context context, String image_to_load, int placeholder, ImageView view, Callback callback){
        loadStatusFrag(context, Uri.parse(image_to_load), placeholder, view, callback);
    }

    public static void loadStatusFrag(Context context, Uri image_to_load, int placeholder, ImageView view, final Callback callback){
        setUpLongView(context, image_to_load.toString(), view, false);
        String img=image_to_load.toString().trim();
        if(img.isEmpty()){
            return;
        }
        Glide.with(context) // replace with 'this' if it's in activity
                .load(image_to_load)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        callback.onError();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        callback.onSuccess();
                        return false;
                    }
                })
                .into(view);
//        setUpLongView(context, image_to_load.toString(), view, false);
    }

    public static void load(Context context, String image_to_load, ImageView view){
        loadGlide(context, Uri.parse(image_to_load), view);
    }

    public static void loadGlide(Context context, Uri item, ImageView view, boolean b) {
        loadGlide(context, item, R.drawable.ic_forward_screen_mystory, view, b);
    }
}