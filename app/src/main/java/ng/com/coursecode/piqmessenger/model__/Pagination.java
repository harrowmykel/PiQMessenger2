package ng.com.coursecode.piqmessenger.model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("pages")
    @Expose
    private String pages;
    @SerializedName("curr_pages")
    @Expose
    private String currPages;
    @SerializedName("pages_left")
    @Expose
    private String pagesLeft;

    /**
     * No args constructor for use in serialization
     *
     */
    public Pagination() {
    }

    /**
     *
     * @param pagesLeft
     * @param pages
     * @param currPages
     */
    public Pagination(String pages, String currPages, String pagesLeft) {
        super();
        this.pages = pages;
        this.currPages = currPages;
        this.pagesLeft = pagesLeft;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getCurrPages() {
        return currPages;
    }

    public void setCurrPages(String currPages) {
        this.currPages = currPages;
    }

    public String getPagesLeft() {
        return pagesLeft;
    }

    public void setPagesLeft(String pagesLeft) {
        this.pagesLeft = pagesLeft;
    }

}
