package ng.com.coursecode.piqmessenger.extLib;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import ng.com.coursecode.piqmessenger.R;

public class PiccImageFull extends FullScreenActivity {

    public static final String PICCMAQ_IMAGE_URL = "djhjfh";
    public static final String PICCMAQ_IS_DP = "hfvkfhgu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picc_image_full);
        String img_url=getIntent().getStringExtra(PICCMAQ_IMAGE_URL);
        boolean isDP=getIntent().getBooleanExtra(PICCMAQ_IS_DP, false);
        ImageView imgView=(ImageView)findViewById(R.id.picc_full_image);
        if(img_url!=null) {
            if(isDP){
                Piccassa.loadDp(PiccImageFull.this, img_url, imgView, true);
            }else {
                Piccassa.loadGlide(PiccImageFull.this, Uri.parse(img_url), imgView, true);
            }
        }
    }
}
