package ng.com.coursecode.piqmessenger.retrofit__;

import ng.com.coursecode.piqmessenger.BuildConfig;
import ng.com.coursecode.piqmessenger.model__.Gif__;
import ng.com.coursecode.piqmessenger.model__.Model__;
import ng.com.coursecode.piqmessenger.model__.PostsModel;
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
    String CONSTANT="version="+ BuildConfig.VERSION_CODE+"&";

    @FormUrlEncoded
    @POST("msgs/index.php?"+CONSTANT+"req=fetchfrom")
    Call<Model__> getAllMessages(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("time") String time, @Field("page") String page);

    @FormUrlEncoded
    @POST("msgs/index.php")
    Call<Model__> sendNewMessages(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("msg") String msg);

    @FormUrlEncoded
    @POST("status/index.php?"+CONSTANT+"req=fetchfrom")
    Call<Model__> getAllStatuses(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("time") String time, @Field("page") String page);

    @FormUrlEncoded
    @POST("notify/index.php?"+CONSTANT+"req=fetchfrom")
    Call<PostsModel> getAllNotify(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("time") String time, @Field("page") String page);

    @FormUrlEncoded
    @POST("posts/index.php?"+CONSTANT+"req=fetchfrom")
    Call<PostsModel> getAllPosts(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("q") String q, @Field("page") String page);

    @FormUrlEncoded
    @POST("posts/index.php?"+CONSTANT+"req=fetchdiscover")
    Call<PostsModel> getAllDiscover(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("q") String q, @Field("page") String page);

    @FormUrlEncoded
    @POST("profile/index.php?"+CONSTANT+"req=about")
    Call<Model__> getUser(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("who") String who);

    @FormUrlEncoded
    @POST("msgs/index.php?"+CONSTANT+"req=about")
    Call<Model__> getUserDM(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("who") String who, @Field("time") String time);

    @FormUrlEncoded
    @POST("posts/index.php?"+CONSTANT+"req=userposts")
    Call<PostsModel> getUsersPosts(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("page") String page, @Field("who") String who);

    @FormUrlEncoded
    @POST("groups/index.php?"+CONSTANT+"req=search")
    Call<Model__> searchGroups(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("q") String search, @Field("page") String page);

    @FormUrlEncoded
    @POST("profile/index.php?"+CONSTANT+"req=search")
    Call<Model__> searchUsers(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("q") String search, @Field("location") String location, @Field("page") String page);

    @FormUrlEncoded
    @POST("status/index.php?"+CONSTANT+"req=getallviewers")
    Call<Model__> searchStatusUsers(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("q") String search, @Field("status_code") String status_code, @Field("page") String page);

    @FormUrlEncoded
    @POST("status/index.php?"+CONSTANT+"req=viewstat")
    Call<Model__> saveStatus(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("status_code") String status_code);

    @GET("search?"+CONSTANT+"as=wq")
    Call<Gif__> searchGifs(@Query("q") String search, @Query("key") String key,  @Query("pos") String pos,  @Query("limit") String limit,  @Query("safesearch") String safesearch);

    @GET("trending?"+CONSTANT+"as=wq")
    Call<Gif__> trendingGif(@Query("key") String key, @Query("limit") String limit);

    @FormUrlEncoded
    @POST("posts/index.php?"+CONSTANT+"req=create")
    Call<Model__> newPost(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("text") String text, @Field("imgurl") String q, @Field("privacy") String privacy, @Field("who") String who,  @Field("postid") String location);

    @FormUrlEncoded
    @POST("posts/index.php?"+CONSTANT+"req=edit")
    Call<Model__> editPost(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("text") String text, @Field("privacy") String privacy,  @Field("postid") String location);

    @FormUrlEncoded
    @POST("posts/index.php?"+CONSTANT+"req=createtouser")
    Call<Model__> newPostToUser(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("text") String text, @Field("imgurl") String q, @Field("privacy") String privacy, @Field("who") String who,  @Field("postid") String location);

    @FormUrlEncoded
    @POST("status/index.php?"+CONSTANT+"req=create")
    Call<Model__> newStatus(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("text") String text, @Field("imgurl") String q);

    @FormUrlEncoded
    @POST("profile/index.php?"+CONSTANT+"req=edit")
    Call<Model__> editProfile(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("fullname") String fname, @Field("bio") String bio, @Field("imgurl") String q);

    @FormUrlEncoded
    @POST("services/index.php?"+CONSTANT+"req=create")
    Call<Model__> sendToken(@Field("username") String username, @Field("pass") String pass, @Field("token") String token, @Field("api_key") String api_key);

    @FormUrlEncoded
    @POST("groups/index.php?"+CONSTANT+"req=fetchfrom")
    Call<Model__> getAllGroups(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("page") String page);

    @FormUrlEncoded
    @POST("posts/index.php?"+CONSTANT+"req=likepost")
    Call<Model__> likePost(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("postid") String postid, @Field("type") String type);

    @FormUrlEncoded
    @POST("posts/index.php?"+CONSTANT+"req=unlikepost")
    Call<Model__> unlikePost(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("postid") String postid);

    @FormUrlEncoded
    @POST("posts/index.php?"+CONSTANT+"req=deletepost")
    Call<Model__> deletePost(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("postid") String postid);

    @FormUrlEncoded
    @POST("status/index.php?"+CONSTANT+"req=deletestatus")
    Call<Model__> deleteStatus(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("status_code") String postid);

    @FormUrlEncoded
    @POST("posts/index.php?"+CONSTANT+"req=getpost")
    Call<PostsModel> getPostsReplies(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("postid") String q, @Field("page") String page);

    @FormUrlEncoded
    @POST("posts/index.php?"+CONSTANT+"req=getlikes")
    Call<Model__> getLikes(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("q") String search, @Field("postid") String location, @Field("page") String page);

    @FormUrlEncoded
    @POST("msgs/index.php?"+CONSTANT+"req=create")
    Call<Model__> newMsg(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("text") String text);

    @FormUrlEncoded
    @POST("msgs/index.php?"+CONSTANT+"req=deleteconvo")
    Call<Model__> deleteConvo(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("who") String text);

    @FormUrlEncoded
    @POST("profile/index.php?"+CONSTANT+"req=editpass")
    Call<Model__> editPass(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("new_pass") String text);

    @FormUrlEncoded
    @POST("msgs/index.php?"+CONSTANT+"req=delete")
    Call<Model__> deleteById(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("msg_id") String text);

    /*
    @FormUrlEncoded
    @POST("msgs/index.php?"+CONSTANT+"req=create")
    Call<Model__> newMsg(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("text") String text, @Field("imgurl") String q, @Field("privacy") String privacy, @Field("who") String who);
*/
    @FormUrlEncoded
    @POST("status/index.php?"+CONSTANT+"req=fetchdeleted")
    Call<Model__> getAllDelStatuses(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("page") String page);

    @FormUrlEncoded
    @POST("profile/index.php?"+CONSTANT+"req=reqfrnd")
    Call<Model__> friendReq(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("who") String postid, @Field("type") String type);

    @FormUrlEncoded
    @POST("profile/index.php?"+CONSTANT+"req=create")
    Call<Model__> createUser(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("check") String postid);

    @FormUrlEncoded
    @POST("profile/index.php?"+CONSTANT+"req=checkuser")
    Call<Model__> checkUser(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("check") String postid);

    @FormUrlEncoded
    @POST("posts/index.php?"+CONSTANT+"req=fetchthis")
    Call<PostsModel> getThisPosts(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("postid") String q);

    @FormUrlEncoded
    @POST("profile/index.php?"+CONSTANT+"req=searchonlinefriends")
    Call<Model__> getOnlineFriends(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("q") String search, @Field("location") String location, @Field("page") String page);

    @FormUrlEncoded
    @POST("profile/index.php?"+CONSTANT+"req=searchonline")
    Call<Model__> getOnlineMembers(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("q") String search, @Field("location") String location, @Field("page") String page);

    @FormUrlEncoded
    @POST("groups/index.php?"+CONSTANT+"req=reqfrnd")
    Call<Model__> joinGroup(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("who") String postid, @Field("type") String type);

    @FormUrlEncoded
    @POST("groups/index.php?"+CONSTANT+"req=about")
    Call<Model__> getGroup(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("who") String who);

    @FormUrlEncoded
    @POST("posts/index.php?"+CONSTANT+"req=groupposts")
    Call<PostsModel> getGroupsPosts(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("page") String page, @Field("who") String who);

    @FormUrlEncoded
    @POST("profile/index.php?"+CONSTANT+"req=creategroup")
    Call<Model__> createGroup(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("fullname") String fname, @Field("who") String g_username, @Field("bio") String bio, @Field("imgurl") String q);

    @FormUrlEncoded
    @POST("profile/index.php?"+CONSTANT+"req=editgroup")
    Call<Model__> editGroup(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("fullname") String fname, @Field("who") String g_username, @Field("bio") String bio, @Field("imgurl") String q);

    @FormUrlEncoded
    @POST("profile/index.php?"+CONSTANT+"req=searchgroupuser")
    Call<Model__> getGroupMembers(@Field("username") String username, @Field("pass") String pass, @Field("api_key") String api_key, @Field("q") String search, @Field("location") String location, @Field("page") String page);

}
