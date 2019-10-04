package vcasino.core.games;

import vcasino.core.Card;
import vcasino.core.Player;
import vcasino.core.Ruleset;
import vcasino.core.exceptions.RulesException;

public class UnoRuleset implements Ruleset {

	@Override
	public String getRules() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void newDeck() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getInitialHandCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void passCard(Player from, Player to) throws RulesException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Card drawCard(Player forPlayer) throws RulesException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card dealCard(Player toPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void placeCard(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fold(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beginMatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void advanceTurn() {
		// TODO Auto-generated method stub
		
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
	public void placeBet(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showHand(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reshuffleDeck() {
		// TODO Auto-generated method stub
		
	}

}
