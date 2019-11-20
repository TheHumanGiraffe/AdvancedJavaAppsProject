package vcasino.core;

import java.util.ArrayList;

public class Player {

	private String name;
	private int chips;
	private boolean isTurn, isActive;
	private ArrayList<Card> hand;
	private String id;
	private int activeBet;
	
	public Player(String name, int chips, String id) {
		this.name = name;
		this.chips = chips;
		this.id = id;
		isTurn = false;
		isActive = true;
		hand = new ArrayList<>();
		setActiveBet(0);
		
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

	public boolean isActive() {
		return isActive;
	}
	
	public void activate() {
		isActive = true;
	}
	
	public void deactivate() {
		isActive = false;
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public int getActiveBet() {
		return activeBet;
	}

	public void setActiveBet(int activeBet) {
		this.activeBet = activeBet;
	}

	public String getId() {
		return this.id;
	}
	
	
	public void addCard(Card cardToAdd) {
		this.hand.add(cardToAdd);
	}
	
	public void emptyHand() {
		this.hand = new ArrayList<>();
	}
	
	public void addChips(int numberOfChips) {
		this.chips = this.chips + numberOfChips;
	}
	
	@Override
	public boolean equals(Object o) { 
		   // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [Player]" also returns false */
        if (!(o instanceof Player)) { 
            return false; 
        }
        Player p = (Player) o;
        //Check if player ID matches 
        return id.equals(p.id);
	}
	
	@Override
	public String toString() {
		return "Player [name=" + name + ", chips=" + chips + ", isTurn=" + isTurn + ", hand=" + hand + ", id=" + id
				+ "]";
	}
}
