package vcasino.core.games;

import java.util.List;

import vcasino.core.*;
import vcasino.core.events.GenericGameEvent;
import vcasino.core.events.GameEvent;
import vcasino.core.exceptions.RulesException;

public class UnoRuleset implements Ruleset {
	
	@Override
	public String getDescription() {
		//Copied from http://wonkavator.com/uno/unorules.html
		return "Each player is dealt 7 cards with the remaining ones placed face down to form a DRAW pile. The top card of the DRAW pile is turned over to begin a DISCARD pile.\r\n" + 
				"The first player has to match the card in the DISCARD pile either by number, color or word. For example, if the card is a red 7, player must throw down a red card or any color 7. Or the player can throw down a Wild Card. If the player doesn't have anything to match, he must pick a card from the DRAW pile. If he can play what is drawn, great. Otherwise play moves to the next person.\r\n" + 
				"When you have one card left, you must yell \"UNO\" (meaning one). Failure to do this results in you having to pick two cards from the DRAW pile. That is, of course if you get caught by the other players.\r\n" + 
				"Once a player has no cards left, the hand is over. Points are scored (see scoring section) and you start over again. That's UNO in a nutshell.";
	}

	@Override
	public String getName() {
		return "Uno";
	}
	
	@Override
	public Deck newDeck() {
		Deck deck = new UnoDeck();
		return deck;
	}
	
	@Override
	public int getInitialHandCount() {
		return 7;
	}

	@Override
	public GameEvent passCard(Player from, Player to) throws RulesException {
		return new GenericGameEvent(from);
	}

	@Override
	public void drawCard(GameState state, Player forPlayer) throws RulesException {
		if(forPlayer == state.getCurrentPlayer())
			forPlayer.addCard(state.getDeck().drawCard());
		throw new RulesException("Turn", "Not their turn!", forPlayer);
	}

	@Override
	public void dealHand(GameState state, Player forPlayer) throws RulesException {
		for(int i=0;i<getInitialHandCount();i++) {
			forPlayer.addCard(state.getDeck().drawCard());
		}
	}

	@Override
	public GameEvent placeCard(Player player) {
		return new GenericGameEvent(player);
	}

	@Override
	public GameEvent fold(Player player) {
		return new GenericGameEvent(player);
	}

	@Override
	public GameEvent beginMatch(GameState state) throws RulesException {
		Deck deck;
		
		shuffleDeck(state);
		
		deck = state.getDeck();
		
		for(int i=0;i<getInitialHandCount();i++) {
			for(Player p : state.getPlayers()) {
				drawCard(state, p);
			}
		}
		deck.discardTop();
		
		return null;
	}
	
	@Override
	public GameEvent showHand(Player player) {
		return new GenericGameEvent(player);
	}

	@Override
	public Player advanceTurn(Player current, List<Player> players) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player declareWinner(GameState gameState) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void shuffleDeck(GameState state) {
		state.getDeck().shuffle();
	}

	@Override
	public boolean gameOver(GameState state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void placeBet(GameState state, Player player, int betSize) throws RulesException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandReset(GameState state) {
		// TODO Auto-generated method stub
		
	}

	

}
