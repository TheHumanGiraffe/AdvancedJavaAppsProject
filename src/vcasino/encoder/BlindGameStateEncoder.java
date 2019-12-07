package vcasino.encoder;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import vcasino.blind.BlindGameState;

/*
 * Encodes opponents into json format for front end
 */
public class BlindGameStateEncoder implements Encoder.Text<BlindGameState>{

	private EndpointConfig config;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		config = arg0;
	}

	@Override
	public String encode(BlindGameState gameState) throws EncodeException {
		Gson gson = new Gson();
		
		String str = gson.toJson(gameState);
		System.out.println("Generated state:" + str);
		return "{\"gamestate\": " + str+" }";
	}

}
