package ng.com.coursecode.piqmessenger.model__;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.database__.Group_tab;
import ng.com.coursecode.piqmessenger.database__.Messages;
import ng.com.coursecode.piqmessenger.database__.Posts_tab;
import ng.com.coursecode.piqmessenger.database__.Status_tab;

/**
 * Created by harro on 09/10/2017.
 */

public class Model__2 implements Parcelable {

    // @SerializedName("db_result")
    public List<Messages> db_result;

    // @SerializedName("db_result")
    public List<Group_tab> db_result_group;

    public List<String> gif_list;

    public List<String> img_list;

    public String text;
    String user_image;

    // @SerializedName("id")
    public String id;

    // @SerializedName("error")
    public String error;

    // @SerializedName("data")
    public List<Model__2> data;

    // @SerializedName("pagination")
    public Model__2 pagination;

    // @SerializedName("pages")
    public String pages;

    // @SerializedName("curr_pages")
    public String currPages;

    // @SerializedName("pages_left")
    public String pagesLeft;

    // @SerializedName("error_more")
    public String error_more;

    // @SerializedName("username")
    public String username;

    // @SerializedName("reciv")
    public String reciv;

    // @SerializedName("reciv_data")
    public Model__2 reciv_data;

    // @SerializedName("reciv_username")
    public String reciv_username;

    // @SerializedName("reciv_img")
    public String reciv_img;

    // @SerializedName("auth_username")
    public String auth_username;

    // @SerializedName("auth_img")
    public String auth_img;

    // @SerializedName("auth_data")
    public Model__2 auth_data;

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

    // @SerializedName("join_date")
    public String join_date;

    // @SerializedName("last_seen")
    public String last_seen;

    // @SerializedName("total_seen_time")
    public String total_seen_time;

    // @SerializedName("fullname")
    public String fullname;

    // @SerializedName("email")
    public String email;

    // @SerializedName("dob")
    public String dob;

    // @SerializedName("mob")
    public String mob;

    // @SerializedName("yob")
    public String yob;

    // @SerializedName("gender")
    public String gender;

    // @SerializedName("phone")
    public String phone;

    // @SerializedName("place")
    public String place;

    // @SerializedName("approved")
    public String approved;

    // @SerializedName("school")
    public String school;

    // @SerializedName("country")
    public String country;

    // @SerializedName("work")
    public String work;

    // @SerializedName("work")
    public String reply_to;

    // @SerializedName("dream")
    public String dream;

    // @SerializedName("hobby")
    public String hobby;

    // @SerializedName("piccoins")
    public String piccoins;

    // @SerializedName("bio")
    public String bio;

    // @SerializedName("r_sent")
    public String r_sent;

    // @SerializedName("r_rcvd")
    public String r_rcvd;

    // @SerializedName("r_frnds")
    public String r_frnds;

    // @SerializedName("t_friends")
    public String t_friends;

    // @SerializedName("fav")
    public String fav;

    // @SerializedName("status_code")
    public String status_code;
    private List<Posts_tab> db_resultPosts;
    private boolean seen;
    private ArrayList<Model__2> statData;
    private boolean statFav;
    public String status_id="fskjbjtjktslj;tbobos";

    public Model__2(){

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

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
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

    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }

    public String getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(String last_seen) {
        this.last_seen = last_seen;
    }

    public String getTotal_seen_time() {
        return total_seen_time;
    }

