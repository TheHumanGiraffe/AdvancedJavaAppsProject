package vcasino.core;

import java.util.ArrayList;
import java.util.Collections;

import vcasino.constants.*;
/**
 * Deck handles both the draw deck and the discard deck, in case the discard is required.
 * This is a standard, 52 card deck of common usage.
 *
 */
public class Deck {
	protected ArrayList<Card> cards, discards;
	
	public Deck() {
		cards = new ArrayList<>();
		discards = new ArrayList<>();
		int rank =2;
		for(int i = 1; i <=52; i++) {
			Card c = new Card(i, Constants.SUITS.get(i%4));
			
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
			
			cards.add(c);
			
			if(i%4 == 0) {
				rank++;
			}
		}
		
	}
	
	public int size() {
		return 52;
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public Card drawCard() {
		return cards.remove(0);
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	public void mergeDiscard() {
		int sz = discards.size();
		for(int i=1;i<sz;i++)
			cards.add(discards.remove(1));
	}
	
	public void discard(Card c) {
		discards.add(0, c);
	}
	
	public void discardTop() {
		
		discard(drawCard());
	}
	
	public Card getDiscard(int index) {
		if(discards.size()>0)
			return discards.get(index);
		return new Card();
	}
}
