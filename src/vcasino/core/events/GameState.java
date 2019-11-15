package vcasino.core.events;

import java.util.ArrayList;

import vcasino.core.Card;
import vcasino.core.Player;

public class GameState {
	private Card topDiscard;
	private ArrayList<Card> table;
	private ArrayList<Player> players;
	
	public Card getTopDiscard() {
		return topDiscard;
	}
	public void setTopDiscard(Card topDiscard) {
		this.topDiscard = topDiscard;
	}
	public ArrayList<Card> getTable() {
		return table;
	}
	public void setTable(ArrayList<Card> table) {
		this.table = table;
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

}
