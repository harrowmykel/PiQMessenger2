package ng.com.coursecode.piqmessenger.interfaces;

/**
 * Created by harro on 31/10/2017.
 */

public interface PostItemClicked {

    void onLikeClicked(int position);
    void onReplyClicked(int position);
    void onDMessageClicked(int position);
    void onUsernameClicked(int position);
    void onMoreClicked(int position);
    void onShowClicked(int position);

    void onShowMoreLikeOptions(int position);
}
