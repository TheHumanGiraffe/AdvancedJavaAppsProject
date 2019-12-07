
var urlParams = new URLSearchParams(window.location.search);
var gameState = null;
var cardURL = 'https://raw.githubusercontent.com/TheHumanGiraffe/AdvancedJavaAppsProject/master/web/gameBoard/cards/';
var handCard = -1;
var reconInteval;

$(document).ready(function() {
	$("#echoText").val('');
	
	setInterval(checkBrowse, 5000);
});

if(urlParams.has('roomNumber')){
	var roomNumber = urlParams.get('roomNumber')
}else{
	var roomNumber = 'browse'
}
if(urlParams.has('game')){
	var game = urlParams.get('game')
}else{
	var game = 'browse'
}

if (getCookie("Username") == ""){id="test"}
else{id=getCookie("Username")}
var webSocket = new WebSocket("ws://localhost:8080/AdvancedJavaAppsProject/vcasino/"+id+"/"+game+"/"+roomNumber);
//var webSocket = new WebSocket("ws://ec2-3-89-73-209.compute-1.amazonaws.com:8080/webSocketTest4/websocketendpoint");

// var echoText = document.getElementById("echoText");
// echoText.value = "";

// var message = document.getElementById("message");
webSocket.onopen = function(message){ wsOpen(message);};
webSocket.onmessage = function(message){ wsGetMessage(message);};
webSocket.onclose = function(message){ wsClose(message);};
webSocket.onerror = function(message){ wsError(message);};

function placeBet(){
	var betInput = document.getElementById("betValue").value
	if(!isNaN(betInput)){
		var jsonText ='{ "action":"bet", "arg0":' + betInput + '}'
	}

	wsSendMessage(jsonText);
}

function resetBet(){
	document.getElementById("betValue").value = 0;
}

function add10(){
	var oldBetValue = parseInt(document.getElementById('betValue').value, 10);
	var newBetValue = oldBetValue + 10; 
	document.getElementById('betValue').value = newBetValue;
}

function add20(){
	var oldBetValue = parseInt(document.getElementById('betValue').value, 10);
	var newBetValue = oldBetValue + 20; 
	document.getElementById('betValue').value = newBetValue;
}

function add50(){
	var oldBetValue = parseInt(document.getElementById('betValue').value, 10);
	var newBetValue = oldBetValue + 50;
	document.getElementById('betValue').value = newBetValue;
}

function add100(){
	var oldBetValue = parseInt(document.getElementById('betValue').value, 10);
	var newBetValue = oldBetValue + 100;
	document.getElementById('betValue').value = newBetValue;
}

function fold(){
	var jsonText = '{"action":"fold"}';
	wsSendMessage(jsonText);
}

function drawCard(){
	var jsonText ='{ "action":"draw"}'
	wsSendMessage(jsonText);
}

function playCard(card, arg1){
	var jsonText ='{ "action":"play", "arg0": "'+card+'", "arg1": "'+arg1+'"}'
	wsSendMessage(jsonText);
}

function updatePlayerChooser(){
	var chooser = document.getElementById("playerchooser");
	var html = ""
	
	for(var i=0;i<gameState.players.length;i++) {
		if(i == gameState.visible)
			continue;
		html = html + '<input type="radio" name="player" value="'+gameState.players[i].name+'" onclick="closeDialog(); playCard(handCard, \''+gameState.players[i].name+'\');"> '+gameState.players[i].name+'<br/>';
	}
	
	chooser.innerHTML = html;
}

function closeDialog() {
	if ($('#colorchooser').is(':visible'))
		$('#colorchooser').toggle();
	
	if ($('#playerchooser').is(':visible'))
		$('#playerchooser').toggle();
}

