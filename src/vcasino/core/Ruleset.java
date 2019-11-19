
package vcasino.core;

import java.util.List;

import vcasino.core.events.GameEvent;
import vcasino.core.events.GameState;
import vcasino.core.exceptions.RulesException;

public interface Ruleset {

	//general actions
	String getDescription();
	String getName();
	Deck newDeck();
	int getInitialHandCount();
	
	//Player-level actions
	GameEvent passCard(Player from, Player to) throws RulesException;
	void drawCard(GameState state, Player forPlayer) throws RulesException;
	void dealHand(GameState state, Player forPlayer) throws RulesException;
	GameEvent placeCard(Player player);
	GameEvent fold(Player player);
	
	//Table-level actions
	GameEvent beginMatch(GameState state) throws RulesException;
	Player advanceTurn(Player current, List<Player> players);
	boolean gameOver(GameState state);
	Player declareWinner(GameState gameState);
	void placeBet(GameState state, Player player, int betSize) throws RulesException;
	GameEvent showHand(Player player);
	void shuffleDeck(GameState state);
	void postHandReset(GameState state);
}
