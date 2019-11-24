package vcasino.core.games;

import java.util.ArrayList;

import vcasino.core.Card;
import vcasino.core.Deck;

public class UnoDeck extends Deck {

	private static final String suitMap[] = {"none", 
			"red", "red", "red", "red", "red", "red", "red", "red", "red", "red", "red", "red", "red", "any",
			"yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "any",
			"green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "any", 
			"blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "any", 
			"none", "red", "red", "red", "red", "red", "red", "red", "red", "red", "red", "red", "red", "any",
			"none", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "any",
			"none", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "any",
			"none", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "any"};
	
	public static final int rankMap[] = {9001, //check the scouter
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 100, 110, 120, 300,
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 100, 110, 120, 300,
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 100, 110, 120, 300,
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 100, 110, 120, 300,
			-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 100, 110, 120, 400,
			-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 100, 110, 120, 400,
			-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 100, 110, 120, 400,
			-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 100, 110, 120, 400};
	
	public UnoDeck() {
		cards = new ArrayList<>();
		discards = new ArrayList<>();
		
		for(int card=1;card<113;card++) {
			if(rankMap[card] < 0)
				continue;
			Card c = new Card(card, suitMap[card]);
			c.setRank(rankMap[card]);
			cards.add(c);
		}
	}
	
	@Override
	public int size() {
		return 108;
	}
}
