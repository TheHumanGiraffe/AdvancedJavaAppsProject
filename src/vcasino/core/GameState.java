package vcasino.core;

import java.util.ArrayList;

public class GameState {
	protected Ruleset rules;
	protected Deck deck;
	protected ArrayList<Card> table;
	protected ArrayList<Player> players;
	protected Player currentPlayer, winner;
	protected int potSize;
	
	public GameState(Ruleset rules) {
		this.rules = rules;
		deck = this.rules.newDeck();
		table = new ArrayList<>();
		players = new ArrayList<>();
		potSize = 0;
	}
	
	public Ruleset getRules() {
		return rules;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public Card getTopDiscard() {
		return deck.getDiscard(0);
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
	public void addPlayer(Player newPlayer) {
		players.add(newPlayer);
	}
	
	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public void setPlayer(Player player) {
		for(int i =0; i < players.size(); i++) {
			if(players.get(i).equals(player)) {
				players.set(i, player);
			}
		}
	}
	
	public Player getPlayer(Player player) {
		if(players.contains(player)) 
			return player;
		return null;
	}
	
	public Player getPlayer(int i) {
		return players.get(i);
	}
	
	public int countPlayers() {
		return players.size();
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public int getPotSize() {
		return potSize;
	}

	public void setPotSize(int potSize) {
		this.potSize = potSize;
	}
}
