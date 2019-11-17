package vcasino.core.games;

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
		Deck deck = new Deck(); //either UnoDeck or we need to be able to set lots of properties like values, suits/colors, where to find the images
		deck.discardTop();
		return deck;
	}

	public void setCurrentPlayer(Player player) {
		
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
	public GameEvent drawCard(Player forPlayer) throws RulesException {
		return new GenericGameEvent(forPlayer);
	}

	@Override
	public GameEvent dealCard(Player toPlayer) {
		return new GenericGameEvent(toPlayer);
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
	public GameEvent beginMatch() {
		return new GenericGameEvent(null);
	}

	@Override
	public boolean gameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Player declareWinner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameEvent placeBet(Player player) {
		return new GenericGameEvent(player);
	}

	@Override
	public GameEvent showHand(Player player) {
		return new GenericGameEvent(player);
	}

	@Override
	public GameEvent shuffleDeck() {
		return new GenericGameEvent(null);
	}

	@Override
	public Player advanceTurn(Player[] players) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameEvent dealHand(Player forPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