    public void setTotal_seen_time(String total_seen_time) {
        this.total_seen_time = total_seen_time;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getYob() {
        return yob;
    }

    public void setYob(String yob) {
        this.yob = yob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getDream() {
        return dream;
    }

    public void setDream(String dream) {
        this.dream = dream;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getPiccoins() {
        return piccoins;
    }

    public void setPiccoins(String piccoins) {
        this.piccoins = piccoins;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getR_sent() {
        return r_sent;
    }

    public void setR_sent(String r_sent) {
        this.r_sent = r_sent;
    }

    public String getR_rcvd() {
        return r_rcvd;
    }

    public void setR_rcvd(String r_rcvd) {
        this.r_rcvd = r_rcvd;
    }

    public String getR_frnds() {
        return r_frnds;
    }

    public void setR_frnds(String r_frnds) {
        this.r_frnds = r_frnds;
    }

    public String getT_friends() {
        return t_friends;
    }

    public void setT_friends(String t_friends) {
        this.t_friends = t_friends;
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


    public Model__2 getReciv_data() {
        return reciv_data;
    }

    public void setReciv_data(Model__2 reciv_data) {
        this.reciv_data = reciv_data;
    }

    public Model__2 getAuth_data() {
        return auth_data;
    }

    public void setAuth_data(Model__2 auth_data) {
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

    public List<Model__2> getData() {
        return data;
    }

    public void setData(List<Model__2> data) {
        this.data = data;
    }

    public Model__2 getPagination() {
        return pagination;
    }

    public void setPagination(Model__2 pagination) {
        this.pagination = pagination;
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

    public List<Messages> getDb_result() {
        return db_result;
    }

    public void setDb_result(List<Messages> db_result) {
        this.db_result = db_result;
    }

    public void setDb_resultPosts(List<Posts_tab> db_resultPosts) {
        this.db_resultPosts = db_resultPosts;
    }

    public List<String> getGif_list() {
        return gif_list;
    }

    public void setGif_list(List<String> gif_list) {
        this.gif_list = gif_list;
    }

    public List<String> getImg_list() {
        return img_list;
    }

    public void setImg_list(List<String> img_list) {
        this.img_list = img_list;
    }

    public List<Posts_tab> getDb_resultPosts() {
        return db_resultPosts;
    }

    public List<Group_tab> getDb_result_group() {
        return db_result_group;
    }

    public void setDb_result_group(List<Group_tab> db_result_group) {
        this.db_result_group = db_result_group;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public void setStatData(ArrayList<Model__2> statData) {
        this.statData = statData;
    }

    public boolean isSeen() {
        return seen;
    }

    public ArrayList<Model__2> getStatData() {
        return statData;
    }

    public boolean getSeen() {
        return seen;
    }

    public void setStatFav(boolean statFav) {
        this.statFav = statFav;
    }

    public boolean isStatFav() {
        return statFav;
    }


    public String getReply_to() {
        return reply_to;
    }

    public void setReply_to(String reply_to) {
        this.reply_to = reply_to;
    }

    public Model__2(Parcel in) {
        if (in.readByte() == 0x01) {
            db_result = new ArrayList<Messages>();
            in.readList(db_result, Messages.class.getClassLoader());
        } else {
            db_result = null;
        }
        if (in.readByte() == 0x01) {
            db_result_group = new ArrayList<Group_tab>();
            in.readList(db_result_group, Group_tab.class.getClassLoader());
        } else {
            db_result_group = null;
        }
        if (in.readByte() == 0x01) {
            gif_list = new ArrayList<String>();
            in.readList(gif_list, String.class.getClassLoader());
        } else {
            gif_list = null;
        }
        if (in.readByte() == 0x01) {
            img_list = new ArrayList<String>();
            in.readList(img_list, String.class.getClassLoader());
        } else {
            img_list = null;
        }
        id = in.readString();
        error = in.readString();
        if (in.readByte() == 0x01) {
            data = new ArrayList<Model__2>();
            in.readList(data, Model__2.class.getClassLoader());
        } else {
            data = null;
        }
        pagination = (Model__2) in.readValue(Model__2.class.getClassLoader());
        pages = in.readString();
        currPages = in.readString();
        pagesLeft = in.readString();
        error_more = in.readString();
        username = in.readString();
        reciv = in.readString();
        liked = in.readString();
        likes = in.readString();
        comment = in.readString();
        reciv_data = (Model__2) in.readValue(Model__2.class.getClassLoader());
        reciv_username = in.readString();
        reciv_img = in.readString();
        auth_username = in.readString();
        auth_img = in.readString();
        auth_data = (Model__2) in.readValue(Model__2.class.getClassLoader());
        userid = in.readString();
        subtitle = in.readString();
        time = in.readString();
        timestamp = in.readString();
        auth = in.readString();
        confirm = in.readString();
        image = in.readString();
        img = in.readString();
        user_img = in.readString();
        join_date = in.readString();
        last_seen = in.readString();
        total_seen_time = in.readString();
        fullname = in.readString();
        email = in.readString();
        dob = in.readString();
        mob = in.readString();
        yob = in.readString();
        gender = in.readString();
        phone = in.readString();
        place = in.readString();
        approved = in.readString();
        school = in.readString();
        country = in.readString();
        work = in.readString();
        reply_to = in.readString();
        dream = in.readString();
        hobby = in.readString();
        status_id = in.readString();
        text = in.readString();
        time = in.readString();
        piccoins = in.readString();
        bio = in.readString();
        r_sent = in.readString();
        r_rcvd = in.readString();
        r_frnds = in.readString();
        t_friends = in.readString();
        fav = in.readString();
        status_code = in.readString();
        if (in.readByte() == 0x01) {
            db_resultPosts = new ArrayList<Posts_tab>();
            in.readList(db_resultPosts, Posts_tab.class.getClassLoader());
        } else {
            db_resultPosts = null;
        }
        seen = in.readByte() != 0x00;
        if (in.readByte() == 0x01) {
            statData = new ArrayList<Model__2>();
            in.readList(statData, Status_tab.class.getClassLoader());
        } else {
            statData = null;
        }
        statFav = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (db_result == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(db_result);
        }
        if (db_result_group == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(db_result_group);
        }
        if (gif_list == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(gif_list);
        }
        if (img_list == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(img_list);
        }
        dest.writeString(id);
        dest.writeString(error);
        if (data == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(data);
        }
        dest.writeValue(pagination);
        dest.writeString(pages);
        dest.writeString(currPages);
        dest.writeString(pagesLeft);
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
        dest.writeString(join_date);
        dest.writeString(last_seen);
        dest.writeString(total_seen_time);
        dest.writeString(fullname);
        dest.writeString(email);
        dest.writeString(dob);
        dest.writeString(mob);
        dest.writeString(yob);
        dest.writeString(gender);
        dest.writeString(phone);
        dest.writeString(place);
        dest.writeString(approved);
        dest.writeString(school);
        dest.writeString(country);
        dest.writeString(work);
        dest.writeString(reply_to);
        dest.writeString(dream);
        dest.writeString(hobby);
        dest.writeString(status_id);
        dest.writeString(text);
        dest.writeString(user_image);
        dest.writeString(piccoins);
        dest.writeString(bio);
        dest.writeString(r_sent);
        dest.writeString(r_rcvd);
        dest.writeString(r_frnds);
        dest.writeString(t_friends);
        dest.writeString(fav);
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
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Model__2> CREATOR = new Parcelable.Creator<Model__2>() {
        @Override
        public Model__2 createFromParcel(Parcel in) {
            return new Model__2(in);
        }

        @Override
        public Model__2[] newArray(int size) {
            return new Model__2[size];
        }
    };

}