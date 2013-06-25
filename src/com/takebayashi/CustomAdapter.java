package com.takebayashi;

import java.util.List;
import java.util.Map;

import android.R.drawable;
import android.R.integer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/*
 *  CustomAdapter
 */
public class CustomAdapter extends SimpleAdapter{

	private static final String TAG = "myTag";
	
	private Context context;
	private int resource;
	
	
	private LayoutInflater mLayoutInflater;
	
	public CustomAdapter(
			Context context,
			List<? extends Map<String, ?>> data,
			int resource,
			String[] from, int[] to){
		
		super(context, data, resource, from, to);
		this.context = context;
		this.resource = resource;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		mLayoutInflater = LayoutInflater.from(context);
		
		// 該当位置からデータを取得
		ListView listView = (ListView)parent;
		Map<String, String> data = (Map<String, String>)listView.getItemAtPosition(position);
		
		// レイアウトに紐付けるXMLファイルを選択
		convertView = mLayoutInflater.inflate(resource, parent, false);

		
		
		// icon()
		Resources res = context.getResources();
		String str = (String)data.get("image").replace(".gif", "");//
		ImageView iconImage = (ImageView)convertView.findViewById(R.id.p_icon_image);
		int rsint = context.getResources().getIdentifier(str, "drawable",context.getPackageName());
		Bitmap bm = BitmapFactory.decodeResource(res, rsint);
		iconImage.setImageBitmap(bm);
		
		
		
		Log.d(TAG,"(String)data.get(icon)="+(String)data.get("image") + "  context.getPackageName()="+context.getPackageName());
		
		// uid
		TextView uidText = (TextView)convertView.findViewById(R.id.p_uid_text);
		uidText.setText((String)data.get("uid"));

		// maker
		TextView makerText = (TextView)convertView.findViewById(R.id.p_maker_text);
		makerText.setText((String)data.get("maker"));

		// date
		TextView dateText = (TextView)convertView.findViewById(R.id.p_date_text);
		dateText.setText((String)data.get("date"));
		
		// name
		TextView nameText = (TextView)convertView.findViewById(R.id.p_name_text);
		nameText.setText((String)data.get("name"));
		
		return convertView;
	}

}
