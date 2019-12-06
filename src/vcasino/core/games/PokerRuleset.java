package vcasino.core.games;

import java.util.ArrayList;
import java.util.List;

import vcasino.core.Card;
import vcasino.core.Deck;
import vcasino.core.GameState;
import vcasino.core.PokerHand;
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
		return "poker";
	}

	@Override
	public String getHumanName() {
		return "poker";
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
	public GameEvent passCard(Player from, Player to, int index) throws RulesException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void drawCard(GameState state, Player forPlayer) throws RulesException {
			forPlayer.addCard(state.getDeck().drawCard());
	}

	@Override
	public void dealHand(GameState state, Player forPlayer) throws RulesException {
		for(int i=0;i<handSize;i++) {
			for(Player p : state.getPlayers()) {
				dealCard(state, p);
			}
		}
	}
	
	@Override
	public GameEvent playCard(GameState state, Player player, int handIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameEvent fold(GameState state, Player player) {
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
		ArrayList<Player> playingSet = new ArrayList<>();
		
		for(Player p : state.getPlayers()) {
			if(p.isActive()) {
				playingSet.add(p);
			}
		}
		//If all but one fold. hand is over. Game/Round is over;
		return (playingSet.size()==1);
	}

	@Override
	public Player declareWinner(GameState gameState) {
		ArrayList<Player> players = gameState.getPlayers();
		Player winner = null;
		int currentHigh = -1;
		for(Player player : players) {
			
			//Skip the folded players
			if(!player.isActive()) {
				continue;
			}
			if(makeBestHand(player.getHand()) == currentHigh) {
				PokerHand h1 = new PokerHand(player.getHand());
				PokerHand h2 = new PokerHand(winner.getHand());
				if(PokerHand.compare(h1, h2) == 0) {
					winner = player;
				}
				continue;
			}
			if(makeBestHand(player.getHand()) > currentHigh) {
				winner = player;
				currentHigh = makeBestHand(player.getHand());
			}
			
		}
		
		winner.addChips(gameState.getPotSize());
		
		postHandReset(gameState);
		return winner;
	}

	@Override
	public void placeBet(GameState state, Player player, int betSize) throws RulesException {
				Integer activeBet = player.getActiveBet();
				//If active bet is null tmp set it to 0
				activeBet = activeBet == null ? 0 : activeBet;
				
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
						Integer opponetActiveBet = opponent.getActiveBet();
						opponetActiveBet = opponetActiveBet == null ? 0 : opponetActiveBet;
						if(activeBet + betSize < opponetActiveBet) {
							throw new RulesException("Under Bet", "Player Did not meet oppenent Call", player);
						}
					}	
					player.setActiveBet(activeBet + betSize);
					player.setChips(player.getChips() - betSize);
					state.setPotSize(state.getPotSize() + betSize);
					
				}
				if(isBettingOver(player, state)) {
					declareWinner(state);
				}
	}
	
	public boolean isBettingOver(Player player, GameState state) {
		//Check if current player has placed a bet
		Integer activeBet = player.getActiveBet();
		if(activeBet == null) {
			return false;
		}
		
		for(Player opponent : state.getPlayers()) {
			//Skip current player
			if(opponent.equals(player)) {
				continue;
			}
			//Skip opponents that have folded
			if(!opponent.isActive()) {
				continue;
			}
			//Check if opponent has placed a bet
			if(opponent.getActiveBet() == null) {
				return false;
			}
			//Check if all the bets are equal across the table;
			if(activeBet != opponent.getActiveBet()) {
				return false;
			}		
		}	
		//If everyone has placed a bet and they are equal betting is over
		return true;		
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
		PokerHand hand = new PokerHand(handAsList);
		hand.makeBestHand();
		return hand.getHandRank();
	}
	
	private void dealCard(GameState state, Player forPlayer) {
		forPlayer.addCard(state.getDeck().drawCard());
	}

	
	@Override
	public void postHandReset(GameState state) {
		//Get new Deck and shuffle it
		state.newDeck();
		shuffleDeck(state);	
		state.setPotSize(0);
		
		for(Player p : state.getPlayers()) {
			//Reset player betting and give new hand
			p.setActiveBet(null);
			p.emptyHand();
			try {
				this.dealHand(state, p);
			} catch (RulesException e) {e.printStackTrace();}
			p.activate();
		}		
	}
	
	@Override
	public void setArg1(String arg1) {
		//this.arg1 = arg1;
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
