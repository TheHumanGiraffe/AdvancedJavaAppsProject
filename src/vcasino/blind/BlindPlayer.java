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
public class BlindPlayer {
	private int chips;
	private int numberOfCards;
	private String name;

	public BlindPlayer(Player p){
		this.chips = p.getChips();
		this.numberOfCards = p.getHand().size();
		this.name = p.getName();
	}
}
