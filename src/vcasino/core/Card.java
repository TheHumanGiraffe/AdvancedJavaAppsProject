package vcasino.core;

public class Card {

	private int cardID=0; //Java always does, but it's just the C coder in me...
	private String name;
	private int rank;
	private String suit;
	
	
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
        return this.getRank() == card.getRank();
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
