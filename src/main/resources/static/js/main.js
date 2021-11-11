/** ============================================================================ */
/*Medidas da tela */

const constWidth = window.innerWidth || document.documentElement.clientWidth ||
	document.body.clientWidth;
const constHeight = window.innerHeight || document.documentElement.clientHeight ||
	document.body.clientHeight;
var i = 0;

console.log(constWidth, constHeight);

/** ============================================================================ */
/* Executado ao carregar a página index*/
function onLoadIndex() {

	//Pega as dimensões da imagem
	var logo = document.getElementById("iconeInicial");

	//Calcula o espaço que deve ficar a cada lado da div que contém a imagem do logo
	var sobraEsquerda = (constWidth - logo.width) / 2;
	//Calcula o espaço que deve ficar acima e abaixo da div que contém a imagem do logo
	var sobraTopo = (constHeight - logo.height) / 2;

	//Seta o espaço à esquerda da div
	document.getElementById("containerLogo").style.left = sobraEsquerda + "px";
	document.getElementById("containerLogo").style.top = sobraTopo + "px";

};


/** ============================================================================ */
function idExtintorDefault() {

	var idExt = document.getElementById('extintorId').value;

	if (idExt == null || idExt == "" || idExt < 0) {
		document.getElementById('extintorId').value = '0';
	}
	else {

	}

};

/** ============================================================================ */
function onLoadLogin() {

i++;
alert(document.getElementById("usuario").innerText + " i: " + i);

var target = document.getElementById("usuario");

	// cria uma nova instância de observador
	var observer = new MutationObserver(function(mutations) {
		mutations.forEach(function(mutation) {
			console.log(mutation.type);
			
			target.innerText = target.innerText + "_mudado";
		});
	});
	
	// configuração do observador:
	var config = { attributes: true, childList: true, characterData: true };
	
	// passar o nó alvo, bem como as opções de observação
	observer.observe(target, config);
};