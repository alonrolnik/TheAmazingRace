package cse.bgu.finalandroidproject.theamazingrace;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	
	public static final int DATABASE_VERSION = 1;
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
	        		DB_Schema.GmeScenro.CHALLENGE + " text, text not null" +
	        		DB_Schema.GmeScenro.RIGHT_ANSWER + " text, text not null" +
	        		DB_Schema.GmeScenro.ANSWER1 + " text, text not null" +
	        		DB_Schema.GmeScenro.ANSWER2 + " text, text not null" +
	        		DB_Schema.GmeScenro.ANSWER3 + " text, text not null" +
	        		DB_Schema.GmeScenro.NEXT_CHECKPOINT_LAT + " real," +
	        		DB_Schema.GmeScenro.NEXT_CHECKPOINT_LONG + " real" +
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
	
	public void addChallenge(Challenge challenge){
		SQLiteDatabase db;
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DB_Schema.GmeScenro.CHECKPOINT_LAT, challenge.getCheckpoint().latitude);
		values.put(DB_Schema.GmeScenro.CHECKPOINT_LONG, challenge.getCheckpoint().longitude);
		values.put(DB_Schema.GmeScenro.NEXT_CHECKPOINT_LAT, challenge.getNext_checkpoint().latitude);
		values.put(DB_Schema.GmeScenro.NEXT_CHECKPOINT_LAT, challenge.getNext_checkpoint().longitude);
		values.put(DB_Schema.GmeScenro.CHALLENGE, challenge.getChallenge());
		values.put(DB_Schema.GmeScenro.ANSWER1, challenge.getWrong_answers()[0]);
		values.put(DB_Schema.GmeScenro.ANSWER2, challenge.getWrong_answers()[1]);
		values.put(DB_Schema.GmeScenro.ANSWER3, challenge.getWrong_answers()[2]);
		values.put(DB_Schema.GmeScenro.RIGHT_ANSWER, challenge.getRight_answer());
		
		//inserting row
		db.insert(DB_Schema.GmeScenro.TABLE_NAME, null, values);
		db.close(); // closing db connection
	}
	
	public Cursor getChallenge(int index){
		SQLiteDatabase db;
		db = this.getReadableDatabase();
		String[] projection = {
				DB_Schema.GmeScenro._ID,
				DB_Schema.GmeScenro.CHECKPOINT_LAT,
				DB_Schema.GmeScenro.CHECKPOINT_LONG,
				DB_Schema.GmeScenro.CHALLENGE,
				DB_Schema.GmeScenro.RIGHT_ANSWER,
				DB_Schema.GmeScenro.ANSWER1,
				DB_Schema.GmeScenro.ANSWER2,
				DB_Schema.GmeScenro.ANSWER3,
				DB_Schema.GmeScenro.NEXT_CHECKPOINT_LAT,
				DB_Schema.GmeScenro.NEXT_CHECKPOINT_LONG
		};
		Cursor c = db.query(true,
				DB_Schema.GmeScenro.TABLE_NAME,
				projection, DB_Schema.GmeScenro._ID + "=" + index,
				null, null, null, null, null);
		db.close();
		if (c != null)
			c.moveToFirst();
		return c;
	}

	public Cursor getEntireGame(){
		SQLiteDatabase db = this.getReadableDatabase();
	Cursor c = db.query(true,
			DB_Schema.GmeScenro.TABLE_NAME,
			null, null,
			null, null, null, null, null);
		return c;
		
	}
}