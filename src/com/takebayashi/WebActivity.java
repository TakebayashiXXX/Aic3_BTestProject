package com.takebayashi;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;

public class WebActivity extends Activity implements View.OnClickListener{
	
	public static String web;
	
	private static final String TAG = "myTag";
	public static final String DATA_TAG = "myData";
	
	private WebView webView;
	private Button closeBtn, backwardBtn, forwardBtn, refleshBtn;
	
//-----------------------------------------------------
	
	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		
//		//既存のWebアプリに飛ばす
//		Uri uri = Uri.parse("http://www.livedoor.co.jp");
//		Intent i = new Intent(Intent.ACTION_VIEW,uri);
//		startActivity(i);
		
		
		setContentView(R.layout.activity_web);
		
		// URL
		String url = web;
		
		if(checkInternetConnection()){
			// Webクライアント----------------------------------
			webView = new WebView(this);
			WebSettings settings = webView.getSettings();
			settings.setJavaScriptEnabled(true);
			settings.setSavePassword(false);
			settings.setSaveFormData(false);
			settings.setSupportZoom(false);//拡大&縮小
			
			// WebViewの通知リクエストの処理-------------------------
			webView.setWebViewClient(new WebViewClient(){
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url){
					view.loadUrl(url);
					return true;
				}

				@Override
				public void onReceivedError(WebView view, int errorCode,
						String description, String url){
					Log.e(TAG, "onReceivedError()_errorCode:" + errorCode);
					Log.e(TAG, "onReceivedError()_description:" + description);
					Log.e(TAG, "onReceivedError()_url:" + url);
				}
			});

			// Webブラウザに貼付ける--------------------------------
			RelativeLayout rl_layout = (RelativeLayout) this
					.findViewById(R.id.rl_web);
			webView.loadUrl(url);// URLをロード
			rl_layout.addView(webView);

			// ボタンを設定-------------------------------------------
			closeBtn = (Button) this.findViewById(R.id.closebtn);
			closeBtn.setOnClickListener(this);
			backwardBtn = (Button) this.findViewById(R.id.backwardbtn);
			backwardBtn.setOnClickListener(this);
			forwardBtn = (Button) this.findViewById(R.id.forwardbtn);
			forwardBtn.setOnClickListener(this);
			refleshBtn = (Button) this.findViewById(R.id.refleshbtn);
			refleshBtn.setOnClickListener(this);
		}
	}

//-----------------------------------------------------
	
	@Override
	public void onClick(View view){
		if(view == closeBtn){
			finish();//閉じる
		}else if(view == backwardBtn){
			webView.goBack();//戻る
		}else if(view == forwardBtn){
			webView.goForward();//次へ
		}else if(view == refleshBtn){
			webView.reload();//更新
		}
	}
	
	// インターネットに繋がっているのかどうか-----------------------
	public boolean checkInternetConnection(){
		ConnectivityManager cm = 
				(ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if(info != null){
			Log.d(TAG, "NetworkInfo_isAvailable:" + info.isAvailable());
			Log.d(TAG, "NetworkInfo_isConnected:" + info.isConnected());
			Log.d(TAG, "NetworkInfo_isRoaming:" + info.isRoaming());
			if(info.isAvailable() && info.isConnected()){
				return true;
			}else{
				return false;
			}
		}else{
			Log.d(TAG, "NetworkInfo:null");
			return false;
		}
	}
}
