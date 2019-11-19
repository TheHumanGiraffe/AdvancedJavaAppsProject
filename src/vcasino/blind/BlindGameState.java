package vcasino.blind;

import vcasino.core.GameState;
import vcasino.core.Player;

public class BlindGameState extends GameState {
	private int visible;
	
	public BlindGameState(GameState gameState, Player sight) {
		super(gameState.getRules());
		int count=0;
		
		//manipulate the game state to hide the other players info
		for(Player p : gameState.getPlayers()) {
			if(p == sight) {
				addPlayer(sight);
				visible = count;
			} else {
				addPlayer(new BlindPlayer(p));
			}
			count++;
		}
		this.deck = new BlindDeck();
		this.deck.discard(gameState.getTopDiscard());
		this.table = gameState.getTable();
		this.currentPlayer = gameState.getCurrentPlayer();
		this.winner = gameState.getWinner();
	}
	
}
