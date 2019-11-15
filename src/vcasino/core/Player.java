package vcasino.core;

import java.util.ArrayList;

public class Player {

	private String name;
	private int chips;
	private boolean isTurn;
	private ArrayList<Card> hand;
	
	public Player() {
		name = "TestName";
		chips = 100;
		isTurn = false;
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getChips() {
		return chips;
	}

	public void setChips(int chips) {
		this.chips = chips;
	}

	public boolean isTurn() {
		return isTurn;
	}

	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public int getId() {
		return 0;
	}
	
	public String toJSONString() {
		return "{ player:{}}";
	}
}