function renderGamestate(gamestate) {
	
	//resets all divs
	var player1div = document.getElementById('player1Div');
	var player2div = document.getElementById('player2Div');
	var player3div = document.getElementById('player3Div');
	var player4div = document.getElementById('player4Div');

	var player1info = document.getElementById('player1info');
	var player2info = document.getElementById('player2info');
	var player3info = document.getElementById('player3info');
	var player4info = document.getElementById('player4info');
	
	if(gamestate==null)
		return;
	
	player1div.innerHTML = "";
	player2div.innerHTML = "";
	player3div.innerHTML = "";
	player4div.innerHTML = "";
	
	player1info.innerHTML = "";
	player2info.innerHTML = "";
	player3info.innerHTML = "";
	player4info.innerHTML = "";
	
	gameState = gamestate; //save it for lates
	
	var currentPlayer = gamestate.players[gamestate.visible];
	console.dir(gamestate);
	console.dir(currentPlayer);
	console.dir(currentPlayer.hand);
	
	if(currentPlayer.hand.length>0) {
		console.dir(currentPlayer.hand[0]);
		console.log(currentPlayer.hand[0].cardID);
		updatePlayerChooser();
	} else {
		echoText.value += "Waiting for players...\n";
	}

	var playerCount = gamestate.players.length;
	var players = gamestate.players;
	console.log("<img src='" + cardURL + gamestate.cards + '/' + gamestate.deck.discards[0].cardID + ".jpg' />")
	document.getElementById("discard").innerHTML = "<img class='deck' src='" + cardURL + gamestate.cards + '/' + gamestate.deck.discards[0].cardID + ".jpg' />";
	document.getElementById("deck").innerHTML = "<img class='deck' src='" + cardURL + gamestate.cards + '/' + "0.jpg' />";
	document.getElementById("pot").innerHTML="<h2>Pot Size: " + gamestate.potSize + "</h2>";
	
	players.forEach(function(player){
		if(player.isTurn){
			document.getElementById("currentPlayer").innerHTML = "<h2>Current Player: " + player.name + "</h2>";
		}
	});
	
	var offset=0;
 	var img_id=0;
	currentPlayer.hand.forEach(function(card) {
		var imgContainer = document.createElement("span");
		imgContainer.className = "imgWrap";
		
		var div = document.createElement("cardh");
		div.classList.add('cardh');
		div.style.left = "-"+offset+"px";
		div.id = ""+img_id+"Div";
			
		var img = document.createElement("img");
		img.src = cardURL + gamestate.cards + '/' + card.cardID + ".jpg";
		img.className = "p1";
		img.id = img_id;
		if(card.suit == "any")
			img.addEventListener('click', function() {handCard = img.id; $('#colorchooser').toggle();});
		else if(game == "gofish")
			img.addEventListener('click', function() {handCard = img.id; $('#playerchooser').toggle();});
		else img.addEventListener('click', function() {playCard(img.id, "");});
		
		/*if(img_id < currentPlayer.hand.length-1) {
			img.addEventListener("mouseover", function(){document.getElementById(""+(img_id+1)+"Div").style.left=0;});
			img.addEventListener("mouseout", function(){document.getElementById(""+(img_id+1)+"Div").style.left=-(img_id+1)*80;});
		}*/
		img_id++;
		imgContainer.appendChild(img);
		
		offset += 80;
		
		var span = document.createElement("span");
		span.innerHTML = card.name + " of " + card.suit;
		span.className = "tooltip";
		imgContainer.appendChild(span);
		
		player1div.appendChild(imgContainer);
	})
	
	var text = document.createElement("h3");
	text.className = "p1Stats";
	text.innerHTML = "Player: " + currentPlayer.name + "  Chips: " + currentPlayer.chips;
	player1info.appendChild(text);

	players.splice(gamestate.visible, 1);

	var iterator = 2;
	players.forEach(function(player) {
		console.log(player);

		var workingDiv = document.getElementById('player' + iterator + 'Div');
		var workingInfo = document.getElementById('player' + iterator + 'info');
		
		var top=0;
		
		player.hand.forEach(function(card){
			var div = document.createElement("div");
			if(iterator%2==0) {
				div.classList.add('cardv');
				div.style.top = "-"+top+"px";
			} else {
				div.classList.add('cardh');
				div.style.left = "-"+top+"px";
			}
			var img = document.createElement("img");
			img.src = cardURL + gamestate.cards + '/' + card.cardID + ".jpg";
			img.className = "p" + iterator;
			div.appendChild(img);
			workingDiv.appendChild(div);
			top = top + 80;
		});
		
		if(iterator == 3){
			var text = document.createElement("h3");
			text.className = "p3Stats";
			text.innerHTML = "Player: " + player.name + "  Chips: " + player.chips;
			workingInfo.appendChild(text);
		} else {
			var playerName = document.createElement("h3");
			playerName.innerHTML = "Player: " + player.name;
			
			var playerChips = document.createElement("h3");
			playerChips.innerHTML = "Chips: " + player.chips;
			
			workingInfo.appendChild(playerName);
			workingInfo.appendChild(playerChips);
		}
		
		iterator++;
	});
}

