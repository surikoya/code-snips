package com.gamestore.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.gamestore.web.service.GameService;

public class GameServiceTest {

	private GameService gs;
	@Before 
	public void init() {
		gs = new GameService();
	}

	@Test
	public void testInit() {
		assertNotNull("Game service cannot be null", gs);
	}

	@Test
	public void testGameLogic() {
		assertNotNull("Game service cannot be null", gs);
		int limit = 20;
		int answer = gs.getRandomNumber(limit);
		boolean answered = false;
		for(int i =0 ; i < 5; i++) {
			int attempt = gs.getRandomNumber(limit);
			System.out.println("Attempted answer is:" + attempt);
			if (answer == attempt) {
				answered = true;
				System.out.println("Correct Guess");
				break;
			}
		}
		if (!answered)
			System.out.println("Too many attemts. Try again.");
	}
	
	
}
