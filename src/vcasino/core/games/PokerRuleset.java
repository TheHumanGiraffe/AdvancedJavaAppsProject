package vcasino.core.games;

import java.util.ArrayList;
import java.util.List;

import vcasino.core.Card;
import vcasino.core.Deck;
import vcasino.core.GameState;
import vcasino.core.Hand;
import vcasino.core.Player;
import vcasino.core.Ruleset;
import vcasino.core.events.GameEvent;
import vcasino.core.exceptions.RulesException;

public class PokerRuleset implements Ruleset {

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
		return new Deck();
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
	public void drawCard(GameState state, Player forPlayer) throws RulesException {
		if(forPlayer == state.getCurrentPlayer())
			forPlayer.addCard(state.getDeck().drawCard());
		throw new RulesException("Turn", "Not their turn!", forPlayer);
	}

	@Override
	public void dealHand(GameState state, Player forPlayer) throws RulesException {
		for(int i=0;i<handSize;i++) {
			forPlayer.addCard(state.getDeck().drawCard());
		}
	}
	
	@Override
	public GameEvent placeCard(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameEvent fold(Player player) {
		player.deactivate();		
		return null;
	}

	@Override
	public GameEvent beginMatch(GameState state) throws RulesException {
		Deck deck;
		
		shuffleDeck(state);
		
		deck = state.getDeck();
		
		for(int i=0;i<handSize;i++) {
			for(Player p : state.getPlayers()) {
				dealCard(state, p);
			}
		}
		deck.discardTop();
		state.getPlayers().get(0).setTurn(true);
		
		return null;
	}

	@Override
	public Player advanceTurn(Player current, List<Player> players) {
		Player nextPlayer;
		int i = players.indexOf(current)+1;
		
		current.setTurn(false);
		while(!players.get(i >= players.size() ? 0 : i).isActive()) {
			i = (i>= players.size() ? 0 : i+1);
		}
		nextPlayer = players.get(i >= players.size() ? 0 : i);
		nextPlayer.setTurn(true);
		return nextPlayer;
	}

	@Override
	public boolean gameOver(GameState state) {
		int numberOfFold = 0;
		for(Player p : state.getPlayers()) {
			if(!p.isActive()) {
				numberOfFold++;
			}
			//If all but one fold. hand is over. Game/Round is over;
			if(numberOfFold == state.getPlayers().size()-1) {
				return true;
			}		
		}
		return false;
	}

	@Override
	public Player declareWinner(GameState gameState) {
		ArrayList<Player> players = gameState.getPlayers();
		Player winner = null;
		int currentHigh = -1;
		for(Player player : players) {
			if(makeBestHand(player.getHand()) == currentHigh) {
				Hand h1 = new Hand(player.getHand());
				Hand h2 = new Hand(winner.getHand());
				if(Hand.compare(h1, h2) == 0) {
					winner = player;
				}
				
			}
			if(makeBestHand(player.getHand()) > currentHigh) {
				winner = player;
				currentHigh = makeBestHand(player.getHand());
			}
			
		}
		
		winner.addChips(gameState.getPotSize());
		return winner;
	}

	@Override
	public void placeBet(GameState state, Player player, int betSize) throws RulesException {
		//Check if player has enough chips to bet
				if(player.getChips() - betSize < 0) {
					throw new RulesException("Over Bet", "Not enough Chips", player);
				}else {
					for(Player opponent : state.getPlayers()) {
						//Skip current player
						if(opponent.equals(player)) {
							continue;
						}
						//Check if players bet is greater than or = to oppents current bet 
						if(player.getActiveBet() + betSize < opponent.getActiveBet()) {
							throw new RulesException("Under Bet", "Player Did not meet oppenent Call", player);
						}
					}	
					player.setActiveBet(player.getActiveBet() + betSize);
					player.setChips(player.getChips() - betSize);
					state.setPotSize(state.getPotSize() + betSize);
					
				}
	}

	@Override
	public GameEvent showHand(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void shuffleDeck(GameState state) {
		state.getDeck().shuffle();
	}

	private int makeBestHand(ArrayList<Card> handAsList) {
		Hand hand = new Hand(handAsList);
		hand.makeBestHand();
		return hand.getHandRank();
	}
	
	private void dealCard(GameState state, Player forPlayer) {
		forPlayer.addCard(state.getDeck().drawCard());
	}

	
	@Override
	public void postHandReset(GameState state) {
		//Get new Deck and shuffle it
		this.newDeck();
		this.shuffleDeck(state);	
		state.setPotSize(0);
		
		for(Player p : state.getPlayers()) {
			//Reset player betting and give new hand
			p.setActiveBet(0);
			p.emptyHand();
			try {
				this.dealHand(state, p);
			} catch (RulesException e) {e.printStackTrace();}
			p.activate();
		}		
	}
	
	public enum HandNameAndRank {
	    HIGH_CARD("High Card", 0), PAIR("Pair", 1), TWO_PAIR("Two Pair", 2),
	    THREE_OF_A_KIND("Three of a kind", 3), STRIGHT("Stright", 4), FLUSH("Flush", 5),
	    FULL_HOUSE("Full House", 6), FOUR_OF_A_KIND("Four of a kind", 7),
	    STRIGHT_FLUSH("Stright Flush", 8), ROYAL_FLUSH("Royal Flush", 9);
	
	    private String handName;
	    private int handRank;
	
	
	    HandNameAndRank(String name, int rank){
	        this.handName = name;
	        this.handRank = rank;
	    }
	    public String handName(){
	        return handName;
	    }
	
	    public  int handRank(){
	        return handRank;
	    }
	}
}
