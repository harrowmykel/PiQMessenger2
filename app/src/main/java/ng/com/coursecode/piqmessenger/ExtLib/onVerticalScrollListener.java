package ng.com.coursecode.piqmessenger.ExtLib;

/**
 * Created by harro on 27/10/2017.
 */

import android.support.v7.widget.RecyclerView;

public class onVerticalScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!recyclerView.canScrollVertically(-1)) {
                onScrolledToTop();
            } else if (!recyclerView.canScrollVertically(1)) {
                onScrolledToBottom();
            }
            if (dy < 0) {
                onScrolledUp(dy);
            } else if (dy > 0) {
                onScrolledDown(dy);
            }
        }

        public void onScrolledUp(int dy) {
            onScrolledUp();
        }

        public void onScrolledDown(int dy) {
            onScrolledDown();
        }

        public void onScrolledUp() {}

        public void onScrolledDown() {}

        public void onScrolledToTop() {}

        public void onScrolledToBottom() {}

}
