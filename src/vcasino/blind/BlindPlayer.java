package vcasino.blind;

import java.util.ArrayList;

import javax.websocket.EncodeException;

import vcasino.core.Card;
import vcasino.core.GameState;
import vcasino.core.Player;
import vcasino.core.events.GameEvent;

/**
 * Convenience class for making players appear to have only face-down cards to the client
 * 
 * @author Adam Turk
 * @author Mathias Ham
 *
 */
public class BlindPlayer extends Player {
	
	public BlindPlayer(Player p){
		super(p.getName(), p.getChips(), p.getId());
		
		ArrayList<Card> hand = new ArrayList<>();
		
		for(@SuppressWarnings("unused") Card c : p.getHand()) {
			hand.add(new Card());
		}
		
		this.setHand(hand);
	}
	
	public void sendMessage(String message) {
		// Unused
	}
	
	public void sendEvent(GameEvent event) throws EncodeException {
		// Unused
	}
	
	public void update(GameState state) throws EncodeException {
		// Unused
	}
}
