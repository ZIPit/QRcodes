package ziphome.myapplication.api;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import ziphome.myapplication.models.Attendee;
import ziphome.myapplication.models.AttendeeResp;
import ziphome.myapplication.models.AuthResponse;

public interface Api {

   // @FormUrlEncoded
    @POST("auth/login")
    Call<AuthResponse> authresp (@Body RequestBody requestBody);
//    Call<AuthResponse> authresp(
//            @Field("login") String login,
//            @Field("password") String password
//    );

    @POST("attendees")
    Call<ArrayList<AttendeeResp>> sendAtt(@Body ArrayList<Attendee> attendee);
}
