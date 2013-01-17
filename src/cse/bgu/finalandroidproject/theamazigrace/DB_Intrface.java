package cse.bgu.finalandroidproject.theamazigrace;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DB_Intrface {
	 // Database fields
	  private SQLiteDatabase database;
	  private MySQLiteOpenHelper dbHelper;
	  private String[] allColumns = { MyDB.JokesTable._ID,
			  MyDB.JokesTable.COLUMN_NAME_JOKES, MyDB.JokesTable.COLUMN_NAME_CREATOR,
			  MyDB.JokesTable.COLUMN_NAME_DATE, MyDB.JokesTable.COLUMN_NAME_LIKE};

	  public ManipulateDB(Context context) {
	    dbHelper = new MySQLiteOpenHelper(context);
	  }

	public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  /*
	   * Insert new joke into jokes DB as a new row
	   */
	  public void createJoke(Joke joke ) {
	    ContentValues values = new ContentValues();
	    values.put(MyDB.JokesTable.COLUMN_NAME_JOKES, joke.getJoke());
	    values.put(MyDB.JokesTable.COLUMN_NAME_CREATOR, joke.getAuthor());
	    values.put(MyDB.JokesTable.COLUMN_NAME_DATE, joke.getDate());
	    values.put(MyDB.JokesTable.COLUMN_NAME_LIKE, joke.getLikes());

	    long insertId = database.insert(MyDB.JokesTable.TABLE_NAME, null,  values);
	  //  Cursor cursor = database.query(MyDB.JokesTable.TABLE_NAME ,allColumns, MyDB.JokesTable._ID + " = " + insertId, null,
	  //  							null, null, null);
	 //   cursor.moveToFirst();
	 //   Joke newJoke = cursorToJoke(cursor);
	//    cursor.close();
	    //return newJoke;
	  }

	  /*
	   * Delete a joke row 
	   */
	  public void deleteJoke(Joke joke) {
	    long id = joke.getId();
	    System.out.println("Joke deleted with id: " + id);
	    database.delete(MyDB.JokesTable.TABLE_NAME, MyDB.JokesTable._ID + " = " + id, null);
	  }

	  public void updateJoke(Joke joke){
		  long id = joke.getId();
		  ContentValues cv = new ContentValues();
		  cv.put(MyDB.JokesTable.COLUMN_NAME_LIKE, joke.getLikes());
		  database.update(MyDB.JokesTable.TABLE_NAME, cv, MyDB.JokesTable._ID + " = " + id, null);
	  }
	  /*
	   * get all the jokes from the table
	   */
	  public List<Joke> getAllJokes() {
	    List<Joke> jokes = new ArrayList<Joke>();
	    Cursor cursor = database.query(MyDB.JokesTable.TABLE_NAME, allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Joke joke = cursorToJoke(cursor);
	      jokes.add(joke);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return jokes;
	  }

	  public Joke getJoke(long id) {
		  Cursor cursor =  database.query( MyDB.JokesTable.TABLE_NAME,
				  							allColumns, MyDB.JokesTable._ID + "=?",
				  							new String[] { String.valueOf(id) },
				  							null,null,null);
		  cursor.moveToFirst();
		  Joke joke = cursorToJoke(cursor);
		  cursor.close();
		  return joke;
	  }

	  private Joke cursorToJoke(Cursor cursor) {
	    Joke joke = new Joke();
	    joke.setId(cursor.getLong(0));
	    joke.setJoke(cursor.getString(1));
	    joke.setAuthor(cursor.getString(2));
	    joke.setDate(cursor.getString(3));
	    joke.setLikes(cursor.getString(4));
	    return joke;
	  }

}
