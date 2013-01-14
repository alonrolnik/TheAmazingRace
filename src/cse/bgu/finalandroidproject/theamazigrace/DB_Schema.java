package cse.bgu.finalandroidproject.theamazigrace;

import android.provider.BaseColumns;

public class DB_Schema {

	/*
	 * Create private constructor to prevent instance of this class
	 */
	private DB_Schema() {}

	
	public static abstract class UserDetails implements BaseColumns{

	    public static final String TABLE_NAME = "user_details";
	    public static final String COLUMN_USER_ID = "user_id";
	    public static final String COLUMN_NAME_USER_NAME = "user_name";
	    public static final String COLUMN_NAME_EMAIL = "email";	
	    public static final String COLUMN_NAME_PASSWORD = "password";
	    
	}	    
	
	public static abstract class GamLstTable implements BaseColumns{
		
	    public static final String TABLE_NAME = "games_list";
	    public static final String COLUMN_NAME_GAME_NAME = "game_name";
	    public static final String COLUMN_NAME_CREATOR = "creator";
	    public static final String COLUMN_NAME_AREA = "area";	
	    public static final String COLUMN_NAME_LEADING_SCORE = "leading_score";	    
	}
	
	public static abstract class GamStatsc implements BaseColumns{

	    public static final String TABLE_NAME = "game_statistics";
	    public static final String COLUMN_NAME_DATE = "date";
	    public static final String COLUMN_NAME_SCORE = "score";
	    public static final String COLUMN_NAME_TIME_TO_FINISH= "time_to_finish";	
	    
	}	    
	public static abstract class GmeScenro implements BaseColumns{

	    public static final String TABLE_NAME = "game_scenario";
	    public static final String COLUMN_NAME_CHECKPOINT = "checkpoint";
	    public static final String COLUMN_NAME_CHALLENGE = "challenge";
	    public static final String COLUMN_NAME_RIGHT_ANSWER = "right_answer";	
	    public static final String COLUMN_NAME_NEXT_CHECKPOINT = "next_checkpoint";
	    
	}	    

}
