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
			"red", "red", "red", "red", "red", "red", "red", "red", "red", "red", "red", "red", "red", "any",
			"yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "yellow", "any",
			"green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "any",
			"blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "any", "red", "yellow", "green", "blue"};
	
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
	
	public static int getColoredCardId(int oldId, String color) {
		if(oldId == 14 || oldId == 28 || oldId == 42 || oldId == 56) {
			switch(color) {
				case "red":
					return 57;
				case "yellow":
					return 71;
				case "green":
					return 85;
				case "blue":
					return 99;
			}
		} else if(oldId == 70 || oldId == 84 || oldId == 98 || oldId == 112) {
			switch(color) {
			case "red":
				return 113;
			case "yellow":
				return 114;
			case "green":
				return 115;
			case "blue":
				return 116;
		}
		}
		return oldId;
	}
}
