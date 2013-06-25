package com.takebayashi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;//SQL
import android.database.sqlite.SQLiteDatabase;//データーベース
import android.graphics.BitmapFactory;
import android.util.Log;//ログ
import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.view.View;

import android.widget.ArrayAdapter;

import android.widget.Button;//ボタン

import android.widget.ListView;//リストビュー

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity implements View.OnClickListener,OnItemClickListener{
	
	private static final String TAG = "myTag";
	public  static final String DATA_TAG = "myData";
	private String listId;//id
	private String listName;//名前
	private String listKana;//かな
	private String listPost;//郵便番号
	private String listAddress;//住所
	private String listLatitude;//緯度
	private String listLongitude;//経度
	private String listImage;//画像
	private String listUrl;//URL
	private String listPopulation;//人口
	private String listArea;//面積
	
	private Button button;
	private ListView listView;
	
	HashMap<String, Object> map;
	ArrayList<HashMap<String, Object>> tun ;
	//------------------------------------------------------
	
	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		
		this.setContentView(R.layout.activity_main);
		
		button = (Button) this.findViewById(R.id.button);
		button.setOnClickListener(this);
		
		listView = (ListView) findViewById(R.id.list_view);
		listView.setOnItemClickListener(this);
		
		
		
		readDatabase();//データーベース読み込み
		
	}
	
	//--------------------------------------------------
	
	@Override
	public void onClick(View view){
		if(view == button){
			readDatabase();//メソッド呼び出し	
			
		}
	}
	
	// データベースからの読み込み-----------------------------------
	private void readDatabase(){
		
		// 1,データをを取り出す
		MySQLiteOpenHelper msoh = new MySQLiteOpenHelper(this);
		SQLiteDatabase db = msoh.openDatabase();
		
		if(db != null){
			try{
				
				// 2, ArrayAdapterを用意します
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1);
				
				// 3, データベースにアクセスし、一件づつ取り出します
				String sql = "SELECT *FROM GifuTable ORDER BY GifuListid ASC";
				Cursor csr = db.rawQuery(sql, null);
				csr.moveToFirst();
				
				tun = new ArrayList<HashMap<String,Object>>();
				
				//要素取得---------------------------------------
				for(int i = 0; i < csr.getCount(); i++){
					
					map = new HashMap<String, Object>();
					
					// 1件分のデータを整形します
					listId   = "" + csr.getString(0);
						map.put("id",listId);
				    listName = "" + csr.getString(1);
				    	map.put("name",listName);
				    listKana = "" + csr.getString(2);
				    	map.put("kana",listKana);
				    listPost = "" + csr.getString(3);
				    	map.put("post",listPost);
				    listAddress = "" + csr.getString(4);
				    	map.put("address",listAddress);
				    listLatitude = "" + csr.getString(5);
				    	map.put("latitude",listLatitude);
				    listLongitude = "" + csr.getString(6);
				    	map.put("longitude",listLongitude);
				    listImage = "" + csr.getString(7);
				    	map.put("image",listImage);
				    listUrl = "" + csr.getString(8);
				    	map.put("url",listUrl);
				    listPopulation = "" + csr.getString(9);
				    	map.put("population",listPopulation);
				    listArea = "" + csr.getString(10);
				    	map.put("area",listArea);
				    	
				    tun.add(map);//リストに追加
				    
					// 1件分のデータをArrayAdapterに登録します
					adapter.add(listName);//リストに追加
					csr.moveToNext();
				}
				csr.close();
				db.close();
				Log.d(TAG,""+tun.toString());
				
				// カスタムアダプター
				CustomAdapter customAdapter = new CustomAdapter(
						this, 
						tun, 
						R.layout.custom_adapter_row,
						new String[]{"icon", "uid", "maker","date", "name"},
						new int[]{
								R.id.p_icon_image, R.id.p_uid_text, 
								R.id.p_maker_text, R.id.p_date_text, 
								R.id.p_name_text});
				
				
				// リストビューに設定
				ListView listView = (ListView)this.findViewById(R.id.list_view);
				listView.setScrollingCacheEnabled(false);
				listView.setAdapter(customAdapter);

				
//				// 4,ArrayAdapterをListViewに設定
//				listView = (ListView) findViewById(R.id.list_view);
//				listView.setAdapter(adapter);
			}catch(SQLException e){
				Log.e(TAG, "SQLException:" + e.toString());
			}
		}
	}
	
	//------------------------------------------------
	
	// リスト項目をクリックした時に実行
	@Override
    public void onItemClick(AdapterView<?> arg,View p,int u,long y){
		
		Intent intent = new Intent(this,SubActivity.class);//遷移先を決める
		
		SubActivity a = new SubActivity();
		a.city = tun.get(u);
		
        if (intent != null){
            startActivity(intent); // 画面遷移する
	}
	}
}