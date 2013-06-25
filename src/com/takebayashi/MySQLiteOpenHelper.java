package com.takebayashi;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/*
 * assetsフォルダからDBをアプリケーションのデフォルトシステムパスにコピー
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper{

	private static String TAG = "myTag";

	// デフォルトシステムパス
	private static String DB_PATH = "/data/data/com.takebayashi/databases/";
	// データベース名
	private static String DB_NAME = "GifuList.db";

	private Context context;
	SQLiteDatabase db;

	public MySQLiteOpenHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db){
		Log.d(TAG, "MySQLOpenHandler onCreate()");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		Log.d(TAG, "MySQLOpenHelper onUpgrade()");
	}

	@Override
	public synchronized void close(){
		super.close();
		Log.d(TAG, "MySQLOpenHelper close()");
		if(db != null)
			db.close();
	}

	// DBを開く
	public SQLiteDatabase openDatabase(){
		String myPath = DB_PATH + DB_NAME;
		File myFile = new File(myPath);

		// ファイルが無い場合はデータベースをコピー
		if(!myFile.exists())
			copyDatabase();

		try{
			db = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		}catch(SQLiteException e){
			Log.e(TAG, "SQLE:" + e.toString());
		}
		return db;
	}

	// assetに格納したDBをデフォルトのデフォルトシステムパスにコピーする
	private void copyDatabase(){
		this.getReadableDatabase();

		String myPath = DB_PATH + DB_NAME;
		try{
			// InputStream, OutputStreamオープン
			InputStream is = context.getAssets().open(DB_NAME);
			OutputStream os = new FileOutputStream(myPath);

			// コピー
			byte[] buffer = new byte[1024];
			int size = 0;
			while((size = is.read(buffer)) > 0){
				os.write(buffer, 0, size);
			}

			// InputStream, OutputStreamクローズ
			os.flush();
			os.close();
			is.close();

		}catch(FileNotFoundException e){
			Log.e(TAG, "FNFE:" + e.toString());
		}catch(IOException e){
			Log.e(TAG, "IOE:" + e.toString());
		}

	}
}