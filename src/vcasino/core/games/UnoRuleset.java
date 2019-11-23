package vcasino.core.games;

import java.util.List;

import vcasino.core.*;
import vcasino.core.events.GenericGameEvent;
import vcasino.core.events.GameEvent;
import vcasino.core.exceptions.RulesException;

public class UnoRuleset implements Ruleset {
	
	private boolean clockwise = true;
	private boolean skip = false;
	private String color;
	
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
		return "uno";
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
	public GameEvent playCard(GameState state, Player player, int handIndex) throws RulesException {
		
		Card discard = state.getTopDiscard();
		
		Card play = player.getHand().get(handIndex);
		
		if(!discard.matchSuit(play) && !discard.matchRank(play)) {
			throw new RulesException("Card", "You must play a card that matches the color or the value of the top card", player);
		}
		
		player.getHand().remove(handIndex);
		
		//now, was it something fun?
		switch(play.getRank()) {
			case 100: //skip turn!
				state.getDeck().discard(play);
				skip = true;
				break;
			case 110:
				state.getDeck().discard(play);
				state.reverseOrder();
				clockwise = !clockwise;
				break;
			case 120:
				state.getDeck().discard(play);
				//ha-ha!
				for(Player p : state.getPlayers()) {
					if(p != player ) {
						if(state.getDeck().size() < 2) {
							createCleanDeck(state);
							state.getDeck().shuffle();
						}
						p.addCard(state.getDeck().drawCard());
						p.addCard(state.getDeck().drawCard());
					}
				}
				break;
			case 300:
				//FIXME: welp.
				//Card c = new Card(play.getCardID(), action.arg1);
				//c.setRank(UnoDeck.rankMap[play.getCardID()]);
				//state.getDeck().discard(c);
				break;
			case 400:
				for(Player p : state.getPlayers()) {
					if(p != player ) {
						if(state.getDeck().size() < 4) {
							createCleanDeck(state);
							state.getDeck().shuffle();
						}
						p.addCard(state.getDeck().drawCard());
						p.addCard(state.getDeck().drawCard());
						p.addCard(state.getDeck().drawCard());
						p.addCard(state.getDeck().drawCard());
					}
				}
				//FIXME: umm...
				//Card c = new Card(play.getCardID(), action.arg1);
				//c.setRank(UnoDeck.rankMap[play.getCardID()]);
				//state.getDeck().discard(c);
				break;
			default:
				state.getDeck().discard(play);
				break;
		}
		
		return new GenericGameEvent(player);
	}

	@Override
	public GameEvent fold(GameState state, Player player) {
		return new GenericGameEvent(player);
	}

	@Override
	public GameEvent beginMatch(GameState state) throws RulesException {
		Deck deck;
		
		shuffleDeck(state);
		
		deck = state.getDeck();
		
		for(int i=0;i<getInitialHandCount();i++) {
			for(Player p : state.getPlayers()) {
				dealCard(state, p);
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
		Player nextPlayer=current;
		
		current.setTurn(false);
		
		do {
			if(clockwise) {
				int i = players.indexOf(current)+1;
				
				while(!players.get(i >= players.size() ? 0 : i).isActive()) {
					i = (i>= players.size() ? 0 : i+1);
				}
				nextPlayer = players.get(i >= players.size() ? 0 : i);
				nextPlayer.setTurn(true);
			} else {
				int i = players.indexOf(current)-1;
				
				if( i < 0 )
					i = players.size()-1;
				
				while(!players.get(i).isActive()) {
					i = (i>0  ? i-1 : players.size()-1);
				}
				nextPlayer = players.get(i);
				nextPlayer.setTurn(true);
			}
		} while(skip && !(skip = !skip)); //this is evil.
		
		return nextPlayer;
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

	private void dealCard(GameState state, Player forPlayer) {
		forPlayer.addCard(state.getDeck().drawCard());
	}

	/**
	 * So the reason for this function is that the logic above actually injects 
	 * "choose color" cards with the suit (color) specified instead of "any". This
	 * causes a problem when shuffling the discard pile back into the deck. Hence,
	 * this function creates a new deck and removes the cards that the players have 
	 * in their hand as well as the top of the old discard pile.
	 * 
	 * @param state
	 */
	private void createCleanDeck(GameState state) {
		Card discard = state.getDeck().getDiscard(0);
		
		state.newDeck();
		
		Deck deck = state.getDeck();
		
		for(Player p : state.getPlayers()) {
			deck.getCards().removeAll(p.getHand());
		}
		
		deck.getCards().remove(discard);
		
		deck.discard(discard);
	}
}
