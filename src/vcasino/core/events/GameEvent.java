package vcasino.core.events;

import vcasino.core.Player;

public class GameEvent {

	private Player targetPlayer;
	private String action;
	private int priority;
	
	public GameEvent(String a, int p) {
		action = a;
		priority = p;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public void to(Player t) {
		targetPlayer = t;
	}
	
	public Player getTarget() {
		return targetPlayer;
	}
	
	@Override
	public String toString() {
		return "GameEvent [action=" + action + ", priority=" + priority + "]";
	}
	
}
