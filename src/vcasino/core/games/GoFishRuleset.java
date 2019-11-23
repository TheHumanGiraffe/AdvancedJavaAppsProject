package vcasino.core.games;

import java.util.List;

import vcasino.core.Deck;
import vcasino.core.GameState;
import vcasino.core.Player;
import vcasino.core.Ruleset;
import vcasino.core.events.GameEvent;
import vcasino.core.exceptions.RulesException;

public class GoFishRuleset implements Ruleset {

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "poker";
	}

	@Override
	public Deck newDeck() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getInitialHandCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GameEvent passCard(Player from, Player to) throws RulesException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void drawCard(GameState state, Player forPlayer) throws RulesException {
		// TODO Auto-generated method stub

	}

	@Override
	public void dealHand(GameState state, Player forPlayer) throws RulesException {
		// TODO Auto-generated method stub

	}

	@Override
	public GameEvent playCard(GameState state, Player player, int handIndex) throws RulesException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameEvent fold(GameState state, Player player) throws RulesException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameEvent beginMatch(GameState state) throws RulesException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player advanceTurn(Player current, List<Player> players) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean gameOver(GameState state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Player declareWinner(GameState gameState) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void placeBet(GameState state, Player player, int betSize) throws RulesException {
		// TODO Auto-generated method stub

	}

	@Override
	public GameEvent showHand(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void shuffleDeck(GameState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandReset(GameState state) {
		// TODO Auto-generated method stub

	}

}
