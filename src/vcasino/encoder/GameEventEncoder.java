package vcasino.encoder;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import vcasino.core.events.GameEvent;

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
	public String encode(GameEvent object) throws EncodeException {
		// TODO Auto-generated method stub
		return null;
	}

}
