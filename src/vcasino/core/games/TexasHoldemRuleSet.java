package vcasino.core.games;

import java.util.ArrayList;
import java.util.List;

import vcasino.core.Card;
import vcasino.core.Deck;
import vcasino.core.GameState;
import vcasino.core.Player;
import vcasino.core.PokerHand;
import vcasino.core.Ruleset;
import vcasino.core.events.GameEvent;
import vcasino.core.exceptions.RulesException;

public class TexasHoldemRuleSet implements Ruleset {
	
	private static int handSize= 2;
	private boolean isFlopDone = false;
	private boolean isTurnDone = false;
	private boolean isRiverDone = false;

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
	public String getHumanName() {
		return "holdem";
	}

	@Override
	public Deck newDeck() {
		return new Deck();
	}

	@Override
	public int getInitialHandCount() {
		// TODO Auto-generated method stub
		return 0;
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
				drawCard(state, p);
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
				drawCard(state, p);
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
			//Add Cards on table to players hand 
			player.getHand().addAll(gameState.getTable());
			
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

	private int makeBestHand(ArrayList<Card> handAsList) {
		PokerHand hand = new PokerHand(handAsList);
		hand.makeBestHand();
		return hand.getHandRank();
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
		//Advace Round when betting is over
		if(isBettingOver(player, state)) {
			if(!isFlopDone) {
				flop(state);
				return;
			}
			if(!isTurnDone) {
				turn(state);
				return;
			}
			if(!isRiverDone) {
				river(state);
				return;
			}
			//After the three rounds are done declar winner
			if(isFlopDone && isTurnDone && isRiverDone) {
				declareWinner(state);
			}
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
			if(opponent.getActiveBet() == -1) {
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

	@Override
	public void postHandReset(GameState state) {
		//Get new Deck and shuffle it
				state.newDeck();
				shuffleDeck(state);	
				state.setPotSize(0);
				
				for(Player p : state.getPlayers()) {
					//Reset player betting and active status
					
					p.setActiveBet(-1);
					p.emptyHand();		
					p.activate();
				}	
				try {
					//Deal out Fresh hands
					this.dealHand(state, null);
				} catch (RulesException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Clear Table
				state.setTable(new ArrayList<Card>());
				isFlopDone = false;
				isTurnDone = false;
				isRiverDone = false;
	}
	
	@Override
	public void setArg1(String arg1) {
		//this.arg1 = arg1;
	}
	
	/*
	 * Add 3 cards to community cards
	 */
	private void flop(GameState state) {
		
		//Burn card
		state.getDeck().drawCard();
		state.getTable().add(state.getDeck().drawCard());
		state.getTable().add(state.getDeck().drawCard());
		state.getTable().add(state.getDeck().drawCard());
		isFlopDone = true;
	}
	/*
	 * Add 1 card to community cards
	 */
	private void turn(GameState state) {
		//Burn card
		state.getDeck().drawCard();
		state.getTable().add(state.getDeck().drawCard());
		isTurnDone = true;
	}
	/*
	 * Add 1 cards to community cards
	 */
	private void river(GameState state) {
		//Burn card
		state.getDeck().drawCard();
		state.getTable().add(state.getDeck().drawCard());
		isRiverDone = true;
	}

}
