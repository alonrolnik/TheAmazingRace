package cse.bgu.finalandroidproject.theamazingrace;

import java.util.List;

public class Game {

	String creator;
	String game_name;
	String area;
	List<Challenge> game_challenges;
	public Game(String creator, String game_name, String area,
			List<Challenge> game_challenges) {
		super();
		this.creator = creator;
		this.game_name = game_name;
		this.area = area;
		this.game_challenges = game_challenges;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getGame_name() {
		return game_name;
	}
	public void setGame_name(String game_name) {
		this.game_name = game_name;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public List<Challenge> getGame_challenges() {
		return game_challenges;
	}
	public void setGame_challenges(List<Challenge> game_challenges) {
		this.game_challenges = game_challenges;
	}
	
}
