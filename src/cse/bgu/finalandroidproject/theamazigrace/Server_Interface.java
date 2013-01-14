package cse.bgu.finalandroidproject.theamazigrace;

import android.database.Cursor;

public class Server_Interface extends Http_Interface{
	public String url = "www.my_server.com";
	
	/**
	 * send to the server registration request
	 * @param user
	 * @return true if user successfully register else return false
	 */
	public boolean register(User user){
		// sign in into the server
		// then login
		return log_in(user);// Not implemented
	}
	
	/**
	 * Login to server
	 * @param user
	 * @return
	 */
	public boolean log_in(User user){
		return false;
	}
	/**
	 * get the available games
	 * @return
	 */
	public Cursor get_games_list(){
		return null;
	}
	
	/**
	 * get score board of the game
	 * @return
	 */
	public Cursor get_score_board(int game_id){
		return null;
	}
	
	/**
	 * get game details
	 * @param game_id
	 * @return
	 */
	public Cursor get_game(int game_id){
		return null;
	}
	
	/**
	 * get user get_statistics
	 * @param game_id
	 * @return
	 */
	public Cursor get_statistics(int user_id){
		return null;
	}

	
	/**
	 * upload new game to the server
	 * @param game
	 */
	public void create_new_game(Game game){
		
	}
	
	/**
	 * update user details
	 * @param user
	 */
	public void update_user_datails(User user){
		
	}
	
	/**
	 * update existing game
	 * @param game
	 */
	public void update_game(Game game){
		
	}
	
}
