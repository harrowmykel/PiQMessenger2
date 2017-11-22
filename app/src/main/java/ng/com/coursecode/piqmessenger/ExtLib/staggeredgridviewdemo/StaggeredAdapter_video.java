package ng.com.coursecode.piqmessenger.ExtLib.staggeredgridviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ng.com.coursecode.piqmessenger.ExtLib.staggeredgridviewdemo.views.ScaleImageView;
import ng.com.coursecode.piqmessenger.R;

public class StaggeredAdapter_video extends ArrayAdapter<String> {

	public StaggeredAdapter_video(Context context, int resource, List<String> objects) {
		super(context, resource, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(getContext());
			convertView = layoutInflator.inflate(R.layout.row_staggered_demo, null);
			holder = new ViewHolder();
			holder.imageView = (ScaleImageView) convertView .findViewById(R.id.imageView1);
			convertView.setTag(holder);
		}

		holder = (ViewHolder) convertView.getTag();

		Uri uri=Uri.parse(getItem(position));

		Bitmap bMap= ThumbnailUtils.createVideoThumbnail(uri.getPath(), MediaStore.Video.Thumbnails.MICRO_KIND);
		holder.imageView.setImageBitmap(bMap);

		return convertView;
	}

	static class ViewHolder {
		ScaleImageView imageView;
	}
}
