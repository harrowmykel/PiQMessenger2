package ng.com.coursecode.piqmessenger.NetworkCalls;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.Database__.Messages;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Faker;
import ng.com.coursecode.piqmessenger.Firebasee.FirebaseInstanceIdServ;
import ng.com.coursecode.piqmessenger.HomeSta;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Model__.TimeModel;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import ng.com.coursecode.piqmessenger.Servicess.StatusCallService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static ng.com.coursecode.piqmessenger.Model__.Stores2.user_name;

/**
 * Created by harro on 24/10/2017.
 */

public class MessagesCall {

    Context context;
    public MessagesCall(Context context1) {
        super();
        context=context1;
    }


    public void getAllMessages() {
        Intent intent=new Intent(context, StatusCallService.class);
        context.startService(intent);
    }
}
