package br.com.androidzin.launchablesitens;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class LaunchableItemAdapter extends ArrayAdapter<LaunchableItem> {

	private List<LaunchableItem> itens;
	private LayoutInflater inflator;
	private Context mContext;
	private OnItemCheckedListener action;

	static class ViewHolder {
		TextView appActivity, appPackage;
		ImageView appIcon;
		CheckBox checkBox;
	}

	public LaunchableItemAdapter(Context context, int layoutRowId,
			List<LaunchableItem> data, OnItemCheckedListener action) {

		super(context, layoutRowId, data);

		mContext = context;
		inflator = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		itens = data;
		
		this.action = action;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		LaunchableItem currentItem = itens.get(position);
		if (convertView == null) {

		    convertView= inflator.inflate(R.layout.item, null);
		    
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.appActivity = (TextView) convertView.findViewById(R.id.appActivity);
			viewHolder.appPackage  = (TextView) convertView.findViewById(R.id.appPackage);
			viewHolder.checkBox= (CheckBox) convertView.findViewById(R.id.export);
			viewHolder.appIcon = (ImageView) convertView.findViewById(R.id.appIcon);
						
			convertView.setTag(viewHolder);			
			viewHolder.checkBox.setTag(currentItem);
			
			viewHolder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					
					LaunchableItem itemChecked = ((LaunchableItem) viewHolder.checkBox.getTag()); 
					if(itemChecked.isSelected() != isChecked){
						itemChecked.setSelected(isChecked);
						action.onItenChecked(itemChecked);
					}
				}
			});

					
		} else {
			((ViewHolder) convertView.getTag()).checkBox.setTag(currentItem);
		}

		ViewHolder holder = (ViewHolder) convertView.getTag();


		holder.appActivity.setText(currentItem.getItemLaunchable());
		holder.appPackage.setText(currentItem.getItemPackage());
		holder.appIcon.setImageDrawable(currentItem.getItemIcon());
		holder.checkBox.setChecked(currentItem.isSelected());

		return convertView;
	}
}
