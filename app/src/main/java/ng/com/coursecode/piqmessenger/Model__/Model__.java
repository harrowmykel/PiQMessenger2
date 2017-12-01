package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ng.com.coursecode.piqmessenger.Database__.Messages;

/**
 * Created by harro on 09/10/2017.
 */

public class Model__ {

    @SerializedName("verified")
    public String verified;

    @SerializedName("likes")
    public String likes;

    @SerializedName("main_post")
    public Model__ main_post;

    @SerializedName("array_replies")
    public List<Model__>replyarray;

    @SerializedName("comment")
    public String comment;

    @SerializedName("id")
    public String id;

    @SerializedName("error")
    public String error;

    @SerializedName("success")
    public String success;

    @SerializedName("data")
    public List<Model__> data;

    @SerializedName("pagination")
    public Model__ pagination;

    @SerializedName("pages")
    public String pages;

    @SerializedName("curr_pages")
    public String currPages;

    @SerializedName("pages_left")
    public String pagesLeft;

    @SerializedName("error_more")
    public String error_more;

    @SerializedName("username")
    public String username;

    @SerializedName("reciv")
    public String reciv;

    @SerializedName("reciv_data")
    public Model__ reciv_data;

    @SerializedName("reciv_username")
    public String reciv_username;

    @SerializedName("reciv_img")
    public String reciv_img;

    @SerializedName("auth_username")
    public String auth_username;

    @SerializedName("auth_img")
    public String auth_img;

    @SerializedName("auth_data")
    public Model__ auth_data;

    @SerializedName("reply_to")
    public String reply_to;

    @SerializedName("userid")
    public String userid;

    @SerializedName("subtitle")
    public String subtitle;

    @SerializedName("time")
    public String time;

    @SerializedName("timestamp")
    public String timestamp;

    @SerializedName("auth")
    public String auth;

    @SerializedName("confirm")
    public String confirm;

    @SerializedName("image")
    public String image;

    @SerializedName("img")
    public String img;

    @SerializedName("user_img")
    public String user_img;

    @SerializedName("join_date")
    public String join_date;

    @SerializedName("last_seen")
    public String last_seen;

    @SerializedName("total_seen_time")
    public String total_seen_time;

    @SerializedName("fullname")
    public String fullname;

    @SerializedName("email")
    public String email;

    @SerializedName("dob")
    public String dob;

    @SerializedName("mob")
    public String mob;

    @SerializedName("yob")
    public String yob;

    @SerializedName("gender")
    public String gender;

    @SerializedName("phone")
    public String phone;

    @SerializedName("place")
    public String place;

    @SerializedName("approved")
    public String approved;

    @SerializedName("school")
    public String school;

    @SerializedName("country")
    public String country;

    @SerializedName("work")
    public String work;

    @SerializedName("dream")
    public String dream;

    @SerializedName("hobby")
    public String hobby;

    @SerializedName("piccoins")
    public String piccoins;

    @SerializedName("bio")
    public String bio;

    @SerializedName("r_sent")
    public String r_sent;

    @SerializedName("r_rcvd")
    public String r_rcvd;

    @SerializedName("r_frnds")
    public String r_frnds;

    @SerializedName("t_friends")
    public String t_friends;

    @SerializedName("fav")
    public String fav;

    @SerializedName("status_code")
    public String status_code;

    @SerializedName("liked")
    public String liked;

    @SerializedName("frnds_data")
    @Expose
    private FrndsData frndsData;

    public FrndsData getFrndsData() {
        return frndsData;
    }

    public void setFrndsData(FrndsData frndsData) {
        this.frndsData = frndsData;
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

    public Model__ getReciv_data() {
        return reciv_data;
    }

    public void setReciv_data(Model__ reciv_data) {
        this.reciv_data = reciv_data;
    }

    public Model__ getAuth_data() {
        return auth_data;
    }

    public void setAuth_data(Model__ auth_data) {
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

    public List<Model__> getData() {
        return data;
    }

    public void setData(List<Model__> data) {
        this.data = data;
    }

    public Model__ getPagination() {
        return pagination;
    }

    public void setPagination(Model__ pagination) {
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

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Model__ getMain_post() {
        return main_post;
    }

    public void setMain_post(Model__ main_post) {
        this.main_post = main_post;
    }

    public List<Model__> getReplyArray() {
        return replyarray;
    }

    public void setReplyArray(List<Model__> replyarray) {
        this.replyarray = replyarray;
    }

    public List<Model__> getReplyarray() {
        return replyarray;
    }

    public void setReplyarray(List<Model__> replyarray) {
        this.replyarray = replyarray;
    }

    public String getReply_to() {
        return reply_to;
    }

    public void setReply_to(String reply_to) {
        this.reply_to = reply_to;
    }

    public boolean getVerified() {
        return (verified!=null && verified.trim().equalsIgnoreCase("1"));
    }

    public void setVerified(String reply_to) {
        this.verified = reply_to;
    }



}
