
package vcasino.core;

import vcasino.core.exceptions.RulesException;

public interface Ruleset {

	//general actions
	String getRules();
	void newDeck();
	int getInitialHandCount();
	
	//Player-level actions
	void passCard(Player from, Player to) throws RulesException;
	Card drawCard(Player forPlayer) throws RulesException;
	Card dealCard(Player toPlayer);
	void placeCard(Player player);
	void fold(Player player);
	
	//Table-level actions
	void beginMatch();
	void advanceTurn();
	boolean gameOver();
	Player declareWinner();
	void placeBet(Player player);
	void showHand(Player player);
	void reshuffleDeck();
}
