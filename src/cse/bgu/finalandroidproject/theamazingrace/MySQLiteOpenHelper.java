package cse.bgu.finalandroidproject.theamazingrace;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	
	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ";
	public static final int DATABASE_VERSION = 1;


	//create helper object to manipulate database
	public MySQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, DB_Schema.dbName, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// create user table
        db.execSQL
        		(
	        		"create table " +
	        		DB_Schema.UserDetails.TABLE_NAME +
	        		"(" +
	        		DB_Schema.UserDetails._ID +
	        		" integer primary key autoincrement, " +
	        		DB_Schema.UserDetails.USER_NAME + "text ," +
	        		DB_Schema.UserDetails.PASSWORD + "text ," +
	        		DB_Schema.UserDetails.EMAIL + "text ," +
	        		"text not null" +
	        		" );"
        		);	
        
     // create games list table
        db.execSQL
        		(
	        		"create table " +
	        		DB_Schema.GamLstTable.TABLE_NAME +
	        		"(" +
	        		DB_Schema.GamLstTable._ID +
	        		" integer primary key autoincrement, " +
	        		DB_Schema.GamLstTable.GAME_NAME + "text ," +
	        		DB_Schema.GamLstTable.CREATOR + "text ," +
	        		DB_Schema.GamLstTable.AREA + "text ," +
	        		DB_Schema.GamLstTable.LEADING_SCORE + "Integer ," +
	        		" );"
        		);
     // create game statistics table
        db.execSQL
        		(
	        		"create table " +
	        		DB_Schema.GamStatsc.TABLE_NAME +
	        		"(" +
	        		DB_Schema.GamStatsc._ID +
	        		" integer primary key autoincrement, " +
	        		DB_Schema.GamStatsc.DATE + "text ," +
	        		DB_Schema.GamStatsc.SCORE + "Integer ," +
	        		DB_Schema.GamStatsc.TIME_TO_FINISH + "Integer ," +
	        		" );"
        		);
     // create game scenario table
        db.execSQL
        		(
	        		"create table " +
	        		DB_Schema.GmeScenro.TABLE_NAME +
	        		"(" +
	        		DB_Schema.GmeScenro._ID +
	        		" integer primary key autoincrement, " +
	        		DB_Schema.GmeScenro.CHECKPOINT + "text ," +
	        		DB_Schema.GmeScenro.CHALLENGE + "text ," +
	        		DB_Schema.GmeScenro.RIGHT_ANSWER + "text ," +
	        		DB_Schema.GmeScenro.ANSWER1 + "text ," +
	        		DB_Schema.GmeScenro.ANSWER2 + "text ," +
	        		DB_Schema.GmeScenro.ANSWER3 + "text ," +
	        		DB_Schema.GmeScenro.NEXT_CHECKPOINT + "text ," +

	        		"text not null" +
	        		" );"
	        	);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}