package com.takebayashi;

import android.os.Bundle;
import android.util.Log;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;

public class MapActivity extends FragmentActivity{

	
	public static String mapLa;
	public static String mapLo;
	
	private static final String TAG = "myTag";
	private GoogleMap gMap;

	float la;
	float lo;
	
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Log.d(TAG,"1");
		
		setContentView(R.layout.activity_map);
		Log.d(TAG,"2");
		
		la = Float.parseFloat(mapLa);
		lo = Float.parseFloat(mapLo);
		
		gMap = ((SupportMapFragment)
				getSupportFragmentManager().
				findFragmentById(R.id.map)).getMap();
		
		// 初期化メソッド
		try{
			MapsInitializer.initialize(this);
		}catch(GooglePlayServicesNotAvailableException e){
			Log.e(TAG, "GPSNAE:" + e.toString());
		}
		
		Log.d(TAG, ""+la);
		Log.d(TAG, ""+lo);
		
		
		// 中心地の変更(緯度、経度のみ)
		CameraUpdate cu1 = CameraUpdateFactory.newLatLng(	new LatLng(la, lo));
		gMap.moveCamera(cu1);
		
		// 中心地の変更(緯度、経度、ズームレベル)※Zoomレベルは、2 ~ 21
		CameraUpdate cu2 = 
				CameraUpdateFactory.newLatLngZoom(
						new LatLng(la, lo), 15);
		gMap.moveCamera(cu2);
		
		
		// ピンを立てる
		gMap.addMarker(new MarkerOptions().position(new LatLng(la, lo)).title("市役所"));
	
	}
}
