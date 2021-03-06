package ng.com.coursecode.piqmessenger.model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostsModel {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    @SerializedName("main_post")
    public Datum main_post;
    /**
     * No args constructor for use in serialization
     *
     */
    public PostsModel() {
    }

    public Datum getMain_post() {
        return main_post;
    }

    public void setMain_post(Datum main_post) {
        this.main_post = main_post;
    }

    /**
     *
     * @param data
     * @param pagination
     */
    public PostsModel(List<Datum> data, Pagination pagination) {
        super();
        this.data = data;
        this.pagination = pagination;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.RecivData.java-----------------------------------

        package ng.com.coursecode.piqmessenger.Model__;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;*/

