package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by harro on 23/11/2017.
 */

public class FrndsData {


    @SerializedName("r_sent")
    @Expose
    private String rSent;
    @SerializedName("r_rcvd")
    @Expose
    private String rRcvd;
    @SerializedName("r_frnds")
    @Expose
    private String rFrnds;

    /**
     * No args constructor for use in serialization
     *
     */
    public FrndsData() {
    }

    /**
     *
     * @param rFrnds
     * @param rSent
     * @param rRcvd
     */
    public FrndsData(String rSent, String rRcvd, String rFrnds) {
        super();
        this.rSent = rSent;
        this.rRcvd = rRcvd;
        this.rFrnds = rFrnds;
    }

    public boolean getRSent() {
        return checkPossible(rSent);//.equalsIgnoreCase("1");
    }

    public void setRSent(String rSent) {
        this.rSent = rSent;
    }

    public boolean getRRcvd() {
        return checkPossible(rRcvd);//.equalsIgnoreCase("1");
    }

    public void setRRcvd(String rRcvd) {
        this.rRcvd = rRcvd;
    }

    public boolean getRFrnds() {
        return checkPossible(rFrnds);//.equalsIgnoreCase("1");
    }

    public void setRFrnds(String rFrnds) {
        this.rFrnds = rFrnds;
    }

    public boolean checkPossible(String gh){
        return (gh!=null && !gh.isEmpty() && gh.equalsIgnoreCase("1"));
    }
}
