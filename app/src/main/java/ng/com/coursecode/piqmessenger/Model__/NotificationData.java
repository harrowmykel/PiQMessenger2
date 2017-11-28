package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by harro on 31/10/2017.
 */

public class NotificationData {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

   /* -----------------------------------ng.com.coursecode.piqmessenger.Model__.AuthData.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class AuthData {

        @SerializedName("auth_img")
        @Expose
        private String authImg;
        @SerializedName("auth")
        @Expose
        private String auth;

        public String getAuthImg() {
            return authImg;
        }

        public void setAuthImg(String authImg) {
            this.authImg = authImg;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.AuthData_.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class AuthData_ {

        @SerializedName("auth_img")
        @Expose
        private String authImg;
        @SerializedName("auth")
        @Expose
        private String auth;

        public String getAuthImg() {
            return authImg;
        }

        public void setAuthImg(String authImg) {
            this.authImg = authImg;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.AuthData__.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class AuthData__ {

        @SerializedName("auth_img")
        @Expose
        private String authImg;
        @SerializedName("auth")
        @Expose
        private String auth;

        public String getAuthImg() {
            return authImg;
        }

        public void setAuthImg(String authImg) {
            this.authImg = authImg;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.AuthData___.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class AuthData___ {

        @SerializedName("auth")
        @Expose
        private String auth;
        @SerializedName("fullname")
        @Expose
        private String fullname;
        @SerializedName("auth_img")
        @Expose
        private String authImg;

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getAuthImg() {
            return authImg;
        }

        public void setAuthImg(String authImg) {
            this.authImg = authImg;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Data.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class Data {

        @SerializedName("status")
        @Expose
        private Status status;
        @SerializedName("del_status")
        @Expose
        private Status delstatus;

        @SerializedName("message")
        @Expose
        private Message message;
        @SerializedName("group_message")
        @Expose
        private GroupMessage groupMessage;
        @SerializedName("friends")
        @Expose
        private Friends friends;
        @SerializedName("notify")
        @Expose
        private Notify notify;

        public Status getStatus() {
            return status;
        }

        public Status getDelstatus() {
            return delstatus;
        }

        public void setDelstatus(Status delstatus) {
            this.delstatus = delstatus;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

        public GroupMessage getGroupMessage() {
            return groupMessage;
        }

        public void setGroupMessage(GroupMessage groupMessage) {
            this.groupMessage = groupMessage;
        }

        public Friends getFriends() {
            return friends;
        }

        public void setFriends(Friends friends) {
            this.friends = friends;
        }

        public Notify getNotify() {
            return notify;
        }

        public void setNotify(Notify notify) {
            this.notify = notify;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Friends.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class Friends {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("auth_username")
        @Expose
        private String authUsername;
        @SerializedName("subtitle")
        @Expose
        private String subtitle;
        @SerializedName("auth_data")
        @Expose
        private AuthData___ authData;

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

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public AuthData___ getAuthData() {
            return authData;
        }

        public void setAuthData(AuthData___ authData) {
            this.authData = authData;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.GroupMessage.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class GroupMessage {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("reciv_username")
        @Expose
        private String recivUsername;
        @SerializedName("auth_username")
        @Expose
        private String authUsername;
        @SerializedName("timestamp")
        @Expose
        private String timestamp;
        @SerializedName("subtitle")
        @Expose
        private String subtitle;
        @SerializedName("confirm")
        @Expose
        private String confirm;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("auth_data")
        @Expose
        private AuthData__ authData;
        @SerializedName("reciv_data")
        @Expose
        private RecivData_ recivData;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRecivUsername() {
            return recivUsername;
        }

        public void setRecivUsername(String recivUsername) {
            this.recivUsername = recivUsername;
        }

        public String getAuthUsername() {
            return authUsername;
        }

        public void setAuthUsername(String authUsername) {
            this.authUsername = authUsername;
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

        public AuthData__ getAuthData() {
            return authData;
        }

        public void setAuthData(AuthData__ authData) {
            this.authData = authData;
        }

        public RecivData_ getRecivData() {
            return recivData;
        }

        public void setRecivData(RecivData_ recivData) {
            this.recivData = recivData;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Message.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class Message {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("reciv_username")
        @Expose
        private String recivUsername;
        @SerializedName("auth_username")
        @Expose
        private String authUsername;
        @SerializedName("timestamp")
        @Expose
        private String timestamp;
        @SerializedName("subtitle")
        @Expose
        private String subtitle;
        @SerializedName("confirm")
        @Expose
        private String confirm;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("auth_data")
        @Expose
        private AuthData_ authData;
        @SerializedName("reciv_data")
        @Expose
        private RecivData recivData;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRecivUsername() {
            return recivUsername;
        }

        public void setRecivUsername(String recivUsername) {
            this.recivUsername = recivUsername;
        }

        public String getAuthUsername() {
            return authUsername;
        }

        public void setAuthUsername(String authUsername) {
            this.authUsername = authUsername;
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

        public AuthData_ getAuthData() {
            return authData;
        }

        public void setAuthData(AuthData_ authData) {
            this.authData = authData;
        }

        public RecivData getRecivData() {
            return recivData;
        }

        public void setRecivData(RecivData recivData) {
            this.recivData = recivData;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.NotificationData.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName; */

/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Notify.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class Notify {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("link")
        @Expose
        private String link;
        @SerializedName("subj")
        @Expose
        private String subj;
        @SerializedName("obj")
        @Expose
        private String obj;
        @SerializedName("notif_id")
        @Expose
        private String notifId;
        @SerializedName("notif_c")
        @Expose
        private String notifC;
        @SerializedName("type_of")
        @Expose
        private String typeOf;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getSubj() {
            return subj;
        }

        public void setSubj(String subj) {
            this.subj = subj;
        }

        public String getObj() {
            return obj;
        }

        public void setObj(String obj) {
            this.obj = obj;
        }

        public String getNotifId() {
            return notifId;
        }

        public String getNotifC() {
            return notifC;
        }

        public void setNotifC(String notifC) {
            this.notifC = notifC;
        }

        public void setNotifId(String notifId) {
            this.notifId = notifId;
        }

        public String getTypeOf() {
            return typeOf;
        }

        public void setTypeOf(String typeOf) {
            this.typeOf = typeOf;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.RecivData.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class RecivData {

        @SerializedName("reciv_img")
        @Expose
        private String recivImg;
        @SerializedName("reciv")
        @Expose
        private String reciv;

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
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.RecivData_.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class RecivData_ {

        @SerializedName("reciv_img")
        @Expose
        private String recivImg;
        @SerializedName("reciv")
        @Expose
        private String reciv;

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
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Status.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class Status {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("auth_username")
        @Expose
        private String authUsername;
        @SerializedName("timestamp")
        @Expose
        private String timestamp;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("subtitle")
        @Expose
        private String subtitle;
        @SerializedName("status_code")
        @Expose
        private String statusCode;
        @SerializedName("fav")
        @Expose
        private String fav;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("auth_data")
        @Expose
        private AuthData authData;

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

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getFav() {
            return fav;
        }

        public void setFav(String fav) {
            this.fav = fav;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public AuthData getAuthData() {
            return authData;
        }

        public void setAuthData(AuthData authData) {
            this.authData = authData;
        }

    }
}
