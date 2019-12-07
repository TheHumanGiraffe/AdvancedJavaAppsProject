package vcasino.encoder;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import vcasino.core.events.GameEvent;

/*
 * Encodes GameEvents into json format
 */
public class GameEventEncoder implements Encoder.Text<GameEvent> {

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(GameEvent event) throws EncodeException {
		return "{\"event\": {\"action\": \""+event.getAction()+"\", \"priority\": "+event.getPriority()+"} }";
	}

}
