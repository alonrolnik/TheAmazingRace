package cse.bgu.finalandroidproject.theamazigrace;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	
	private static final String USER_DEATAILS_ENTRIES = 
			DB_Schema.UserDetails._ID + COMMA_SEP +
			DB_Schema.UserDetails.USER_NAME + COMMA_SEP +
			DB_Schema.UserDetails.EMAIL + COMMA_SEP +
			DB_Schema.UserDetails.PASSWORD;

	private static final String GAMES_ENTRIES = 
			DB_Schema.GamLstTable._ID + COMMA_SEP +
			DB_Schema.GamLstTable.GAME_NAME + COMMA_SEP +
			DB_Schema.GamLstTable.CREATOR + COMMA_SEP +
			DB_Schema.GamLstTable.AREA + COMMA_SEP +
			DB_Schema.GamLstTable.LEADING_SCORE;

	private static final String GAME_STATISTICS_ENTRIES = 
			DB_Schema.GamStatsc._ID + COMMA_SEP +
			DB_Schema.GamStatsc.DATE + COMMA_SEP +
			DB_Schema.GamStatsc.TIME_TO_FINISH + COMMA_SEP +
			DB_Schema.GamStatsc.SCORE;

	private static final String GAME_SCENARIO_ENTRIES = 
			DB_Schema.GmeScenro._ID + COMMA_SEP +
			DB_Schema.GmeScenro.CHECKPOINT + COMMA_SEP +
			DB_Schema.GmeScenro.CHALLENGE + COMMA_SEP +
			DB_Schema.GmeScenro.RIGHT_ANSWER + COMMA_SEP +
			DB_Schema.GmeScenro.AVAILABLE_ANSWERS + COMMA_SEP +
			DB_Schema.GmeScenro.NEXT_CHECKPOINT;

	
	private static final String SQL_CREATE_ENTRIES = "create table " +
			MyDB.JokesTable.TABLE_NAME + "(" +
			MyDB.JokesTable._ID + " integer primary key autoincrement, " +
			MyDB.JokesTable.COLUMN_NAME_JOKES + TEXT_TYPE + COMMA_SEP +
			MyDB.JokesTable.COLUMN_NAME_CREATOR + TEXT_TYPE + COMMA_SEP +
			MyDB.JokesTable.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP + 
			MyDB.JokesTable.COLUMN_NAME_LIKE + TEXT_TYPE +
			" text not null" +
			" );";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME_ENTRIES;
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "jokes.db";

	
	
	
	//create helper object to manipulate database
	public MySQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
        db.execSQL(SQL_CREATE_ENTRIES);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);	

	}

}
