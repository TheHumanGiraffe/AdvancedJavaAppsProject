package vcasino.client;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import javax.websocket.*;
import javax.websocket.WebSocketContainer;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import vcasino.encoder.GameStateEncoder;
import vcasino.core.Match;
import vcasino.core.Player;
import vcasino.core.events.GameEvent;
import vcasino.core.events.GameState;
import vcasino.decoder.GameEventDecoder;


@ServerEndpoint(
		value ="/vcasion/{game}/{roomNumber}",
		encoders = {GameStateEncoder.class},
		decoders = {GameEventDecoder.class}
)
public class VCasinoClientEndpoint {

	URI uri;
	Session userSession = null;
	private final static HashMap<String, VCasinoClientEndpoint> openSessions = new HashMap<>();
	private final static HashMap<String, Match> match = new HashMap<>();
	private String myUniqueId;
    
    public VCasinoClientEndpoint(URI endpointURI) {
        uri = endpointURI;
	}

    @OnOpen
    public void onOpen(Session userSession,@PathParam("game") final String game, @PathParam("roomNumber") final String roomNumber) {
        System.out.println("opening websocket");
        this.userSession = userSession;
		this.myUniqueId = this.getMyUniqueId();
		
		userSession.getUserProperties().put("roomNumber", roomNumber);
		userSession.getUserProperties().put("game", game);
		userSession.getUserProperties().put("player", new Player());
		
		VCasinoClientEndpoint.openSessions.put(myUniqueId, this);
		
		//Add a new match to the server if one does not exist
		VCasinoClientEndpoint.match.putIfAbsent(game+roomNumber, new Match());
		
		for(VCasinoClientEndpoint currentClient  : VCasinoClientEndpoint.openSessions.values()) {
			if(currentClient == this) {
				continue;
			}
			//Checks if the connection is open, if the game and roomNumbers match
			if(VCasinoClientEndpoint.openSessions.get(this.myUniqueId).userSession.isOpen()  &&
					VCasinoClientEndpoint.openSessions.get(currentClient.myUniqueId).userSession.getUserProperties().get("game").equals(this.userSession.getUserProperties().get("game")) &&
					VCasinoClientEndpoint.openSessions.get(currentClient.myUniqueId).userSession.getUserProperties().get("roomNumber").equals(this.userSession.getUserProperties().get("roomNumber"))) {
					currentClient.sendMessage(String.format("User %s is now connected!", this.myUniqueId));
			}		
		}
		System.out.println("Open Connection...\n Session ID: "+ userSession.getId() );
		this.sendMessage("Connected!");
		this.sendMessage(String.format("User ID: %s", this.myUniqueId));
    }
    
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
    	if (VCasinoClientEndpoint.openSessions.containsKey(this.myUniqueId)) {
            // remove connection
			VCasinoClientEndpoint.openSessions.remove(this.myUniqueId);

            // broadcast this lost connection to all other connected clients
            for (VCasinoClientEndpoint dstSocket : VCasinoClientEndpoint.openSessions.values()) {
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
    public void onMessage(GameEvent gameEvent, Session userSession) {
    	String roomNumber = (String) userSession.getUserProperties().get("roomNumber");
    	String game = (String) userSession.getUserProperties().get("game");
    	
        System.out.println("Message from ClientID:" + userSession.getId() + "GameEvent" + gameEvent);
        Match usersMatch = VCasinoClientEndpoint.match.get(game+roomNumber);
      //Add the action for the event
        switch(gameEvent.getAction()) {
	        case "draw":
	        	break;
	        case "play":
	        	break;
	        case "chat":
	        	break;
	        case "fold":
	        	break;
	        case "bet":
	        	break;
	        default:
	        	System.out.println("No event Found");
        }
        
        for(VCasinoClientEndpoint connectedUser : VCasinoClientEndpoint.openSessions.values()) {
        	if(checkGameAndRoom(connectedUser)) {
        		sendGameState(connectedUser, usersMatch.getGameState());
        	}
        }
        
    }

    public void sendMessage(String message) {
    	if(this.userSession != null)
    		this.userSession.getAsyncRemote().sendText(message);
    }
    
    //Sends state of game as JSON object 
    public void sendGameState(VCasinoClientEndpoint client, GameState state) {
    	client.userSession.getAsyncRemote().sendObject(state);
    }
    public void sendRequest(String command, String payload) {
    	sendMessage("{\"request\": {\"command\": \""+command+"\",\n\"payload\": \""+payload+"\"\n}}");
    }

    private String getMyUniqueId() {
		return Integer.toHexString(this.hashCode());
	}
    
    private boolean checkGameAndRoom(VCasinoClientEndpoint client) {
    	if(VCasinoClientEndpoint.openSessions.get(this.myUniqueId).userSession.isOpen() &&
    	VCasinoClientEndpoint.openSessions.get(client.myUniqueId).userSession.getUserProperties().get("game")
    	.equals(this.userSession.getUserProperties().get("game")) 
    	&& VCasinoClientEndpoint.openSessions.get(client.myUniqueId).userSession.getUserProperties().get("roomNumber")
    	.equals(this.userSession.getUserProperties().get("roomNumber"))) {
    		return true;
    	}
    	return false;
    }
    
}