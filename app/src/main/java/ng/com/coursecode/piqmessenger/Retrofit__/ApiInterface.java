package ng.com.coursecode.piqmessenger.Retrofit__;

import java.util.List;

import ng.com.coursecode.piqmessenger.Model__.Gif__;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.PostsModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by harro on 09/10/2017.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("msgs/index.php?req=fetchfrom")
    Call<Model__> getAllMessages(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("time") String time, @Field("page") String page);

    @FormUrlEncoded
    @POST("msgs/index.php")
    Call<Model__> sendNewMessages(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("msg") String msg);

    @FormUrlEncoded
    @POST("status/index.php?req=fetchfrom")
    Call<Model__> getAllStatuses(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("time") String time, @Field("page") String page);

    @FormUrlEncoded
    @POST("posts/index.php?req=fetchfrom")
    Call<PostsModel> getAllPosts(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("q") String q, @Field("page") String page);

    @FormUrlEncoded
    @POST("profile/index.php?req=about")
    Call<Model__> getUser(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("who") String who);

    @FormUrlEncoded
    @POST("posts/index.php?req=userposts")
    Call<PostsModel> getUsersPosts(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("page") String page, @Field("who") String who);

    @FormUrlEncoded
    @POST("index.php?msg=msg")
    Call<Model__> getAllDiscover(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("time") String time);

    @FormUrlEncoded
    @POST("groups/index.php?req=search")
    Call<Model__> searchGroups(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("q") String search, @Field("page") String page);

    @FormUrlEncoded
    @POST("profile/index.php?req=search")
    Call<Model__> searchUsers(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("q") String search, @Field("location") String location, @Field("page") String page);

    @FormUrlEncoded
    @POST("status/index.php?req=getallviewers")
    Call<Model__> searchStatusUsers(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("q") String search, @Field("status_code") String status_code, @Field("page") String page);

    @GET("search?as=wq")
    Call<Gif__> searchGifs(@Query("q") String search, @Query("key") String key,  @Query("pos") String pos,  @Query("limit") String limit,  @Query("safesearch") String safesearch);

    @GET("trending?as=wq")
    Call<Gif__> trendingGif(@Query("key") String key, @Query("limit") String limit);

    @FormUrlEncoded
    @POST("posts/index.php?req=create")
    Call<Model__> newPost(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("text") String text, @Field("imgurl") String q, @Field("privacy") String privacy, @Field("who") String who,  @Field("postid") String location);

    @FormUrlEncoded
    @POST("status/index.php?req=create")
    Call<Model__> newStatus(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("text") String text, @Field("imgurl") String q);

    @FormUrlEncoded
    @POST("profile/index.php?req=edit")
    Call<Model__> editProfile(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("fullname") String fname, @Field("bio") String bio, @Field("imgurl") String q);

    @FormUrlEncoded
    @POST("services/index.php?req=create")
    Call<Model__> sendToken(@Field("username") String username, @Field("token") String pass, @Field("api_key") String api_key);

    @FormUrlEncoded
    @POST("groups/index.php?req=fetchfrom")
    Call<Model__> getAllGroups(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("time") String time, @Field("page") String page);

    @FormUrlEncoded
    @POST("posts/index.php?req=likepost")
    Call<Model__> likePost(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("postid") String postid, @Field("type") String type);

    @FormUrlEncoded
    @POST("posts/index.php?req=unlikepost")
    Call<Model__> unlikePost(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("postid") String postid);

    @FormUrlEncoded
    @POST("posts/index.php?req=deletepost")
    Call<Model__> deletePost(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("postid") String postid);

    @FormUrlEncoded
    @POST("status/index.php?req=deletestatus")
    Call<Model__> deleteStatus(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("status_code") String postid);

    @FormUrlEncoded
    @POST("posts/index.php?req=getpost")
    Call<PostsModel> getPostsReplies(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("postid") String q, @Field("page") String page);

    @FormUrlEncoded
    @POST("posts/index.php?req=getlikes")
    Call<Model__> getLikes(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("q") String search, @Field("postid") String location, @Field("page") String page);

    @FormUrlEncoded
    @POST("msgs/index.php?req=create")
    Call<Model__> newMsg(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("text") String text);

    /*
    @FormUrlEncoded
    @POST("msgs/index.php?req=create")
    Call<Model__> newMsg(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("text") String text, @Field("imgurl") String q, @Field("privacy") String privacy, @Field("who") String who);
*/
    @FormUrlEncoded
    @POST("status/index.php?req=fetchdeleted")
    Call<Model__> getAllDelStatuses(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("time") String time, @Field("page") String page);

    @FormUrlEncoded
    @POST("profile/index.php?req=reqfrnd")
    Call<Model__> friendReq(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("who") String postid, @Field("type") String type);

}
