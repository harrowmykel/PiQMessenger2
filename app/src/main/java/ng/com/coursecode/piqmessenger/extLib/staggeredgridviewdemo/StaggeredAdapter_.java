package ng.com.coursecode.piqmessenger.extLib.staggeredgridviewdemo;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import ng.com.coursecode.piqmessenger.extLib.staggeredgridviewdemo.views.ScaleImageView;
import ng.com.coursecode.piqmessenger.R;

public class StaggeredAdapter_ extends ArrayAdapter<String> {

	public StaggeredAdapter_(Context context, int resource, List<String> objects) {
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
		holder.imageView.setImageURI(Uri.parse(getItem(position)));
		if(holder.imageViewl.getVisibility()!=View.GONE)
			holder.imageViewl.setVisibility(View.GONE);

		return convertView;
	}

	static class ViewHolder {
		ScaleImageView imageView;
		ImageView imageViewl;
	}
}
