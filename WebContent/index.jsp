<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name='viewport' content='minimum-scale=1.0, initial-scale=1.0, width=device-width, maximum-scale=1.0, user-scalable=no'/>
<title>Chat-zti</title>
<link rel="stylesheet" type="text/css" href="content/main.css">
<script type="text/javascript" src="scripts/chatroom.js"></script>
<script type="text/javascript">
//var wsUri = "wss://chatjpa.mybluemix.net/chatroom";
var wsUri = "ws://localhost:9080/chat_jpa/chatroom";
var proxy = CreateProxy(wsUri);

document.addEventListener("DOMContentLoaded", function(event) {
	console.log(document.getElementById('login'));
	proxy.initiate({
		login: document.getElementById('login'),
		loginError: document.getElementById('loginError'),
		msg: document.getElementById('msg'),
		txtMsg: document.getElementById('txtMsg'),
		txtLogin: document.getElementById('txtLogin'),
		msgContainer: document.getElementById('msgContainer')
	});
});

</script>
</head>
<body>
<div id="wraper">
	<div id="login">
		<div id="infoLabel">Podaj nick</div>
		<div>
			<input id="txtLogin" maxlength="22" type="text" class="loginInput" onkeyup="proxy.login_keyup(event)" />
			<button type="button" class="msgButton" onclick="proxy.login()">Dołącz</button>
		</div>
	</div>
	<div id="loginError" class="errorMessage">Użytkownik o takim nicku jest już zalogowany</div>
	<!-- message container -->
	
	<div id="msg" style="display: none">
		<div id="msgContainer" style="overflow: auto;"></div>
		<div id="msgController">
			<textarea id="txtMsg" maxlength="255" onkeyup="proxy.sendMessage_keyup(event)"></textarea>
			<button class="msgButton" type="button" onclick="proxy.sendMessage()">Wyślij</button>	
			<button class="msgButton" type="button" onclick="proxy.logout()">Rozłącz</button>
		</div>
	</div>
</div>
</body>
</html>