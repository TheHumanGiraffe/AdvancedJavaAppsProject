package vcasino.core;

import vcasino.blind.BlindGameState;
import vcasino.core.events.ChatEvent;
import vcasino.core.events.GameEvent;
import vcasino.core.exceptions.RulesException;
import vcasino.servlet.GameAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;

import javax.websocket.EncodeException;
import javax.websocket.Session;

public class Match {

	public enum MatchState {
		MSTATE_INIT,
		MSTATE_AWAITINGPLAYERS,
		MSTATE_PLAYING,
		MSTATE_GAMEOVER,
	};
	 
	private Ruleset gameRules;
	private String matchId;
	private ArrayList<Session> sessions;
	private Deque<GameEvent> messageQueue;
	private MatchState state = MatchState.MSTATE_INIT;
	private GameState gameState;
	private GameAction lastAction;
	
	public Match(String id, Ruleset rules/* , Deque<GameEvent> q */) {
		matchId = id;
		gameRules = rules;
		
		gameState = new GameState(gameRules);
		
		sessions = new ArrayList<>();
		//Collections.addAll(this.players, players);
		//messageQueue = q;
	}
	
	public void addPlayer(Session newSession) throws RulesException {
		Player newPlayer = (Player)newSession.getUserProperties().get("player");
		
		if(gameState.countPlayers() == 4)
			throw new RulesException("Players", "Too many players!", newPlayer);
		
		if(state == MatchState.MSTATE_PLAYING)
			gameRules.dealHand(gameState, newPlayer);
		
		if(gameState.countPlayers() == 0) {
			newPlayer.setTurn(true);
		}
		
		gameState.addPlayer(newPlayer);
		
		sessions.add(newSession);
		
		if(gameState.countPlayers() < 4)
			state = MatchState.MSTATE_AWAITINGPLAYERS;
		
		update(gameState);
	}
	
	public void dropPlayer(Session deadSession) {
		Player player = (Player)deadSession.getUserProperties().get("player");
		player.deactivate();
		
		//keep going without them or just pause?
		
		update(gameState);
	}
	
	//Needs to be GameAction because they will be addtional info tied to it other than the action. IE card ID and bet amount
	public void doAction(GameAction action, Player player) throws RulesException {
		lastAction = action;
		if(player.isTurn()) {
			switch(action.action) {
				case "draw":
					gameRules.drawCard(gameState, player);
					//gameState.setPlayer(player);
					break;
				case "play":
					gameRules.playCard(gameState, player, Integer.parseInt(action.arg0));
					break;
				case "chat":
					sendEvent(new ChatEvent(player, action.arg0));
					break;
		        case "fold":
		        	gameRules.fold(gameState, player);
		        	sendMessage(player.getName()+" has folded.");
		        	break;
		        case "bet":
		        	gameRules.placeBet(gameState, player, Integer.parseInt(action.arg0));
		        	break;
				case "winner":
					Player winner = gameRules.declareWinner(gameState);
					gameState.setWinner(winner);
					break;
				default:
					System.out.println("NO ACTION");
			}
			if(gameRules.gameOver(gameState)) {
				Player winner = gameRules.declareWinner(gameState);
				sendMessage(winner.getName() + " has won the round!");
			}
			gameState.setCurrentPlayer(gameRules.advanceTurn(player, gameState.getPlayers()));
			update(gameState);
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
		update(gameState);
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
	
	public int countPlayers() {
		return sessions.size();
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
	
	public void sendMessage(String message) {
		String msg = "{ \"message\": {\"text\": \""+message+"\"}}";
		for(Session userSession : sessions) {
			try {
				userSession.getBasicRemote().sendText(msg);
			} catch (IOException e) {
				e.printStackTrace();
				((Player)userSession.getUserProperties().get("player")).deactivate();
			}
		}
	}
	
	private void sendEvent(GameEvent event) {
		for(Session userSession : sessions) {
			try {
				userSession.getBasicRemote().sendObject(event);
			} catch (IOException e) {
				e.printStackTrace();
				((Player)userSession.getUserProperties().get("player")).deactivate();
			} catch (EncodeException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void update(GameState state) {
		try {
			System.out.println("updating gamestate");
			for(Session userSession : sessions) {
				Player player = ((Player)userSession.getUserProperties().get("player"));
				try {
					BlindGameState blindState = new BlindGameState(state, player);
					userSession.getBasicRemote().sendObject(blindState);
				} catch (IOException e) {
					e.printStackTrace();
					player.deactivate();
				}
			}
		} catch(EncodeException ee) {
			ee.printStackTrace();
		}
	}
}
