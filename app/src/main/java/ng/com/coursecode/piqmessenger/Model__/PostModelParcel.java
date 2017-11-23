package ng.com.coursecode.piqmessenger.Model__;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.Database__.Group_tab;
import ng.com.coursecode.piqmessenger.Database__.Messages;
import ng.com.coursecode.piqmessenger.Database__.Posts_tab;
import ng.com.coursecode.piqmessenger.Database__.Status_tab;

/**
 * Created by harro on 09/10/2017.
 */

public class PostModelParcel implements Parcelable {

    public String text;
    String user_image;

    // @SerializedName("id")
    public String id;

    // @SerializedName("error")
    public String error;

    // @SerializedName("data")
    public List<PostModelParcel> data;

    // @SerializedName("pagination")

    // @SerializedName("username")
    public String username;

    // @SerializedName("reciv")
    public String reciv;

    // @SerializedName("reciv_data")
    public PostModelParcel reciv_data;

    // @SerializedName("reciv_username")
    public String reciv_username;

    // @SerializedName("reciv_img")
    public String reciv_img;

    // @SerializedName("auth_username")
    public String auth_username;

    // @SerializedName("auth_img")
    public String auth_img;

    // @SerializedName("auth_data")
    public PostModelParcel auth_data;

    // @SerializedName("userid")
    public String userid;
    public String likes;
    public String liked;
    public String comment;

    // @SerializedName("subtitle")
    public String subtitle;

    // @SerializedName("time")
    public String time;

    // @SerializedName("timestamp")
    public String timestamp;

    // @SerializedName("auth")
    public String auth;

    // @SerializedName("confirm")
    public String confirm;

    // @SerializedName("image")
    public String image;

    // @SerializedName("img")
    public String img;

    // @SerializedName("user_img")
    public String user_img;


    // @SerializedName("fullname")
    public String fullname;

    // @SerializedName("email")
    public String email;

    // @SerializedName("work")
    public String reply_to;
    public String error_more;


    public PostModelParcel(){

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
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

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAuth_username() {
        return auth_username;
    }

    public void setAuth_username(String auth_username) {
        this.auth_username = auth_username;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReciv() {
        return reciv;
    }

    public void setReciv(String reciv) {
        this.reciv = reciv;
    }

    public String getReciv_username() {
        return reciv_username;
    }

    public void setReciv_username(String reciv_username) {
        this.reciv_username = reciv_username;
    }

    public String getReciv_img() {
        return reciv_img;
    }

    public void setReciv_img(String reciv_img) {
        this.reciv_img = reciv_img;
    }

    public String getAuth_img() {
        return auth_img;
    }

    public void setAuth_img(String auth_img) {
        this.auth_img = auth_img;
    }


    public PostModelParcel getReciv_data() {
        return reciv_data;
    }

    public void setReciv_data(PostModelParcel reciv_data) {
        this.reciv_data = reciv_data;
    }

    public PostModelParcel getAuth_data() {
        return auth_data;
    }

    public void setAuth_data(PostModelParcel auth_data) {
        this.auth_data = auth_data;
    }

    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }

    public String getError_more() {
        return error_more;
    }

    public void setError_more(String error_more) {
        this.error_more = error_more;
    }

    public List<PostModelParcel> getData() {
        return data;
    }

    public void setData(List<PostModelParcel> data) {
        this.data = data;
    }


    public String getReply_to() {
        return reply_to;
    }

    public void setReply_to(String reply_to) {
        this.reply_to = reply_to;
    }

    public PostModelParcel(Parcel in) {
        id = in.readString();
        error = in.readString();
        if (in.readByte() == 0x01) {
            data = new ArrayList<PostModelParcel>();
            in.readList(data, PostModelParcel.class.getClassLoader());
        } else {
            data = null;
        }
        error_more = in.readString();
        username = in.readString();
        reciv = in.readString();
        liked = in.readString();
        likes = in.readString();
        comment = in.readString();
        reciv_data = (PostModelParcel) in.readValue(PostModelParcel.class.getClassLoader());
        reciv_username = in.readString();
        reciv_img = in.readString();
        auth_username = in.readString();
        auth_img = in.readString();
        auth_data = (PostModelParcel) in.readValue(PostModelParcel.class.getClassLoader());
        userid = in.readString();
        subtitle = in.readString();
        time = in.readString();
        timestamp = in.readString();
        auth = in.readString();
        confirm = in.readString();
        image = in.readString();
        img = in.readString();
        user_img = in.readString();
        fullname = in.readString();
        email = in.readString();
        reply_to = in.readString();
        text = in.readString();
        time = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(error);
        if (data == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(data);
        }
        dest.writeString(error_more);
        dest.writeString(username);
        dest.writeString(reciv);
        dest.writeString(liked);
        dest.writeString(likes);
        dest.writeString(comment);
        dest.writeValue(reciv_data);
        dest.writeString(reciv_username);
        dest.writeString(reciv_img);
        dest.writeString(auth_username);
        dest.writeString(auth_img);
        dest.writeValue(auth_data);
        dest.writeString(userid);
        dest.writeString(subtitle);
        dest.writeString(time);
        dest.writeString(timestamp);
        dest.writeString(auth);
        dest.writeString(confirm);
        dest.writeString(image);
        dest.writeString(img);
        dest.writeString(user_img);
        dest.writeString(fullname);
        dest.writeString(email);
        dest.writeString(reply_to);
        dest.writeString(text);
        dest.writeString(user_image);
    }

    @SuppressWarnings("unused")
    public static final Creator<PostModelParcel> CREATOR = new Creator<PostModelParcel>() {
        @Override
        public PostModelParcel createFromParcel(Parcel in) {
            return new PostModelParcel(in);
        }

        @Override
        public PostModelParcel[] newArray(int size) {
            return new PostModelParcel[size];
        }
    };

}