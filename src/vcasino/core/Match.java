package vcasino.core;

import vcasino.core.events.GameEvent;
import vcasino.core.events.GameState;

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
	
	private ArrayList<Player> players; 
	private Ruleset gameRules;
	private String matchId;
	private Deque<GameEvent> messageQueue;
	private MatchState state = MatchState.MSTATE_INIT;
	private GameState gameState;
	
	public Match() {
		
	}
	public Match(String id, Ruleset rules, Player [] players, Deque<GameEvent> q) {
		matchId = id;
		gameRules = rules;
		Collections.addAll(this.players, players);
		messageQueue = q;
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
	public String toJSONString() {
		String ret = "{\"Match\": {id:\""+matchId+"\", rules: \""+gameRules.getName()+"\", ";
		if(players.size()>0) {
			ret += "players: [";
			for(int i=0;i<players.size()-1;i++)
				ret += players.get(i).toJSONString();
			ret += players.get(players.size()-1).toJSONString()+"],";
		}
		return ret+"state: \"init\"}}";
	}
}
