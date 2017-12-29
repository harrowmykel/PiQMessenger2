package ng.com.coursecode.piqmessenger.dialog_;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.extLib.Toasta;
import ng.com.coursecode.piqmessenger.fragments_.Chats;
import ng.com.coursecode.piqmessenger.interfaces.SendDatum;
import ng.com.coursecode.piqmessenger.interfaces.ServerError;
import ng.com.coursecode.piqmessenger.model__.Model__;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by harro on 01/12/2017.
 */

public class SearchDialog implements View.OnClickListener{


    Context context;
    Dialog dialog;
    boolean currentIsCurrentPass=true;
    MaterialEditText materialEditText;
    String curr_pass;
    TextView title;
    Stores stores;
    String editText;
    SendDatum sendDatum;

    public SearchDialog(Context context, SendDatum sendDatum1) {
        this.context=context;
        sendDatum=sendDatum1;
        stores=(new Stores(context));
        curr_pass=stores.getPass();
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.search);
        View view=dialog.findViewById(R.id.parent_view);

        View lCancel=view.findViewById(R.id.cancel);
        View lDone=view.findViewById(R.id.action_done);
        materialEditText=(MaterialEditText)view.findViewById(R.id.pass_editext);
        title=(TextView) view.findViewById(R.id.title);

        lDone.setOnClickListener(this);
        lCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id) {
            case R.id.cancel:
                sendDatum.send(""+Chats.REFRESH);
                break;
            case R.id.action_done:
                editText=materialEditText.getText().toString().trim();
                if(!editText.isEmpty()){
                    sendDatum.send(editText);
                }else{
                    sendDatum.send(""+Chats.REFRESH);
                }
                break;
        }
        dialog.dismiss();
    }

    public void show(){
        dialog.show();
    }

}
