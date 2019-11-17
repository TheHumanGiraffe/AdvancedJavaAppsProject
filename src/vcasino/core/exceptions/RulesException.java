package vcasino.core.exceptions;

import vcasino.core.Player;

public class RulesException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String rule, description;
	private Player byPlayer;
	
	public RulesException(String rule, String description, Player p) {
		this.rule = rule;
		this.description = description;
		this.byPlayer = p;
	}
	
	@Override
	public String toString() {
		return "Broken rule \""+rule+"\": "+description +":"+ byPlayer;
	}
}
