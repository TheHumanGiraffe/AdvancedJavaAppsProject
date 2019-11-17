package vcasino.blind;


import java.util.ArrayList;

import vcasino.core.Card;
import vcasino.core.Player;
import vcasino.core.events.GameState;

public class BlindGameState {

	private Card topDiscard;
	private ArrayList<Card> table;
	private ArrayList<Player> otherPlayers;
	private ArrayList<BlindPlayer> blindPlayers;
	private Player currentPlayer;
	private Player winner;
	
	
	public BlindGameState(GameState gameState) {
		blindPlayers = new ArrayList<BlindPlayer>();
		
		this.topDiscard = gameState.getTopDiscard();
		this.table = gameState.getTable();
		this.otherPlayers = gameState.getPlayers();
		this.winner = gameState.getWinner();
		
	}
	
	//manipulate the game state to hide the other players info
	public BlindGameState getBlindGameState(Player player) {
		currentPlayer = player;
		for(Player p : otherPlayers) {
			if(!p.equals(currentPlayer)) {
				blindPlayers.add(new BlindPlayer(p));
			}
		}
		return this;
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

	public ArrayList<BlindPlayer> getBlindPlayers() {
		return blindPlayers;
	}

	public void setBlindPlayers(ArrayList<BlindPlayer> blindPlayers) {
		this.blindPlayers = blindPlayers;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	public void setOtherPlayers() {
		this.otherPlayers = null;
	}
	
}
