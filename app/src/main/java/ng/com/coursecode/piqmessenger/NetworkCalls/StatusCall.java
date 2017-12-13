package ng.com.coursecode.piqmessenger.NetworkCalls;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.Database__.Messages;
import ng.com.coursecode.piqmessenger.Database__.Status_tab;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Interfaces.FetchMore;
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

/**
 * Created by harro on 24/10/2017.
 */

public class StatusCall {


    Context context;

    public StatusCall(Context context1) {
        context=context1;
    }


    public void getAllMessages() {
        Intent intent=new Intent(context, StatusCallService.class);
        intent.putExtra(Stores.TYPE_OF_ACTION, StatusCallService.GET_MSG);
        context.startService(intent);
    }
    public void clear() {
        Intent intent=new Intent(context, StatusCallService.class);
        intent.putExtra(Stores.TYPE_OF_ACTION, StatusCallService.CLEAR);
        intent.putExtra(StatusCallService.CLEAR, true);
        context.startService(intent);
    }

    public void getAllMessages(FetchMore fetchMore) {
        getAllMessages();
    }

    public void getAllDelMessages() {
        Intent intent=new Intent(context, StatusCallService.class);
        intent.putExtra(Stores.TYPE_OF_ACTION, StatusCallService.DEL);
        intent.putExtra(StatusCallService.DEL, true);
        context.startService(intent);
    }

    public void hasViewed(ArrayList<String> all_updates) {
        Intent intent=new Intent(context, StatusCallService.class);
        intent.putExtra(StatusCallService.HAS_VIEWED, true);
        intent.putExtra(Stores.TYPE_OF_ACTION, StatusCallService.HAS_VIEWED);
        intent.putExtra(StatusCallService.STATCODES, all_updates);
        context.startService(intent);
    }
}
