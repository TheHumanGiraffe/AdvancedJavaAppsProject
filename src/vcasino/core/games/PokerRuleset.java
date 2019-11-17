package vcasino.core.games;

import vcasino.core.Deck;
import vcasino.core.Player;
import vcasino.core.Ruleset;
import vcasino.core.events.GameEvent;
import vcasino.core.exceptions.RulesException;

public class PokerRuleset implements Ruleset {

	private Deck deck;
	private static int handSize= 5;
	@Override
	public String getDescription() {
		return "Simple 5 card stud ruleset";
	}

	@Override
	public String getName() {
		return "Poker Rules";
	}

	@Override
	public Deck newDeck() {
		this.deck = new Deck();
		return this.deck;
	}

	@Override
	public void setCurrentPlayer(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getInitialHandCount() {
		return handSize;
	}

	@Override
	public GameEvent passCard(Player from, Player to) throws RulesException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameEvent drawCard(Player forPlayer) throws RulesException {
		forPlayer.addCard(deck.drawCard());
		return null;
	}

	@Override
	public GameEvent dealHand(Player forPlayer){
		for(int i =0; i < handSize; i ++) {
			forPlayer.addCard(deck.drawCard());
		}
		return null;
	}
	@Override
	public GameEvent dealCard(Player toPlayer) {
		
		return null;
	}

	@Override
	public GameEvent placeCard(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameEvent fold(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameEvent beginMatch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player advanceTurn(Player[] players) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameEvent showHand(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameEvent shuffleDeck() {
		deck.shuffle();
		return null;
	}

}
