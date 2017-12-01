package ng.com.coursecode.piqmessenger.Mmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ng.com.coursecode.piqmessenger.R;

public class Menu_ extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_post)
    TextView editPost;
    @BindView(R.id.delete_post)
    TextView deletePost;
    @BindView(R.id.this_user)
    LinearLayout thisUser;
    @BindView(R.id.action_report)
    TextView actionReport;
    @BindView(R.id.nt_this_user)
    LinearLayout ntThisUser;


    String PostId="jdldn";
    public static final String ISUSERR = "jdkdlkd";
    public static String POSTIDID="jdldn";
    boolean isUser=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        PostId=getIntent().getStringExtra(POSTIDID);
        isUser=getIntent().getBooleanExtra(ISUSERR, false);
        if(isUser){
            ntThisUser.setVisibility(View.GONE);
            thisUser.setVisibility(View.VISIBLE);
        }else{
            ntThisUser.setVisibility(View.VISIBLE);
            thisUser.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.action_report)
    public void report(){

    }

    @OnClick(R.id.delete_post)
    public void delete(){

    }

    @OnClick(R.id.edit_post)
    public void edit(){

    }

}
