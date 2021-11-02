/** ============================================================================ */
/*Medidas da tela */

const width  = window.innerWidth || document.documentElement.clientWidth || 
document.body.clientWidth;
const height = window.innerHeight|| document.documentElement.clientHeight|| 
document.body.clientHeight;

console.log(width, height);

/** ============================================================================ */
/* Executado ao carregar a página*/
window.onload = function() {
	
	var escalaH = height/955;
	var escalaW = width/1920;
	var icones = document.getElementsByClassName("clsIcon");
	
	for(i = 0; i < icones.length; i++) {
    	icones[i].style.width = "1000px";
  	}
	
	//capturar a div do cabeçalho e a redimensionar
	var divCabecalho = document.getElementById("containerHeader");
	divCabecalho.style.width = (escalaW * 1780) +  "px";

}
/** ============================================================================ */
/* */
function teste2(){

	var divCabecalho = document.getElementById("containerHeader");
	
	divCabecalho.style.width = "2000px";

	
};
/**/
/** ============================================================================ */
function idExtintorDefault(){
	
	var idExt = document.getElementById('extintorId').value;
		
	if(idExt == null ||  idExt == "" || idExt < 0){
		document.getElementById('extintorId').value = '0';
	}
	else{

	}
	
};


/** ============================================================================ */
/**/
