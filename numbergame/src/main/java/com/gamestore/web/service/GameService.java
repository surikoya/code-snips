package com.gamestore.web.service;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.gamestore.enums.Outcome;
import com.gamestore.pojo.GameInfo;

@Component
public class GameService {
	private static final int MAXATTEMPTS = 5;
	private static final int LIMIT = 20;

	/**
	 * Returns a random number less than or equal to the given number
	 * 
	 * @return
	 */
	public int getRandomNumber(int upperLimit) {
		Random randomInstance = new Random();
		return randomInstance.nextInt(upperLimit + 1);
	}

	public GameInfo init() {
		GameInfo gameInfo = new GameInfo();
		gameInfo.setAnswer(getRandomNumber(LIMIT));
		return gameInfo;
	}
	public Outcome getOutcome(GameInfo gameInfo, int guess) {
		if (gameInfo.getAnswer() == guess)
			return Outcome.Success;
		gameInfo.setAttemptsMade(gameInfo.getAttemptsMade() + 1);
		if (gameInfo.getAttemptsMade() >= MAXATTEMPTS)
			return Outcome.TooManyFailures;
		return Outcome.Fail;
	}

	public boolean needsInit(GameInfo gameInfo) {
		return gameInfo == null || gameInfo.getAttemptsMade() >= MAXATTEMPTS;
	}
}
