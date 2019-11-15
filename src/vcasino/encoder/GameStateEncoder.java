package vcasino.encoder;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import vcasino.core.events.GameState;

public class GameStateEncoder implements Encoder.Text<GameState>{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(GameState object) throws EncodeException {
		Gson gson = new Gson();
		return gson.toJson(object);
	}

}
