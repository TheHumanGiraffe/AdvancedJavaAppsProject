package vcasino.core;

import java.util.ArrayList;
import java.util.Collections;

import vcasino.constants.*;
/**
 * Deck handles both the draw deck and the discard deck, in case the discard is required.
 * 
 *
 */
public class Deck {
	private ArrayList<Card> cards, discards;
	
	public Deck() {
		cards = new ArrayList<>();
		discards = new ArrayList<>();
		int rank =2;
		for(int i = 1; i <=52; i++) {
			Card c = new Card();
			c.setCardID(i);
			c.setRank(rank);
			
			
			
			if(rank >= 11) {
				switch (rank){
	                case 11:
	                    c.setName("Jack");
	                    break;
	                case 12:
	                    c.setName("Queen");
	                    break;
	                case 13:
	                    c.setName("King");
	                    break;
	                case 14:
	                    c.setName("Ace");
	                    break;
				}
			}
			else {
				c.setName(Integer.toString(rank));
			}
			
			c.setSuit(Constants.SUITS.get(i%4));
			cards.add(c);
			
			if(i%4 == 0) {
				rank++;
			}
		}
		
	}
	
	public Card drawCard() {
		return cards.remove(0);
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	public void discard(Card c) {
		discards.add(c);
	}
	
	public void discardTop() {
		discard(drawCard());
	}
	
	
}
