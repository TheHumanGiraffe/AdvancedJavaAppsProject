package vcasino.core;

import java.util.ArrayList;

/**
 * Deck handles both the draw deck and the discard deck, in case the discard is required.
 * 
 *
 */
public class Deck {
	private ArrayList<Card> cards, discards;
	
	
	public Card drawCard() {
		return cards.remove(0);
	}
	
	
	public void discard(Card c) {
		discards.add(c);
	}
	
	public void discardTop() {
		discard(drawCard());
	}
	
	
}
