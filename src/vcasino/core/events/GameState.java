package vcasino.core.events;

import java.util.ArrayList;

import vcasino.core.Card;
import vcasino.core.Player;

public class GameState {
	private Card topDiscard;
	private ArrayList<Card> table;
	private ArrayList<Player> players;
	private Player winner;
	
	public GameState() {
		topDiscard = new Card();
		table = new ArrayList<>();
		players = new ArrayList<>();
	}
	
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
		for(int i =0; i < players.size(); i++) {
			if(players.get(i).equals(player)) {
				return players.get(i);
			}
		}	
		return null;
	}
	public Player getNextPlayer(Player previousPlayer) {
		for(int i =0; i < players.size(); i++) {
			if(players.get(i).equals(previousPlayer)) {
				int playerId = (i+1)%players.size();
				return players.get(playerId);
			}
		}
		return null;
	}

}
