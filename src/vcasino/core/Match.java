package vcasino.core;

import vcasino.core.events.GameEvent;
import vcasino.core.events.GameState;
import vcasino.core.exceptions.RulesException;

import java.util.Deque;

public class Match {

	public enum MatchState {
		MSTATE_INIT,
		MSTATE_AWAITINGPLAYERS,
		MSTATE_PLAYING,
		MSTATE_GAMEOVER,
	};
	 
	private Ruleset gameRules;
	private String matchId;
	private Deque<GameEvent> messageQueue;
	private MatchState state = MatchState.MSTATE_INIT;
	private GameState gameState;
	
	public Match(String id, Ruleset rules/* , Deque<GameEvent> q */) {
		matchId = id;
		gameRules = rules;
		
		gameState = new GameState(gameRules);
		
		//Collections.addAll(this.players, players);
		//messageQueue = q;
	}
	
	public void addPlayer(Player newPlayer) throws RulesException {
		
		if(gameState.countPlayers() == 4)
			throw new RulesException("Players", "Too many players!", newPlayer);
		
		if(state == MatchState.MSTATE_PLAYING)
			gameRules.dealHand(gameState, newPlayer);
		
		if(gameState.countPlayers() == 0) {
			newPlayer.setTurn(true);
		}
		
		gameState.addPlayer(newPlayer);
		
		if(gameState.countPlayers() < 4)
			state = MatchState.MSTATE_AWAITINGPLAYERS;
		
	}
	
	//Needs to be GameEvent becuase they will be addtional info tied to it other than the action. IE card ID and bet amount
	public void doAction(GameEvent action, Player player) throws RulesException {
		if(player.isTurn()) {
			switch(action.getAction()) {
				case "draw":
					gameRules.drawCard(gameState, player);
					//gameState.setPlayer(player);
					break;
				case "play":
		        	break;
		        case "chat":
		        	break;
		        case "fold":
		        	gameRules.fold(player);
		        	break;
		        case "bet":
		        	gameRules.placeBet(gameState, player, action.getBetAmount());
		        	break;
				case "winner":
					Player winner = gameRules.declareWinner(gameState);
					gameState.setWinner(winner);
					break;
				default:
					System.out.println("NO ACTION");
			}
			if(gameRules.gameOver(gameState)) {
				gameRules.declareWinner(gameState);
			}
			gameState.setCurrentPlayer(gameRules.advanceTurn(player, gameState.getPlayers()));
			
		}else {
			throw new RulesException("Turn", "Unauthorized turn attemped by Player", player);
		}
	}
	public Ruleset getRuleset() {
		return gameRules;
	}
	
	public GameEvent nextEvent() {
		return messageQueue.pollFirst();
	}
	
	public void begin() throws RulesException {
		gameRules.beginMatch(gameState);
		state = MatchState.MSTATE_PLAYING;
	}
	
	void checkDoTurn() {
		
	}
	
	void end() {
		
	}
	
	public String getMatchId() {
		return matchId;
	}
	
	public MatchState getMatchState() {
		return state;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	public static final String generateMatchId() {
		String ret="";
		for(int i=0;i<16;i++) {
			ret += (Math.random() * 64 + 'a');
		}
		return ret;
	}
	
	private boolean matchInDb() {
		return false;
	}
}
