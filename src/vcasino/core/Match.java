package vcasino.core;

import vcasino.core.events.GameEvent;
import vcasino.core.events.GameState;
import vcasino.core.exceptions.RulesException;
import vcasino.core.games.PokerRuleset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;

public class Match {

	public enum MatchState {
		MSTATE_INIT,
		MSTATE_AWAITINGPLAYERS,
		MSTATE_PLAYING,
		MSTATE_GAMEOVER,
	};
	
	//private ArrayList<Player> players; 
	private Ruleset gameRules;
	private String matchId;
	private Deque<GameEvent> messageQueue;
	private MatchState state = MatchState.MSTATE_INIT;
	private GameState gameState;
	private Deck deck;
	
	public Match() {
		gameRules = new PokerRuleset();
		gameRules.newDeck();
		gameRules.shuffleDeck();
		gameState = new GameState();
	}
	public Match(String id, Ruleset rules, Player [] players, Deque<GameEvent> q) {
		matchId = id;
		gameRules = rules;
		//Collections.addAll(this.players, players);
		messageQueue = q;
	}
	
	public void addPlayer(Player newPlayer) {
		if(gameState.getPlayers().size() == 0) {
			newPlayer.setTurn(true);
		}
		gameState.addPlayer(newPlayer);
		gameRules.dealHand(newPlayer);
		
	}
	
	public void doAction(String action, Player player) throws RulesException {
		if(player.isTurn()) {
			switch(action) {
				case "drawCard":
					gameRules.drawCard(player);
					//gameState.setPlayer(player);
					break;
				case "getWinner":
					Player winner = gameRules.declareWinner(gameState);
					gameState.setWinner(winner);
					break;
				default:
					System.out.println("NO ACTION");
			}
			player.setTurn(false);
			Player nextPlayer = gameState.getNextPlayer(player);
			nextPlayer.setTurn(true);
			gameRules.setCurrentPlayer(nextPlayer);
		}else {
			System.out.println("Unauthoried turn attemped by Player: " + player);
		}
	}
	public Ruleset getRuleset() {
		return gameRules;
	}
	
	public GameEvent nextEvent() {
		return messageQueue.pollFirst();
	}
	
	void begin() {
		
	}
	
	void checkDoTurn() {
		
	}
	
	void end() {
		
	}
	
	public GameState getGameState() {
		return gameState;
	}
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	/*
	 * public String toJSONString() { String ret =
	 * "{\"Match\": {id:\""+matchId+"\", rules: \""+gameRules.getName()+"\", ";
	 * if(players.size()>0) { ret += "players: ["; for(int
	 * i=0;i<players.size()-1;i++) ret += players.get(i).toJSONString(); ret +=
	 * players.get(players.size()-1).toJSONString()+"],"; } return
	 * ret+"state: \"init\"}}"; }
	 */
}
