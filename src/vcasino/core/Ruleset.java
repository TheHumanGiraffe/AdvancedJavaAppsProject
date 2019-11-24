
package vcasino.core;

import java.util.List;

import vcasino.core.events.GameEvent;
import vcasino.core.exceptions.RulesException;

public interface Ruleset {
	public static String CARDS="poker";
	
	//general actions
	String getDescription();
	String getName();
	Deck newDeck();
	int getInitialHandCount();
	
	//Player-level actions
	GameEvent passCard(Player from, Player to) throws RulesException;
	void drawCard(GameState state, Player forPlayer) throws RulesException;
	void dealHand(GameState state, Player forPlayer) throws RulesException;
	GameEvent playCard(GameState state, Player player, int handIndex) throws RulesException;
	GameEvent fold(GameState state, Player player) throws RulesException;
	
	//Table-level actions
	GameEvent beginMatch(GameState state) throws RulesException;
	Player advanceTurn(Player current, List<Player> players);
	boolean gameOver(GameState state);
	Player declareWinner(GameState gameState);
	void placeBet(GameState state, Player player, int betSize) throws RulesException;
	GameEvent showHand(Player player);
	void shuffleDeck(GameState state);
	void postHandReset(GameState state);
	
	//Ugly hacks
	void setArg1(String arg1);
}
