/**
 * 
 */

window.onload = function() {


	var img = new Image();
	img.onload = function() {
		// Start resizing
	};

	img.src = "/images/logo.png";
};

function teste() {

	window.console('Teste');
};

function idExtintorDefault(){
	
	var idExt = document.getElementById('extintorId').value;
		
	if(idExt == null ||  idExt == "" || idExt < 0){
		document.getElementById('extintorId').value = '0';
	}
	else{

	}
	
};