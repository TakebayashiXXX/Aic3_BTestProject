package com.takebayashi;

import java.util.HashMap;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

import android.widget.TextView;
import android.widget.Button;

public class SubActivity extends Activity implements View.OnClickListener{

	private static final String TAG = "myTag";
	private TextView nameText;
	private TextView kanaText;
	private TextView postText;
	private TextView addressText;
	private TextView aregText;
	private TextView populationText;
	
	private Button mapButton;
	private Button webButton;
	private Button button;
		
	public static HashMap<String, Object>city;
	//----------------------------------
	
	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.activity_sub);
		
		nameText = (TextView) this.findViewById(R.id.name);
		nameText.setText("" +city.get("name"));
		
		kanaText = (TextView) this.findViewById(R.id.kana);
		kanaText.setText("" +city.get("kana"));
		
		postText = (TextView) this.findViewById(R.id.post);
		postText.setText("" +city.get("post"));
		
		addressText = (TextView) this.findViewById(R.id.address);
		addressText.setText("" +city.get("address"));
		
		aregText = (TextView) this.findViewById(R.id.area);
		aregText.setText("約" +city.get("area") +"Km");
		
		populationText = (TextView) this.findViewById(R.id.population);
		populationText.setText("" +city.get("population") +"人");
		
		mapButton = (Button) this.findViewById(R.id.map);
		mapButton.setOnClickListener(this);
		
		webButton = (Button) this.findViewById(R.id.web);
		webButton.setOnClickListener(this);
		
		button = (Button) this.findViewById(R.id.button);
		button.setOnClickListener(this);
		
		
		Log.d(TAG,""+city.toString());
		
		// インテントからのパラメーター取得
		Bundle extra = this.getIntent().getExtras();
		String data = "";
		
		if(extra != null){
			data = extra.getString(MainActivity.DATA_TAG);
			kanaText.setText(""+city.get("name"));
		}
	}
	
	//------------------------------
	
	@Override
	public void onClick(View view){
		if(view == button){
			// 画面を閉じる
			finish();
		}
		if (view == mapButton) {
			Intent intent = new Intent(getBaseContext(),MapActivity.class);
			MapActivity m = new MapActivity(); 
			m.mapLa = (String)city.get("latitude");
			m.mapLo = (String)city.get("longitude");
			if (intent != null){
	            startActivity(intent); // 画面遷移する
		}

		}
		if (view == webButton) {
			Intent intent = new Intent(getBaseContext(),WebActivity.class);
			WebActivity w = new WebActivity();
			w.web = (String)city.get("url");
			if (intent != null){
	            startActivity(intent); // 画面遷移する
		}
		}
	}
}