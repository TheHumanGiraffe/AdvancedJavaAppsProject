package vcasino.blind;

import java.util.ArrayList;

import vcasino.core.Card;
import vcasino.core.Player;

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
	
}
