	
	var urlParams = new URLSearchParams(window.location.search);
	
	$.getJSON('../../inbound.json', function(data) {
		var container = document.getElementById('discard');
		var img = document.createElement('img');
			img.src = 'https://raw.githubusercontent.com/TheHumanGiraffe/AdvancedJavaAppsProject/master/cards/' + data.topDiscard.CardID + '.jpg'; // img[i] refers to the current URL.
			img.title = data.topDiscard.Name; 
			container.appendChild(img);
		$.each(data.table, function (key, val) {
			var container = document.getElementById('table');
			var img = document.createElement('img');
				img.src = 'https://raw.githubusercontent.com/TheHumanGiraffe/AdvancedJavaAppsProject/master/cards/' + val.CardID + '.jpg'; // img[i] refers to the current URL.
				img.title = val.Name;
				container.appendChild(img);
		});
		$.each(data.player1.hand, function (key, val) {
			var container = document.getElementById('player1');
			var img = document.createElement('img');
				img.src = 'https://raw.githubusercontent.com/TheHumanGiraffe/AdvancedJavaAppsProject/master/cards/' + val.CardID + '.jpg'; // img[i] refers to the current URL.
				img.title = val.Name;
				container.appendChild(img);
		});
		$.each(data.player2.hand, function (key, val) {
			var container = document.getElementById('player2');
			var img = document.createElement('img');
				img.src = 'https://raw.githubusercontent.com/TheHumanGiraffe/AdvancedJavaAppsProject/master/cards/' + val.CardID + '.jpg'; // img[i] refers to the current URL.
				img.title = val.Name;
				container.appendChild(img);
		});
		$.each(data.player3.hand, function (key, val) {
			var container = document.getElementById('player3');
			var img = document.createElement('img');
				img.src = 'https://raw.githubusercontent.com/TheHumanGiraffe/AdvancedJavaAppsProject/master/cards/' + val.CardID + '.jpg'; // img[i] refers to the current URL.
				img.title = val.Name;
				container.appendChild(img);
		});
		$.each(data.player4.hand, function (key, val) {
			var container = document.getElementById('player4');
			var img = document.createElement('img');
				img.src = 'https://raw.githubusercontent.com/TheHumanGiraffe/AdvancedJavaAppsProject/master/cards/' + val.CardID + '.jpg'; // img[i] refers to the current URL.
				img.title = val.Name;
				container.appendChild(img);
		});
	});
	
	
	var jsonText ='{ "name":"Mathias" , "chips":5 }'
	//var json = JSON.parse(jsonText); 
	
	//
	if(urlParams.has('roomNumber')){
		var roomNumber = urlParams.get('roomNumber')
	}else{
		var roomNumber = "TheWrongRoom"
	}
	if(urlParams.has('game')){
		var game = urlParams.get('game');
	}else{
		var game = 'loser'
	}
	
	var webSocket = new WebSocket("ws://localhost:8080/WebSocketServerExample/websocketendpoint/"+game+"/"+roomNumber);
	//var webSocket = new WebSocket("ws://ec2-3-89-73-209.compute-1.amazonaws.com:8080/webSocketTest4/websocketendpoint");
	
    var echoText = document.getElementById("echoText");
    echoText.value = "";
    
    var message = document.getElementById("message");
    webSocket.onopen = function(message){ wsOpen(message);};
    webSocket.onmessage = function(message){ wsGetMessage(message);};
    webSocket.onclose = function(message){ wsClose(message);};
    webSocket.onerror = function(message){ wsError(message);};
    
    function wsOpen(message){
        echoText.value += "Connecting ... \n";
    }
    function wsSendMessage(){
       // webSocket.send(message.value);
       // webSocket.send(json);
        webSocket.send(jsonText);
        echoText.value += "Message sent to the server : " + message.value + "\n";
        message.value = "";
    }
    function wsCloseConnection(){
        webSocket.close();
    }
    function wsGetMessage(message){
        echoText.value += "Message received from the server : " + message.data + "\n";
    }
    function wsClose(message){
        echoText.value += "Disconnect ... \n";
    }

    function wsError(message){
        echoText.value += "Error ... \n";
    }