package vcasino.encoder;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import vcasino.core.GameState;

/*
 * Encodes entire GameState into json format
 */
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
	public String encode(GameState gameState) throws EncodeException {
		
		Gson gson = new Gson();
		
		/*Player ourPlayer = (Player)config.getUserProperties().get("player");
		
		BlindGameState blind = new BlindGameState(gameState, ourPlayer);
		String str = gson.toJson(blind);
		System.out.println("Generated state:" + str);*/
		
		return gson.toJson(gameState);
	}

}
