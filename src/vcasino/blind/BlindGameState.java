package vcasino.blind;

import java.util.ArrayList;

import vcasino.core.Card;
import vcasino.core.Player;
import vcasino.core.events.GameState;

public class BlindGameState {
 	
	private Card topDiscard;
	private ArrayList<Card> table;
	private ArrayList<BlindPlayer> blindPlayers;
	private Player currentPlayer;
	private Player winner;
	private int  potSize;



	public BlindGameState(GameState gameState, Player sight) {
		blindPlayers = new ArrayList<BlindPlayer>();

		
		  this.topDiscard = gameState.getTopDiscard(); 
		  this.table = gameState.getTable(); 
		  this.winner = gameState.getWinner();	
		  this.currentPlayer = sight;
		  this.potSize = gameState.getPotSize();
		
		for(Player p : gameState.getPlayers()) {
			if(!p.equals(currentPlayer)) {
				blindPlayers.add(new BlindPlayer(p));
			}
		}
	}


	
}
