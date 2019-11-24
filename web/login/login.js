
var urlParams = new URLSearchParams(window.location.search);
var gameState = null;
var cardURL = 'https://raw.githubusercontent.com/TheHumanGiraffe/AdvancedJavaAppsProject/master/web/gameBoard/cards/';
var newUser = false;

if(urlParams.has('roomNumber')){
	var roomNumber = urlParams.get('roomNumber')
}else{
	var roomNumber = "login"
}
if(urlParams.has('game')){
	var game = urlParams.get('game');
}else{
	var game = 'login'
}

if (getCookie("Username") == ""){id="test"}
else{id=getCookie("Username")}
var webSocket = new WebSocket("ws://localhost:8080/AdvancedJavaAppsProject/vcasino/"+id+"/"+game+"/"+roomNumber);
//var webSocket = new WebSocket("ws://ec2-3-89-73-209.compute-1.amazonaws.com:8080/webSocketTest4/websocketendpoint");


webSocket.onmessage = function(message){ wsGetMessage(message);};


function login(){
	newUser = false;
	var username = document.getElementById("existingUsername").value;
	var password = document.getElementById("existingPwd").value;
	var jsonText = '{"action":"login", "arg0": "'+username+'", "arg1":"'+password+'"}';
	console.log(jsonText);
	wsSendMessage(jsonText);
}

function createUser(){
	newUser = true;
	var username = document.getElementById("newUsername").value;
	var password = document.getElementById("newPwd").value;
	var confirmPassword = document.getElementById("newConfirmPwd").value;
	if (confirmPassword != password){alert("passwords don't match"); return;}
	var jsonText = '{"action":"newUser", "arg0": "'+username+'", "arg1": "'+confirmPassword+'", "arg2":"'+password+'"}';
	console.log(jsonText);
	wsSendMessage(jsonText);
}

function wsOpen(message){
	// echoText.value += "Connecting ... \n";
}
function wsSendMessage(jsonText){
	webSocket.send(jsonText);
}
function wsCloseConnection(){
	webSocket.close();
}
function wsGetMessage(message){
	var json = JSON.parse(message.data);
//	console.log(json.message.text)
	if (json.message.text == "loginError"){
		alert("fix your login");
		document.cookie="username=";
	}
	else{
		document.cookie = "username="+json.message.text;
	}
	redirect()
}

function redirect(){
	if (getCookie("username") != ""){
		window.location.href = document.location.href.split("/login/")[0]+"/gameBoard/gameBoard.html?game=browse"
	}
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