
package vcasino.core;

import vcasino.core.events.GameEvent;
import vcasino.core.exceptions.RulesException;

public interface Ruleset {

	//general actions
	String getDescription();
	String getName();
	Deck newDeck();
	void setCurrentPlayer(Player player);
	int getInitialHandCount();
	
	//Player-level actions
	GameEvent passCard(Player from, Player to) throws RulesException;
	GameEvent drawCard(Player forPlayer) throws RulesException;
	GameEvent dealHand(Player forPlayer);
	GameEvent dealCard(Player toPlayer);
	GameEvent placeCard(Player player);
	GameEvent fold(Player player);
	
	//Table-level actions
	GameEvent beginMatch();
	Player advanceTurn(Player[] players);
	boolean gameOver();
	Player declareWinner();
	GameEvent placeBet(Player player);
	GameEvent showHand(Player player);
	GameEvent shuffleDeck();
}
