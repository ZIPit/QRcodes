package ziphome.myapplication.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
   //  private static final String BASE_URL = "http://seminars.go.fxtm.com/v1/";
   private static final String BASE_URL = "https://seminars.trunk.fxtm/v1/";
   //private static final String BASE_URL = "https://yandex.ru/v1/";


    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient(){
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static synchronized RetrofitClient getInstance() {
        if (mInstance==null) {
            mInstance = new RetrofitClient();

        }
        return mInstance;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
