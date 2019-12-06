package vcasino.core.games;

import java.util.List;

import vcasino.core.Card;
import vcasino.core.Deck;
import vcasino.core.GameState;
import vcasino.core.Player;
import vcasino.core.Ruleset;
import vcasino.core.events.GameEvent;
import vcasino.core.exceptions.RulesException;

public class GoFishRuleset implements Ruleset {

	private static int handSize=5;
	private String arg1;
	private boolean continueTurn=false;
	
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
		return "gofish";
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
	public GameEvent passCard(Player from, Player to, int handIndex) throws RulesException {
		to.addCard(from.getHand().remove(handIndex));
		return null;
	}

	@Override
	public void drawCard(GameState state, Player forPlayer) throws RulesException {
		forPlayer.addCard(state.getDeck().drawCard());
	}

	@Override
	public void dealHand(GameState state, Player forPlayer) throws RulesException {
		forPlayer.addCard(state.getDeck().drawCard());
	}

	@Override
	public GameEvent playCard(GameState state, Player player, int handIndex) throws RulesException {
		GameEvent event=null;
		try {
			Player target = state.getPlayer(arg1);
			Card card = player.getHand().get(handIndex);
			
			if(!target.isActive())
				throw new RulesException("Player", "The player you chose has left!", player);

			continueTurn = false;
			
			for(int i=0;i<target.getHand().size();i++) {
				Card c = target.getHand().get(i);
				if(c.getRank() == card.getRank()) {
					passCard(target, player, i);
					i=0; //safeguard the loop
					continueTurn=true;
				}
			}
			
			if(!continueTurn) {
				drawCard(state, player);
				event = new GameEvent("Go Fish!", 0);
				event.to(player);
			}
			
			//check for match 4
			int count;
			for(int i=0;i<player.getHand().size();i++) {
				Card comp = player.getHand().get(i);
				count=0;
				for(int j=i;j<player.getHand().size();j++) {
					Card c = player.getHand().get(j);
					if(comp.matchRank(c)) {
						count++;
					}
				}
				
				if(count == 4) {
					//matched! remove them...
					for(int j=0;j<player.getHand().size();j++) {
						if(player.getHand().get(j).matchRank(comp)) {
							state.getDeck().discard(player.getHand().remove(j));
							j--;
						}
					}
					//TODO: no way to notify the player from here...
				}
			}
			
			if(player.getHand().size()==0)
				drawCard(state, player);
			if(target.getHand().size()==0)
				drawCard(state, target);
		} catch (Exception e) {
			
		} finally {
			
		}
		return event;
	}

	@Override
	public GameEvent fold(GameState state, Player player) throws RulesException {
		
		return null;
	}

	@Override
	public GameEvent beginMatch(GameState state) throws RulesException {
		shuffleDeck(state);
		
		for(int i=0;i<handSize;i++) {
			for(Player p : state.getPlayers()) {
				drawCard(state, p);
			}
		}
		
		state.getPlayers().get(0).setTurn(true);
		return new GameEvent("Begin Match!", 1);
	}

	@Override
	public Player advanceTurn(Player current, List<Player> players) {
		Player nextPlayer=current;
		
		if(continueTurn) {
			continueTurn = false;
		} else {
			int i = players.indexOf(current)+1;
			
			current.setTurn(false);
			while(!players.get(i >= players.size() ? 0 : i).isActive()) {
				i = (i>= players.size() ? 0 : i+1);
			}
			nextPlayer = players.get(i >= players.size() ? 0 : i);
			nextPlayer.setTurn(true);
		}
		return nextPlayer;
	}

	@Override
	public boolean gameOver(GameState state) {
		for(Player p : state.getPlayers()) {
			if(p.getHand().size() == 0)
				return true;
		}
		return false;
	}

	@Override
	public Player declareWinner(GameState gameState) {
		for(Player p : gameState.getPlayers()) {
			if(p.getHand().size() == 0)
				return p;
		}
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
		state.getDeck().shuffle();
	}

	@Override
	public void postHandReset(GameState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}
}
