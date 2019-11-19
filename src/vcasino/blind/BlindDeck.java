package vcasino.blind;

import java.util.ArrayList;

import vcasino.core.Card;
import vcasino.core.Deck;

public class BlindDeck extends Deck {
	public BlindDeck() {
		super();
		
		cards = new ArrayList<>();
		cards.add(new Card());
	}
}
