package com.business.goldenfish.AepsSdk.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private static RetrofitClient mInstance;
//    String BASE_URL = "https://aeps.wtsnetindia.com/api/";
public static final String BASE_URL = "https://uat.goldenfishdigital.co.in/api/";
    private Retrofit retrofit;

    private RetrofitClient() {
        Gson gson = new GsonBuilder().setLenient().create();
        this.retrofit = new Retrofit.Builder().baseUrl(this.BASE_URL).client(new OkHttpClient.Builder().connectTimeout(7000, TimeUnit.SECONDS).readTimeout(7000, TimeUnit.SECONDS).build()).addConverterFactory(GsonConverterFactory.create(gson)).addConverterFactory(ScalarsConverterFactory.create()).build();
    }

    public static synchronized RetrofitClient getInstance() {
        RetrofitClient retrofitClient;
        synchronized (RetrofitClient.class) {
            if (mInstance == null) {
                mInstance = new RetrofitClient();
            }
            retrofitClient = mInstance;
        }
        return retrofitClient;
    }

    public interfaceAPI getApi() {
        return (interfaceAPI) this.retrofit.create(interfaceAPI.class);
    }
}