function renderGameList(gameList) {
	var html='<h1>Games Select</h1>\n<table class="browser">';
	var count=0;
	gameList.forEach(function(game) {
		
		if(count%4==0)
			html += "<tr>";
		
		html += '<td class="browser"><a href="gameBoard.html?game=' + game.type + '&roomNumber=' + game.room+'"><h3>'+game.type[0].toUpperCase() + game.type.slice(1).toLowerCase()+'</h3></a><img class="browse" src="ui/'+game.players+'players.png"/></td>'
		
		count++;
		
		if(count%4==0)
			html += "</tr>";
	});
	
	document.getElementById('browser').innerHTML = html+"</table>";
	
	//document.getElementById('discard').appendChild(parentDiv);
	if (!$('#browser').is(':visible'))
		$('#browser').toggle();
}

function handleEvent(event) {
	if(event.priority==0)
		echoText.value += "EVENT: "+event.action+"\n";
	if(event.priority==1)
		echoText.value += "BIG EVENT: "+event.action+"\n";
}

function wsOpen(message){
	// echoText.value += "Connecting ... \n";
}
function wsSendMessage(jsonText){
	// webSocket.send(message.value);
	// webSocket.send(json);
	webSocket.send(jsonText);
	// echoText.value += "Message sent to the server : " + message.value + "\n";
	// message.value = "";
}
function wsCloseConnection(){
	webSocket.close();
}
function wsGetMessage(message){

	console.dir(message);
	console.dir(message.data);

	var json = JSON.parse(message.data);

	console.dir(json);

	if(json["message"] != null) {
		
		//alert(json.message.text);
		echoText.value += "ALERT: "+json.message.text+"\n";
	} else if(json["chat"] != null) {
		echoText.value += json.chat.Player+": "+json.chat.text+"\n";
	} else if(json["event"] != null) {
		handleEvent(json.event);
	} else if (json["games"] != null) {
		renderGameList(json.games);
	} 
	else {
		renderGamestate(json.gamestate);
	}
}
function wsClose(message){
	echoText.value += "Disconnect ... \n";
	console.dir(message);
	
	reconInteval = setInterval(reconnect, 5000);
}

function wsError(message){
	echoText.value += "Error from server\n";
	console.dir(message);
}

function checkBrowse() {
	if(roomNumber == 'browse') {
		var jsonText ='{ "action":"browse", "arg0": "'+game+'"}'
		wsSendMessage(jsonText);
	}
}

function reconnect() {
	echoText.value += "Reconnecting ... \n";
	webSocket = new WebSocket("ws://localhost:8080/AdvancedJavaAppsProject/vcasino/"+id+"/"+game+"/"+roomNumber);
	//var webSocket = new WebSocket("ws://ec2-3-89-73-209.compute-1.amazonaws.com:8080/webSocketTest4/websocketendpoint");

	// var echoText = document.getElementById("echoText");
	// echoText.value = "";

	// var message = document.getElementById("message");
	webSocket.onopen = function(message){ wsOpen(message);};
	webSocket.onmessage = function(message){ wsGetMessage(message);};
	webSocket.onclose = function(message){ wsClose(message);};
	webSocket.onerror = function(message){ wsError(message);};
	
	clearInterval(reconInteval);
}

function getCookie(cname) {
	  var name = cname + "=";
	  var ca = document.cookie.split(';');
	  for(var i = 0; i < ca.length; i++) {
	    var c = ca[i];
	    while (c.charAt(0) == ' ') {
	      c = c.substring(1);
	    }
	    if (c.indexOf(name) == 0) {
	      return c.substring(name.length, c.length);
	    }
	  }
	  return "";
	}