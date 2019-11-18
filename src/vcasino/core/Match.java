package vcasino.core;

import vcasino.core.events.GameEvent;
import vcasino.core.events.GameState;
import vcasino.core.exceptions.RulesException;
import vcasino.core.games.PokerRuleset;

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
	
	public Match() {
		gameState = new GameState();
		
		gameRules = new PokerRuleset(gameState);
		gameRules.newDeck();
		gameRules.shuffleDeck();
		
		
		matchId = generateMatchId();
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
	
	public void doAction(GameEvent event, Player player) throws RulesException {
		if(player.isTurn() && player.isActive()) {
			switch(event.getAction()) {
				case "draw":
					gameRules.drawCard(player);
					//gameState.setPlayer(player);
					break;
				case "play":
		        	break;
		        case "fold":
		        	gameRules.fold(player);
		        	break;
		        case "bet":
		        	gameRules.placeBet(player, event.getBetAmount());
		        	break;
				case "winner":
					gameRules.declareWinner();
							
					break;
				default:
					System.out.println("NO ACTION");
			}
			gameState.setCurrentPlayer(gameRules.advanceTurn(player, gameState.getPlayers()));
			
		}else {
			throw new RulesException("Turn", "Unauthorized turn attemped by Player: " ,player);
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
	
	private String generateMatchId() {
		String ret="";
		for(int i=0;i<16;i++) {
			ret += (Math.random() * 64 + 'a');
		}
		return ret;
	}
}
