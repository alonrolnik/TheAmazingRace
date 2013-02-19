package cse.bgu.finalandroidproject.theamazigrace;
/**
 * This class define Game properties
 * @author alon
 *
 */
public class Game {
	private static final int NUM_OF_ANSWERS = 3;
	private long checkpoint;
	private long next_checkpoint;
	private String challenge;
	private String right_answer;
	private String [] wrong_answers = new String[NUM_OF_ANSWERS];
	
	public Game(long checkpoint, long next_checkpoint, String challenge,
			String right_answer, String[] wrong_answers) {
		super();
		this.checkpoint = checkpoint;
		this.next_checkpoint = next_checkpoint;
		this.challenge = challenge;
		this.right_answer = right_answer;
		this.wrong_answers = wrong_answers;
	}
	public long getCheckpoint() {
		return checkpoint;
	}
	public void setCheckpoint(long checkpoint) {
		this.checkpoint = checkpoint;
	}
	public long getNext_checkpoint() {
		return next_checkpoint;
	}
	public void setNext_checkpoint(long next_checkpoint) {
		this.next_checkpoint = next_checkpoint;
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
	public String[] getWrong_answers() {
		return wrong_answers;
	}
	public void setWrong_answers(String[] wrong_answers) {
		this.wrong_answers = wrong_answers;
	}
}