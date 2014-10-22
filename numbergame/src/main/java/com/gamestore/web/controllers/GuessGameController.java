package com.gamestore.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamestore.enums.Outcome;
import com.gamestore.pojo.GameInfo;
import com.gamestore.web.service.GameService;

@Controller
public class GuessGameController {

	private static final String GUESSGAME = "guess";
	private static final String GAMEPATH = "/game";
	@Autowired private GameService gameSvc;
	private static final String GAME = "GAME";
	

	@RequestMapping(value="/guessgame", method=RequestMethod.GET)
	public String showGuessPage(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession userSession = request.getSession();
		GameInfo gameInfo = (GameInfo) userSession.getAttribute(GAME);
		System.out.println("Game Info:" + gameInfo);
		if (gameSvc.needsInit(gameInfo)) {
			System.out.println("Initializing...");
			setGameInfoInSession(request);
		}
		return "guess.html";
	}

	@RequestMapping(value=GAMEPATH, method=RequestMethod.PUT)
	public @ResponseBody String startGame(@RequestParam("gamename") String gameName, HttpServletRequest request,
			HttpServletResponse response) {
		if (GUESSGAME.equalsIgnoreCase(gameName)) {
			setGameInfoInSession(request);
			return "Success";
		}
		return "Fail";
	}

	@RequestMapping(value=GAMEPATH,method=RequestMethod.GET)
	public @ResponseBody Map<String, String> play(
			@RequestParam("gamename") String gameName,
			@RequestParam(GUESSGAME) int guess, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> resultSummary = new HashMap<String, String>();

		Outcome result = Outcome.Fail;

		if (GUESSGAME.equalsIgnoreCase(gameName)) {
			HttpSession userSession = request.getSession();
			GameInfo gameInfo = (GameInfo) userSession.getAttribute(GAME);
			result = gameSvc.getOutcome(gameInfo, guess);
			userSession.setAttribute(GAME, gameInfo);
			resultSummary.put("ATTEMPTS", String.valueOf(gameInfo.getAttemptsMade()));
			if (result.equals(Outcome.TooManyFailures)) {
				resultSummary.put("ACTUALNUMBER", String.valueOf(gameInfo.getAnswer()));
			}
			
		}
		resultSummary.put("OUTCOME", result.name());
		return resultSummary;
	}
	
	private void setGameInfoInSession(HttpServletRequest request) {
		HttpSession userSession = request.getSession();
		GameInfo gameInfo = gameSvc.init();
		userSession.setAttribute(GAME, gameInfo);
	}
}
