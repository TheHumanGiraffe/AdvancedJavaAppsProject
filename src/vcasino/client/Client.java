package vcasino.client;

import java.net.Socket;
import java.net.URI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import vcasino.servlet.VCasinoServerEndpoint;


public class Client extends Application {
	Socket sock;
	String server = "ec2-3-89-73-209.compute-1.amazonaws.com:8080"; //change me as necessary
	VCasinoServerEndpoint endpoint;
	
	class ClientThread implements Runnable {
		
		TextArea output;
		
		ClientThread(TextArea a) {
			output = a;
		}
		
		public void run() {
			try {
			
				Platform.runLater(() -> {
					output.appendText("Connecting to "+server+"...\n");
				});
			
			
				endpoint = new VCasinoServerEndpoint(new URI("ws://"+server+"/"));
				endpoint.connect();
				
				if(endpoint.isConnected()) {
					Platform.runLater(() -> {
						output.appendText("Connected!\n");
					});
				}
			
			} catch (Exception e) {
				Platform.runLater(() -> {
					output.appendText("Error: "+e.getLocalizedMessage()+"\n");
				});
			}
		}
	}
	public static void main(String [] args) {

		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FlowPane flowPane = new FlowPane();
		TextArea chat = new TextArea();
		TextField chatInput = new TextField();
		
		Button d = new Button("Draw");
		Button ds = new Button("Discard");
		Button p = new Button("Place");
		Button c = new Button("Chat");
		
		flowPane.getChildren().addAll(d,ds,p, chat, chatInput, c);
		
		c.setOnAction(e -> {endpoint.sendRequest("chat", chatInput.getText()); chatInput.setText("");});
		d.setOnAction(e -> {endpoint.sendRequest("draw", "");});
		ds.setOnAction(e -> {endpoint.sendRequest("discard", "todo-cardid");});
		p.setOnAction(e -> {endpoint.sendRequest("place", "todo-cardid;todo-ontowhere");});
		
		chat.setEditable(false);
		
		ClientThread thread = new ClientThread(chat);
		
		Scene scene = new Scene(flowPane);
		
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
		
		(new Thread(thread)).start();
	}
}
