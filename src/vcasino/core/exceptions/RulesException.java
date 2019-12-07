package vcasino.core.exceptions;

import vcasino.core.Player;

/**
 * Gets thrown whenever a rule of gameplay gets broken
 */
public class RulesException extends Exception {
	

	private static final long serialVersionUID = 1L;

	private String rule, description;
	private Player byPlayer;
	
	public RulesException(String rule, String description, Player p) {
		this.rule = rule;
		this.description = description;
		this.byPlayer = p;
	}
	
	public String getRule() {
		return rule;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Player getPlayer() {
		return byPlayer;
	}
	
	@Override
	public String toString() {
		return "Rule \""+rule+"\" broken by "+byPlayer+": "+description;
	}
	
}
