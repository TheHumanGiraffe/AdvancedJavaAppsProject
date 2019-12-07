package vcasino.core;

import vcasino.blind.BlindGameState;
import vcasino.core.events.ChatEvent;
import vcasino.core.events.GameEvent;
import vcasino.core.exceptions.RulesException;
import vcasino.db.DatabaseConnection;
import vcasino.servlet.GameAction;

import java.io.IOException;
import java.sql.SQLException;
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
	private boolean complete=false;
	private Session deadSession=null; //this is so we don't mess with the session array while we iterate over it
	
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
	
	public void dropPlayer(Session dropSession) {
		try {
			Player player = (Player)dropSession.getUserProperties().get("player");
			player.deactivate();
		} catch(IllegalStateException ise) {
			//it's possible we're dealing with a truly dead session...
			deadSession = dropSession;
		}
		
		//keep going without them or just pause?
		
		update(gameState);
	}
	
	//Needs to be GameAction because they will be additional info tied to it other than the action. IE card ID and bet amount
	public void doAction(GameAction action, Player player) throws RulesException {
		lastAction = action;
		
		if(deadSession != null) {
			sessions.remove(deadSession);
			deadSession = null;
		}
		
		if(player.isTurn()) {
			switch(action.action) {
				case "draw":
					gameRules.drawCard(gameState, player);
					//gameState.setPlayer(player);
					break;
				case "play":
					gameRules.setArg1(action.arg1);
					sendEvent(gameRules.playCard(gameState, player, Integer.parseInt(action.arg0)));
					break;
				case "chat":
					sendEvent(new ChatEvent(action.arg0));
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
				updateDB(gameState, winner);
				sendMessage(winner.getName() + " has won the round!");
				complete=true;
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
		sendEvent(new GameEvent("Begin Match!", 1));
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
			ret += (char)((int)(Math.random() * 26) + 65);
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
		if(event == null)
			return;
		for(Session userSession : sessions) {
			try {
				if(event.getTarget() == null || event.getTarget() == userSession.getUserProperties().get("player"))//lazy
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
				Player player=null;
				try {
					player = ((Player)userSession.getUserProperties().get("player"));
					BlindGameState blindState = new BlindGameState(state, player);
					userSession.getBasicRemote().sendObject(blindState);
				} catch (IOException e) {
					e.printStackTrace();
					player.deactivate();
				} catch (IllegalStateException ise) {
					dropPlayer(userSession);
				}
			}
		} catch(EncodeException ee) {
			ee.printStackTrace();
		}
	}
	
	private void updateDB(GameState state, Player winner) {
		ArrayList<Player> players = state.getPlayers();
		DatabaseConnection connection = new DatabaseConnection();
		int pot = 0;
		for(int i=0;i<players.size();i++) {
			Player player = players.get(i);
			if (player != winner){
				System.out.println(player.getActiveBet());
				System.out.println(pot);
				String update = "UPDATE player SET losses=losses+1, chips=chips-"+player.getActiveBet()+" where player.name='"+player.getName()+"';";
				pot += player.getActiveBet();
				player.setChips(player.getChips()-player.getActiveBet());
				player.setActiveBet(-1);
				try {
					connection.executeQuery(update);
				} catch (SQLException | RulesException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				
			}			
		}
		String update = "UPDATE player SET wins=wins+1, chips=chips+"+pot+" where player.name='"+winner.getName()+"';";
		winner.setChips(winner.getChips()+pot);
		try {
			connection.executeQuery(update);
		} catch (SQLException | RulesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
