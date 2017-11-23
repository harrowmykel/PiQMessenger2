package ng.com.coursecode.piqmessenger.Model__;

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

    /**
     * No args constructor for use in serialization
     *
     */
    public PostsModel() {
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

