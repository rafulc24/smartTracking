package thePackcage.raf;

import java.util.ArrayList;
import thePackage.raf.R;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemListBaseAdapter extends BaseAdapter {
	
	private static ArrayList<ItemDetails> itemDetailsrrayList;
	
	Intent intent;
	int i=0;
	private LayoutInflater l_Inflater;
	TextView title;

	// get the context of the layout from 
	// ListViewActivity and the data
	
	public ItemListBaseAdapter(Context context, ArrayList<ItemDetails> results) {
		itemDetailsrrayList = results;
		l_Inflater = LayoutInflater.from(context);


	}

	public int getCount() {
		return itemDetailsrrayList.size();
	}

	public Object getItem(int position) {
		return itemDetailsrrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}


	// this method calls automatically when we 
	// use the adapter class
	
	@SuppressLint("NewApi")
	public View getView(int position, View convertView, ViewGroup parent) {
		

		// position is the number of the item
		// of the list (stars from 0 )

		//convertView get the view of the objects
		// that will shown in each item in the list
					
		
		ViewHolder holder;
		if (convertView == null) {
			
			convertView = l_Inflater.inflate(R.layout.item_details_view, null);
			holder = new ViewHolder();
			
	     	holder.txt_title = (TextView) convertView.findViewById(R.id.title2);
			holder.txt_itemName_title = (TextView) convertView.findViewById(R.id.name_title);
			holder.txt_itemTime_title = (TextView) convertView.findViewById(R.id.time_title);
			holder.txt_itemName = (TextView) convertView.findViewById(R.id.name);
			holder.txt_itemTime = (TextView) convertView.findViewById(R.id.time);	

			
			
			convertView.setTag(holder);
		} 
		
		// if is not the first item, so first 
		// attach the previous items to the view	
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		//define different colors to the text and the 
		// background of the items	
		
		if (position%2==0){


			convertView.setBackgroundColor(Color.parseColor("#C0C0C0"));

				
			holder.txt_itemName_title.setTextColor(Color.parseColor("#FFFF00")); //yellow
			holder.txt_itemTime_title.setTextColor(Color.parseColor("#FFFF00"));
			
			holder.txt_itemName.setTextColor(Color.parseColor("#FFFFFF")); //white
			holder.txt_itemTime.setTextColor(Color.parseColor("#FFFFFF"));
			

		}
		
		else
		{

			convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
			
			
			holder.txt_itemName_title.setTextColor(Color.parseColor("#32cd32")); //LimeGreen
			holder.txt_itemTime_title.setTextColor(Color.parseColor("#32cd32")); //LimeGreen
			
			holder.txt_itemName.setTextColor(Color.parseColor("#000000")); //black
			holder.txt_itemTime.setTextColor(Color.parseColor("#000000")); //black

				
		}

		holder.txt_title .setText(itemDetailsrrayList.get(position).getTitle() );
		holder.txt_itemName_title .setText(itemDetailsrrayList.get(position).getName_title() );
		holder.txt_itemTime_title.setText(itemDetailsrrayList.get(position).getTime_title());
		holder.txt_itemName.setText(itemDetailsrrayList.get(position).getName());
		holder.txt_itemTime.setText(itemDetailsrrayList.get(position).getTime());

		
		return convertView;
	}

	static class ViewHolder {
		TextView txt_itemName_title;
		TextView txt_itemTime_title;
		TextView txt_itemName;
		TextView txt_itemTime;
		TextView txt_title;
	}
}
