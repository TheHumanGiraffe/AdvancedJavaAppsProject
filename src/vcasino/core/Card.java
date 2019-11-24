package vcasino.core;

public class Card {

	private int cardID=0; //Java always does, but it's just the C coder in me...
	private String name;
	private int rank;
	private String suit;
	
	public Card() {
	}
	
	public Card(int id, String suit) {
		cardID = id;
		this.suit = suit;
	}
	
	public int getCardID() {
		return cardID;
	}
	public void setCardID(int cardID) {
		this.cardID = cardID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getSuit() {
		return suit;
	}
	public void setSuit(String suit) {
		this.suit = suit;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return this.getRank() == card.getRank() && this.getSuit().equals(card.getSuit());
    }

    public boolean matchRank(Card b) {
    	return getRank() == b.getRank();
    }
    
    public boolean matchSuit(Card b) {
    	return getSuit().equals(b.getSuit());
    }
    
    public int compare(Card c1){
        if(this.getRank() == c1.getRank()){
            return 0;
        }
        else if(this.getRank() < c1.getRank()){
            return -1;
        }
        else{
            return 1;
        }
    }
}
