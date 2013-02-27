package cse.bgu.finalandroidproject.theamazingrace;

import android.provider.BaseColumns;

public class DB_Schema {

	/*
	 * Create private constructor to prevent instance of this class
	 */
	private DB_Schema() {}

	public static final String dbName = "myDB.db";
	public static abstract class UserDetails implements BaseColumns{

	    public static final String TABLE_NAME = "user_details";
	    public static final String USER_NAME = "user_name";
	    public static final String EMAIL = "email";	
	    public static final String PASSWORD = "password";
	    
	}	    
	
	public static abstract class GamLstTable implements BaseColumns{
		
	    public static final String TABLE_NAME = "games_list";
	    public static final String GAME_NAME = "game_name";
	    public static final String CREATOR = "creator";
	    public static final String AREA = "area";	
	    public static final String LEADING_SCORE = "leading_score";	    
	}
	
	public static abstract class GamStatsc implements BaseColumns{

	    public static final String TABLE_NAME = "game_statistics";
	    public static final String DATE = "date";
	    public static final String SCORE = "score";
	    public static final String TIME_TO_FINISH= "time_to_finish";	
	    
	}	    
	public static abstract class GmeScenro implements BaseColumns{

	    public static final String TABLE_NAME = "gamescenario";
	    public static final String CHECKPOINT_LAT = "checkpointlat";
	    public static final String CHECKPOINT_LONG = "checkpointlong";
	    public static final String CHALLENGE = "challenge";
	    public static final String RIGHT_ANSWER = "rightanswer";	
	    public static final String ANSWER1 = "answer1";
	    public static final String ANSWER2 = "answer2";
	    public static final String ANSWER3 = "answer3";
	    public static final String NEXT_CHECKPOINT_LAT = "nextcheckpointlat";
	    public static final String NEXT_CHECKPOINT_LONG = "nextcheckpointlong";
	    
	}	    

}
