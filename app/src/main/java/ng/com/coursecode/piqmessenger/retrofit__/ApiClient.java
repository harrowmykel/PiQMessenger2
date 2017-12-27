package ng.com.coursecode.piqmessenger.retrofit__;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by harro on 09/10/2017.
 */

public class ApiClient {
    public static final String BASE_URL = "http://piccmaq.com.ng/lock/apiii/";
    public static final String BASE_GIF_URL = "https://api.tenor.com/v1/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit1 = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            Gson gson=new GsonBuilder()
                    .setLenient().create();
            GsonConverterFactory gsonConverterFactory=(GsonConverterFactory.create(gson));
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getGifClient() {
        if (retrofit1==null) {
            Gson gson=new GsonBuilder()
                    .setLenient().create();
            GsonConverterFactory gsonConverterFactory=(GsonConverterFactory.create(gson));
            retrofit1 = new Retrofit.Builder()
                    .baseUrl(BASE_GIF_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
        }
        return retrofit1;
    }
}
