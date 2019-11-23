package vcasino.servlet;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import vcasino.encoder.*;
import vcasino.decoder.*;
import vcasino.core.Match;
import vcasino.core.Match.MatchState;
import vcasino.core.Player;
import vcasino.core.events.GameEvent;
import vcasino.core.events.RulesViolationEvent;
import vcasino.core.exceptions.RulesException;
import vcasino.core.games.*;


@ServerEndpoint(
		value = "/vcasino/{game}/{roomNumber}",
		encoders = {BlindGameStateEncoder.class, GameEventEncoder.class},
		decoders = {GameActionDecoder.class}
)
public class VCasinoServerEndpoint {

	Session userSession = null;
	private final static ConcurrentHashMap<String, VCasinoServerEndpoint> openSessions = new ConcurrentHashMap<>();
	private final static ConcurrentHashMap<String, Match> matches = new ConcurrentHashMap<>();
	private String myUniqueId;
    
  
    @OnOpen
    public void onOpen(Session userSession,@PathParam("game") final String game, @PathParam("roomNumber") final String roomNumber) {
    	Match setupMatch;
    	
    	System.out.println("game " + game + " room "+roomNumber);
    	
        userSession.setMaxIdleTimeout(0); //never time them out
        this.userSession = userSession;
		this.myUniqueId = this.getMyUniqueId();
		
		userSession.getUserProperties().put("roomNumber", roomNumber);
		userSession.getUserProperties().put("game", game);
		userSession.getUserProperties().put("player", new Player(this.myUniqueId,100, this.myUniqueId));
		
		VCasinoServerEndpoint.openSessions.put(myUniqueId, this);
		
		try {
			if(roomNumber.equals("0")) {
				setupMatch = findOrCreateMatch(game);
			} else {
				//Add a new match to the server if one does not exist
				//Pass the Match Constructor game to set the correct ruleset
				setupMatch = VCasinoServerEndpoint.matches.get(game+roomNumber);
				if(setupMatch == null) {
					VCasinoServerEndpoint.matches.putIfAbsent(game+roomNumber, new Match(game+roomNumber, new UnoRuleset()));
					setupMatch = VCasinoServerEndpoint.matches.get(game+roomNumber);
				}
			}
			
			try {
				setupMatch.addPlayer(userSession);
				if(setupMatch.getMatchState() != MatchState.MSTATE_PLAYING && setupMatch.getGameState().countPlayers() >= 4) {
					setupMatch.begin(); //seems legit...
					broadcastMessage("Game start!");
				}
			} catch (RulesException e) {
				System.out.println("RULES: "+e);
				broadcastGameEvent(new RulesViolationEvent(e));
			} finally {
			
				System.out.println("Open Connection...\n Session ID: "+ userSession.getId() );
			}
			//this.sendMessage("Connected!");
			//this.sendMessage(String.format("User ID: %s", this.myUniqueId));
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
    	if (VCasinoServerEndpoint.openSessions.containsKey(this.myUniqueId)) {
            // remove connection
			VCasinoServerEndpoint.openSessions.remove(this.myUniqueId);

            // broadcast this lost connection to all other connected clients
            for (VCasinoServerEndpoint dstSocket : VCasinoServerEndpoint.openSessions.values()) {
                if (dstSocket == this) {
                    // skip me
                    continue;
                }
                dstSocket.sendMessage(String.format("User %s is now gone!", this.myUniqueId));
            }
        }
		System.out.println("Close Connection ...");
    }

    @OnMessage
    public void onMessage(GameAction action, Session userSession) {
    	String roomNumber = (String) userSession.getUserProperties().get("roomNumber");
    	String game = (String) userSession.getUserProperties().get("game");
    	
        //System.out.println("Message from ClientID: " + userSession.getId() + " GameAction " + action);
        Match usersMatch = VCasinoServerEndpoint.matches.get(game+roomNumber);
        Player currentPlayer =(Player) userSession.getUserProperties().get("player");
        
        //Add the action for the event
        try {
        	usersMatch.doAction(action, currentPlayer);
        } catch (RulesException e) {
        	sendMessage(e.getDescription());
        	e.printStackTrace();
		} catch (IllegalStateException ise) {
			usersMatch.dropPlayer(userSession);
			try {
				userSession.close();
			} catch (IOException e) {}
		}
    }

    public void sendMessage(String message) {
    	if(this.userSession != null)
			try {
				this.userSession.getBasicRemote().sendText("{ \"message\": {\"text\": \""+message+"\"}}");
			} catch (IOException e) {
				e.printStackTrace();
			}
    }
    
    public void broadcastMessage(String message) {
    	String game = (String) userSession.getUserProperties().get("game");
		String roomNumber = (String) userSession.getUserProperties().get("roomNumber");
		Match usersMatch = VCasinoServerEndpoint.matches.get(game+roomNumber);
		usersMatch.sendMessage(message);
    }
    
    public void broadcastGameEvent(GameEvent event) {
    	System.out.println("BROADCAST EVENT: "+event.toString());
    	
    	
    }

    private String getMyUniqueId() {
		return Integer.toHexString(this.hashCode());
	}
    
    private Match findOrCreateMatch(String game) throws Exception {
    	for(Match match : VCasinoServerEndpoint.matches.values()) {
    		if(match.countPlayers() < 4)
    			return match;
    	}
    	
    	Match newMatch;
    	
    	switch(game) {
    		case "poker":
    			newMatch = new Match(game+Match.generateMatchId(), new PokerRuleset());
    			return newMatch;
    		case "holdem":
    			newMatch = new Match(game+Match.generateMatchId(), new TexasHoldemRuleSet());
    			return newMatch;
    		case "uno":
    			newMatch = new Match(game+Match.generateMatchId(), new UnoRuleset());
    			return newMatch;
    		case "gofish":
    			newMatch = new Match(game+Match.generateMatchId(), new GoFishRuleset());
    			return newMatch;
    	}
    	
    	throw new Exception("Invalid game type");
    }
}