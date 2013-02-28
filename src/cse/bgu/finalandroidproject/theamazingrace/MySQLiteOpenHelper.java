package cse.bgu.finalandroidproject.theamazingrace;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	
	public static final int DATABASE_VERSION = 1;
	private static final 	String[] projection_game = {
		DB_Schema.GmeScenro._ID,
		DB_Schema.GmeScenro.CHECKPOINT_LAT,
		DB_Schema.GmeScenro.CHECKPOINT_LONG,
		DB_Schema.GmeScenro.CHALLENGE,
		DB_Schema.GmeScenro.RIGHT_ANSWER,
		DB_Schema.GmeScenro.ANSWER1,
		DB_Schema.GmeScenro.ANSWER2,
		DB_Schema.GmeScenro.ANSWER3,
};
 
	//create helper object to manipulate database
	public MySQLiteOpenHelper(Context context) {
		super(context, DB_Schema.dbName, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
//		// create user table
//        db.execSQL
//        		(
//	        		"create table " +
//	        		DB_Schema.UserDetails.TABLE_NAME +
//	        		"(" +
//	        		DB_Schema.UserDetails._ID +
//	        		" integer primary key autoincrement, " +
//	        		DB_Schema.UserDetails.USER_NAME + "text ," +
//	        		DB_Schema.UserDetails.PASSWORD + "text ," +
//	        		DB_Schema.UserDetails.EMAIL + "text ," +
//	        		"text not null" +
//	        		" );"
//        		);	
//        
//     // create games list table
//        db.execSQL
//        		(
//	        		"create table " +
//	        		DB_Schema.GamLstTable.TABLE_NAME +
//	        		"(" +
//	        		DB_Schema.GamLstTable._ID +
//	        		" integer primary key autoincrement, " +
//	        		DB_Schema.GamLstTable.GAME_NAME + "text ," +
//	        		DB_Schema.GamLstTable.CREATOR + "text ," +
//	        		DB_Schema.GamLstTable.AREA + "text ," +
//	        		DB_Schema.GamLstTable.LEADING_SCORE + "Integer ," +
//	        		" );"
//        		);
//     // create game statistics table
//        db.execSQL
//        		(
//	        		"create table " +
//	        		DB_Schema.GamStatsc.TABLE_NAME +
//	        		"(" +
//	        		DB_Schema.GamStatsc._ID +
//	        		" integer primary key autoincrement, " +
//	        		DB_Schema.GamStatsc.DATE + "text ," +
//	        		DB_Schema.GamStatsc.SCORE + "Integer ," +
//	        		DB_Schema.GamStatsc.TIME_TO_FINISH + "Integer ," +
//	        		" );"
//        		);
//     // create game scenario table
        db.execSQL
        		(
	        		"create table " +
	        		DB_Schema.GmeScenro.TABLE_NAME +
	        		" (" +
	        		DB_Schema.GmeScenro._ID +
	        		" integer primary key autoincrement, " +
	        		DB_Schema.GmeScenro.CHECKPOINT_LAT + " real," +
	        		DB_Schema.GmeScenro.CHECKPOINT_LONG + " real," +
	        		DB_Schema.GmeScenro.CHALLENGE + " text," +
	        		DB_Schema.GmeScenro.RIGHT_ANSWER + " text," +
	        		DB_Schema.GmeScenro.ANSWER1 + " text," +
	        		DB_Schema.GmeScenro.ANSWER2 + " text," +
	        		DB_Schema.GmeScenro.ANSWER3 + " text" +
	        		");"
	        	);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older tables if existed
		db.execSQL("DROP TABLE IF EXISTS " + DB_Schema.dbName);
		// create tables again
		onCreate(db);
	}
	
	public long addChallenge(Challenge challenge){
		SQLiteDatabase db;
		ContentValues values = new ContentValues();
		values.put(DB_Schema.GmeScenro.CHECKPOINT_LAT, challenge.getCheckpoint().latitude);
		values.put(DB_Schema.GmeScenro.CHECKPOINT_LONG, challenge.getCheckpoint().longitude);
		values.put(DB_Schema.GmeScenro.CHALLENGE, challenge.getChallenge());
		values.put(DB_Schema.GmeScenro.ANSWER1, challenge.getWrong_answers(0));
		values.put(DB_Schema.GmeScenro.ANSWER2, challenge.getWrong_answers(1));
		values.put(DB_Schema.GmeScenro.ANSWER3, challenge.getWrong_answers(2));
		values.put(DB_Schema.GmeScenro.RIGHT_ANSWER, challenge.getRight_answer());
		
		//inserting row
		db = this.getWritableDatabase();
		long r_id = db.insert(DB_Schema.GmeScenro.TABLE_NAME, null, values);
		db.close(); // closing db connection
		return r_id;
	}
	
	public Challenge getChallenge(long id){
		SQLiteDatabase db;
		db = this.getReadableDatabase();
		Cursor c = db.query(
				DB_Schema.GmeScenro.TABLE_NAME,
				projection_game, DB_Schema.GmeScenro._ID + "=?",
					new String[] { String.valueOf(id) },
					null, null, null);
		
		if (c != null)
			if (c.moveToFirst())
				Log.d("cursor ","C is true");
		db.close();
		c.close();
		return curToChallenge(c);
	}

	private Challenge curToChallenge(Cursor c) {
		// TODO Auto-generated method stub
		Challenge challenge = new Challenge();
		challenge.setChallenge(c.getString(3));
		challenge.setWrong_answers(new String [] {c.getString(5), c.getString(6), c.getString(7)});
		challenge.setCheckpoint( new LatLng(c.getDouble(1), c.getDouble(2)));
		challenge.setRight_answer(c.getString(4));
		return challenge;
	}

	public List<Challenge> getEntireGame(){
		List<Challenge> list = new ArrayList<Challenge>();
		SQLiteDatabase db = this.getReadableDatabase();
	Cursor c = db.query(
			DB_Schema.GmeScenro.TABLE_NAME,
			projection_game, null,
			null, null, null, null);
		c.moveToFirst();
		while(!c.isAfterLast()){
			Challenge challenge = curToChallenge(c);
			list.add(challenge);
			c.moveToNext();
		}
		c.close();
		db.close();
		return list;
		
	}
}