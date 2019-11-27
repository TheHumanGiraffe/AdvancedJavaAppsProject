package vcasino.core.events;

import vcasino.core.exceptions.RulesException;

public class RulesViolationEvent extends GameEvent {
	private String rule;
	private String desc;
	
	public RulesViolationEvent(RulesException e) {
		super(e.getDescription(), 0);
		
		rule = e.getRule();
		desc = e.getDescription();
	}

}
