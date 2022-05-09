package com.business.goldenfish.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

    //public static final String HOST_NAME = BuildConfig.HOST_NAME;
    //public static final String FINGER_PRINT = BuildConfig.FINGER_PRINT;
    //   public static final String TEST_URL = "https://redmilappuat.redmilbusinessmall.com/api/";
//    public static final String BASE_URL = "https://redmilappuat.redmilbusinessmall.com/api/";
    public static final String BASE_URL = "https://uat.goldenfishdigital.co.in/api/";
    public static final String IMAGE_URL = "https://uat.goldenfishdigital.co.in/";
    //    public static final String IMAGE_URL = "https://redmilappuat.redmilbusinessmall.com";
   // public static final String IMAGE_URL = BuildConfig.IMAGE_URL;
//    public static final String IMAGE_URL = "https://redmilappuat.redmilbusinessmall.com";

    //public static final String PDF_URL = BuildConfig.PDF_URL;
    //    public  static final String EMAIL_URL = "https://redmiladminuat.redmilbusinessmall.c om/";
   // public static final String EMAIL_URL = BuildConfig.EMAIL_URL;
    //    public static final String EMAIL_URL = "https://redmiladminuat.redmilbusinessmall.com/";
//    public static final String PAYMENT_GATEWAY = "http://redmiladminuat.redmilbusinessmall.com/";
  //  public static final String PAYMENT_GATEWAY = BuildConfig.PAYMENT_GATEWAY;

    private static RetrofitClient mInstance;

    private Retrofit retrofit;
//    //    Certificate Pinner

    private RetrofitClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
//        Certificate Pinner attached to okhttp
       OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS).build();
        
       /* OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS).build();*/

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }
    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }
    public interfaceAPI getApi()
    {
        return retrofit.create(interfaceAPI.class);
    }

    public MyApi getApiNew()
    {
        return retrofit.create(MyApi.class);
    }
}
