package vcasino.core.events;

import vcasino.core.Player;

public class BasicGameEvent extends GameEvent {

	public BasicGameEvent(Player by) {
		super(by);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toJSONString() {
		return "{\n \"BasicGameEvent\": {\n  owner: "+eventOwner.getId()+"\n }\n}";
	}

	

}
