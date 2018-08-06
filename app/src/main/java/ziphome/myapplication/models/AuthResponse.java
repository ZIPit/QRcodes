package ziphome.myapplication.models;

public class AuthResponse {
    private String access_token;
    private Integer expires_in;

    public AuthResponse(String access_token, Integer expires_in) {
        this.access_token = access_token;
        this.expires_in = expires_in;
    }


    public String getAccess_token() {
        return access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }
}
