package vcasino.core.events;

import vcasino.core.exceptions.RulesException;

public class RulesViolationEvent extends GameEvent {
	private String rule;
	private String desc;
	
	public RulesViolationEvent(RulesException e) {
		super(e.getPlayer());
		
		rule = e.getRule();
		desc = e.getDescription();
	}

}
