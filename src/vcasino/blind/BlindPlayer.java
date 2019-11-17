package vcasino.blind;

import vcasino.core.Player;

public class BlindPlayer {
	private int chips;
	private int numberOfCards;
	private String name;
	
	public BlindPlayer(Player p){
		this.chips = p.getChips();
		this.numberOfCards = p.getHand().size();
		this.name = p.getName();
	}
	public int getChips() {
		return chips;
	}
	public void setChips(int chips) {
		this.chips = chips;
	}
	public int getNumberOfCards() {
		return numberOfCards;
	}
	public void setNumberOfCards(int numberOfCards) {
		this.numberOfCards = numberOfCards;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
