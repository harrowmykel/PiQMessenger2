package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("auth_username")
    @Expose
    private String authUsername;
    @SerializedName("reciv_username")
    @Expose
    private String recivUsername;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("subtitle")
    @Expose
    private String subtitle;
    @SerializedName("reply_to")
    @Expose
    private String replyTo;
    @SerializedName("phrase")
    @Expose
    private String phrase;
    @SerializedName("privacy")
    @Expose
    private String privacy;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("likes")
    @Expose
    private String likes;
    @SerializedName("liked")
    @Expose
    private String liked;
    @SerializedName("reciv_data")
    @Expose
    private RecivData recivData;
    @SerializedName("auth_data")
    @Expose
    private AuthData authData;
    @SerializedName("comments")
    @Expose
    private String comments;


    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("error_more")
    @Expose
    private String errorMore;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorMore() {
        return errorMore;
    }

    public void setErrorMore(String errorMore) {
        this.errorMore = errorMore;
    }
    /**
     * No args constructor for use in serialization
     *
     */
    public Datum() {
    }

    /**
     *
     * @param timestamp
     * @param id
     * @param time
     * @param replyTo
     * @param liked
     * @param likes
     * @param phrase
     * @param privacy
     * @param subtitle
     * @param image
     * @param authData
     * @param recivUsername
     * @param authUsername
     * @param recivData
     * @param comments
     */
    public Datum(String id, String authUsername, String recivUsername, String time, String timestamp, String subtitle, String replyTo, String phrase, String privacy, String image, String likes, String liked, RecivData recivData, AuthData authData, String comments) {
        super();
        this.id = id;
        this.authUsername = authUsername;
        this.recivUsername = recivUsername;
        this.time = time;
        this.timestamp = timestamp;
        this.subtitle = subtitle;
        this.replyTo = replyTo;
        this.phrase = phrase;
        this.privacy = privacy;
        this.image = image;
        this.likes = likes;
        this.liked = liked;
        this.recivData = recivData;
        this.authData = authData;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthUsername() {
        return authUsername;
    }

    public void setAuthUsername(String authUsername) {
        this.authUsername = authUsername;
    }

    public String getRecivUsername() {
        return recivUsername;
    }

    public void setRecivUsername(String recivUsername) {
        this.recivUsername = recivUsername;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public RecivData getRecivData() {
        return recivData;
    }

    public void setRecivData(RecivData recivData) {
        this.recivData = recivData;
    }

    public AuthData getAuthData() {
        return authData;
    }

    public void setAuthData(AuthData authData) {
        this.authData = authData;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
