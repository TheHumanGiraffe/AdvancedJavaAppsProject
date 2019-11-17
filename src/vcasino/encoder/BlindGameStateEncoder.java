package vcasino.encoder;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import vcasino.blind.BlindGameState;
import vcasino.core.Player;
import vcasino.core.events.GameState;


public class BlindGameStateEncoder implements Encoder.Text<GameState>{

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
	public String encode(GameState gameState) throws EncodeException {
		Gson gson = new Gson();
		Player ourPlayer = (Player)config.getUserProperties().get("player");
		
		BlindGameState blind = new BlindGameState(gameState, ourPlayer);
				
		return gson.toJson(blind);
	}

}
