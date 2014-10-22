package com.gamestore.pojo;

import java.io.Serializable;

/**
 * Holder class for game information
 * @author Suresh
 */
public class GameInfo implements Serializable{
	private static final long serialVersionUID = -3045092507531635992L;
	private int answer;
	private int attemptsMade;

	public int getAnswer() {
		return answer;
	}
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	public int getAttemptsMade() {
		return attemptsMade;
	}
	public void setAttemptsMade(int attemptsMade) {
		this.attemptsMade = attemptsMade;
	}
	
	
}
