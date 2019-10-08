package vcasino.core.events;

import vcasino.core.Player;

public abstract class GameEvent {

	protected Player eventOwner;
	
	public GameEvent(Player by) {
		eventOwner = by;
	}
	
	public abstract String toJSONString();
}
