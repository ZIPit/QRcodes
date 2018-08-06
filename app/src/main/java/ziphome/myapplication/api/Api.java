package ziphome.myapplication.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import ziphome.myapplication.models.AuthResponse;

public interface Api {
@FormUrlEncoded
@POST("authresp")
    Call<AuthResponse> authresp(
            @Field("login") String login,
            @Field("password") String password
);
}
