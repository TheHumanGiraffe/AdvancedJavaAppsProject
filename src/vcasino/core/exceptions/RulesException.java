package vcasino.core.exceptions;

public class RulesException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String rule, description;
	
	public RulesException(String rule, String description) {
		this.rule = rule;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Broken rule \""+rule+"\": "+description;
	}
}
