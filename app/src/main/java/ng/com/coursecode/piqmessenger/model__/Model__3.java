package ng.com.coursecode.piqmessenger.model__;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.database__.Posts_tab;

/**
 * Created by harro on 09/10/2017.
 */

public class Model__3 implements Parcelable {

    public String text;
    String user_image;

    // @SerializedName("username")
    public String username;

    // @SerializedName("subtitle")
    public String subtitle;

    // @SerializedName("time")
    public String time;

    // @SerializedName("timestamp")
    public String timestamp;

    // @SerializedName("image")
    public String image;

    // @SerializedName("img")
    public String img;

    // @SerializedName("user_img")
    public String user_img;

    // @SerializedName("fullname")
    public String fullname;

    // @SerializedName("fav")
    public String fav;

    // @SerializedName("status_code")
    public String status_code;
    private List<Posts_tab> db_resultPosts;
    private boolean seen;
    private ArrayList<Model__3> statData;
    private boolean statFav;
    public String status_id="fskjbjtjktslj;tbobos";
    private String type;
    private int startFrom=0;

    public Model__3(){

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public List<Posts_tab> getDb_resultPosts() {
        return db_resultPosts;
    }

    public void setDb_resultPosts(List<Posts_tab> db_resultPosts) {
        this.db_resultPosts = db_resultPosts;
    }

    public boolean isSeen() {
        return seen;
    }

    public boolean getSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public ArrayList<Model__3> getStatData() {
        return statData;
    }

    public void setStatData(ArrayList<Model__3> statData) {
        this.statData = statData;
    }

    public boolean isStatFav() {
        return statFav;
    }

    public void setStatFav(boolean statFav) {
        this.statFav = statFav;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public int getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(int startFrom) {
        this.startFrom = startFrom;
    }

    protected Model__3(Parcel in) {
        text = in.readString();
        user_image = in.readString();
        username = in.readString();
        subtitle = in.readString();
        time = in.readString();
        timestamp = in.readString();
        image = in.readString();
        img = in.readString();
        startFrom=in.readInt();
        user_img = in.readString();
        fullname = in.readString();
        fav = in.readString();
        type = in.readString();
        status_code = in.readString();
        if (in.readByte() == 0x01) {
            db_resultPosts = new ArrayList<Posts_tab>();
            in.readList(db_resultPosts, Posts_tab.class.getClassLoader());
        } else {
            db_resultPosts = null;
        }
        seen = in.readByte() != 0x00;
        if (in.readByte() == 0x01) {
            statData = new ArrayList<Model__3>();
            in.readList(statData, Model__3.class.getClassLoader());
        } else {
            statData = null;
        }
        statFav = in.readByte() != 0x00;
        status_id = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(user_image);
        dest.writeString(username);
        dest.writeString(subtitle);
        dest.writeString(time);
        dest.writeString(timestamp);
        dest.writeString(image);
        dest.writeString(img);
        dest.writeInt(startFrom);
        dest.writeString(user_img);
        dest.writeString(fullname);
        dest.writeString(fav);
        dest.writeString(type);
        dest.writeString(status_code);
        if (db_resultPosts == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(db_resultPosts);
        }
        dest.writeByte((byte) (seen ? 0x01 : 0x00));
        if (statData == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(statData);
        }
        dest.writeByte((byte) (statFav ? 0x01 : 0x00));
        dest.writeString(status_id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Model__3> CREATOR = new Parcelable.Creator<Model__3>() {
        @Override
        public Model__3 createFromParcel(Parcel in) {
            return new Model__3(in);
        }

        @Override
        public Model__3[] newArray(int size) {
            return new Model__3[size];
        }
    };

    public String getType() {
        return (type==null)?Stores.IMG: type;
    }
    public String setType(String type) {
        return this.type=type;
    }
}