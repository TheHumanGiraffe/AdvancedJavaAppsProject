package vcasino.core.events;

import vcasino.core.Player;

public class GenericGameEvent extends GameEvent {

	public GenericGameEvent(Player by) {
		super(by);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toJSONString() {
		return "{\n \"BasicGameEvent\": {\n  owner: "+eventOwner.getId()+"\n }\n}";
	}

	

}
