package ng.com.coursecode.piqmessenger.model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthData {

    @SerializedName("auth_img")
    @Expose
    private String authImg;
    @SerializedName("auth")
    @Expose
    private String auth;

    /**
     * No args constructor for use in serialization
     *
     */
    public AuthData() {
    }

    /**
     *
     * @param authImg
     * @param auth
     */
    public AuthData(String authImg, String auth) {
        super();
        this.authImg = authImg;
        this.auth = auth;
    }

    public String getAuthImg() {
        return authImg;
    }

    public void setAuthImg(String authImg) {
        this.authImg = authImg;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

}
