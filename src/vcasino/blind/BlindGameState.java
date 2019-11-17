package vcasino.blind;

import vcasino.core.Player;
import vcasino.core.events.GameState;

public class BlindGameState extends GameState {
	
	
	public BlindGameState(GameState gameState, Player sight) {
		super();
		
		//manipulate the game state to hide the other players info
		for(Player p : gameState.getPlayers()) {
			if(p == sight) {
				gameState.addPlayer(sight);
			} else {
				gameState.addPlayer(new BlindPlayer(p));
			}
		}
		
		this.topDiscard = gameState.getTopDiscard();
		this.table = gameState.getTable();
		this.currentPlayer = gameState.getCurrentPlayer();
		this.winner = gameState.getWinner();
	}
	
}
