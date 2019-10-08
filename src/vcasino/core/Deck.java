package vcasino.core;

import java.util.ArrayList;

/**
 * Deck handles both the draw deck and the discard deck, in case the discard is required.
 * 
 * @author admin
 *
 */
public class Deck {
	private ArrayList<Card> cards, discards;
	
	
	public Card drawCard() {
		return cards.get(0);
	}
	
	
	public void discard(Card c) {
		discards.add(c);
	}
	
	public void discardTop() {
		discard(drawCard());
	}
	
	
}
