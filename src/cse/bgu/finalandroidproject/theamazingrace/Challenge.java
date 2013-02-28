package cse.bgu.finalandroidproject.theamazingrace;

import com.google.android.gms.maps.model.LatLng;

/**
 * This class define Game properties
 * @author alon
 *
 */
public class Challenge {
	private static final int NUM_OF_ANSWERS = 3;
	private LatLng checkpoint;
	private String challenge;
	private String right_answer;
	private String [] wrong_answers = new String[NUM_OF_ANSWERS];

	public Challenge(LatLng checkpoint, String challenge,
			String right_answer, String[] wrong_answers) {
		super();
		this.checkpoint = checkpoint;
		this.challenge = challenge;
		this.right_answer = right_answer;
		this.wrong_answers = wrong_answers;
	}


	public Challenge() {
		// TODO Auto-generated constructor stub
	}

	public LatLng getCheckpoint() {
		return checkpoint;
	}
	public void setCheckpoint(LatLng checkpoint) {
		this.checkpoint = checkpoint;
	}
	public String getChallenge() {
		return challenge;
	}
	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}
	public String getRight_answer() {
		return right_answer;
	}
	public void setRight_answer(String right_answer) {
		this.right_answer = right_answer;
	}
	public String getWrong_answers(int i) {
		return wrong_answers[i];
	}
	public void setWrong_answers(String[] wrong_answers) {
		this.wrong_answers = wrong_answers;
	}
}