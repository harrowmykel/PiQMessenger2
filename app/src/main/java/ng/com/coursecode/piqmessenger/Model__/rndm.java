package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.SerializedName;

/**
 * Created by harro on 18/12/2017.
 */

public class rndm {
    @SerializedName("id")
    public String id;
    @SerializedName("time")
    public String time;
    @SerializedName("timestamp")
    public String timestamp;
    @SerializedName("auth_username")
    public String authUsername;
    @SerializedName("auth_data")
    public AuthData authData;
}
