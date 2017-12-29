package ng.com.coursecode.piqmessenger.extLib;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ng.com.coursecode.piqmessenger.R;

public class PiccImageFull extends FullScreenActivity {

    public static final String PICCMAQ_IMAGE_URL = "djhjfh";
    public static final String PICCMAQ_IS_DP = "hfvkfhgu";
    Context context;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picc_image_full);
        ButterKnife.bind(this);
        context = PiccImageFull.this;
        String img_url = getIntent().getStringExtra(PICCMAQ_IMAGE_URL);
        boolean isDP = getIntent().getBooleanExtra(PICCMAQ_IS_DP, false);
        ImageView imgView = (ImageView) findViewById(R.id.picc_full_image);
        if (img_url != null && !img_url.isEmpty()) {
            if (isDP) {
                Picasso.with(context)
                        .load(img_url)
                        .into(imgView, new Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar2.setVisibility(View.GONE);
                            }
                            @Override
                            public void onError() {
                                progressBar2.setVisibility(View.GONE);
                                Toast.makeText(context, R.string.unable_to_load_image, Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Glide.with(context).load(img_url).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar2.setVisibility(View.GONE);
                        Toast.makeText(context, R.string.unable_to_load_image, Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar2.setVisibility(View.GONE);
                        return false;
                    }
                }).into(imgView);
            }
        }else{
            progressBar2.setVisibility(View.GONE);
            Toast.makeText(context, R.string.unable_to_load_image, Toast.LENGTH_SHORT).show();
        }
    }
}
