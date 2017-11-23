package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecivData {

    @SerializedName("reciv_img")
    @Expose
    private String recivImg;
    @SerializedName("reciv")
    @Expose
    private String reciv;

    /**
     * No args constructor for use in serialization
     *
     */
    public RecivData() {
    }

    /**
     *
     * @param reciv
     * @param recivImg
     */
    public RecivData(String recivImg, String reciv) {
        super();
        this.recivImg = recivImg;
        this.reciv = reciv;
    }

    public String getRecivImg() {
        return recivImg;
    }

    public void setRecivImg(String recivImg) {
        this.recivImg = recivImg;
    }

    public String getReciv() {
        return reciv;
    }

    public void setReciv(String reciv) {
        this.reciv = reciv;
    }

}
