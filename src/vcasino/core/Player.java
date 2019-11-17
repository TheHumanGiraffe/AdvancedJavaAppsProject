package vcasino.core;

import java.util.ArrayList;

public class Player {

	private String name;
	private int chips;
	private boolean isTurn;
	private ArrayList<Card> hand;
	private String id;
	
	public Player(String name, int chips, String id) {
		this.name = name;
		this.chips = chips;
		isTurn = false;
		this.id = id;
		hand = new ArrayList<>();
		
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

	public String getId() {
		return this.id;
	}
	
	
	public void addCard(Card cardToAdd) {
		this.hand.add(cardToAdd);
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

	public String toJSONString() {
		return "{ player:{}}";
	}
}
