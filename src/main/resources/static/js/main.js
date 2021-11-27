/** ============================================================================ */
/*Medidas da tela */

const constWidth = window.innerWidth || document.documentElement.clientWidth ||
	document.body.clientWidth;
const constHeight = window.innerHeight || document.documentElement.clientHeight ||
	document.body.clientHeight;

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
function idUsuarioDefault() {

	var idUser = document.getElementById('matricula').value;
	var senha = document.getElementById('senha').value;

	if (idUser == null || idUser == "" || idUser < 0) {
		document.getElementById('matricula').value = '999';
	}

	if (senha == null || senha == "" || senha < 0) {
		document.getElementById('senha').value = '0';
	}

	else {

	}

};
/** ============================================================================ */
function idExtintorDefault() {

	var idExt = document.getElementById('extintorPesquisaId').value;

	if (idExt == null || idExt == "" || idExt < 0) {
		document.getElementById('extintorPesquisaId').value = '0';
	}
	else {

	}

};
/** ============================================================================ */
function idVistoriaDefault() {

	var idVist = document.getElementById('vistoriaId').value;
	var periodo = document.getElementById('periodo').value;
	var fkExtintor = document.getElementById('fkExtintor').value;

	if (idVist == null || idVist == "" || idVist < 0) {
		document.getElementById('vistoriaId').value = '0';
	}

	if (periodo == null || periodo == "" || periodo < 0) {
		document.getElementById('periodo').value = '12';
	}

	if (fkExtintor == null || fkExtintor == "" || fkExtintor < 0) {
		document.getElementById('fkExtintor').value = '0';
	}

};
/** ============================================================================ */
function onLoadLogin() {


	var target = document.getElementById("usuario");

	if (target.innerText === 'Felipe Campos') {
		document.getElementById("caixaNovo").style.visibility = 'visible';
		console.log('admin');
	} else {
		document.getElementById("caixaNovo").style.visibility = 'hidden';
		console.log('comum');
	}

};

/** ============================================================================ */
function onLoadExtintor() {


	var target = document.getElementById("usuario");

	if (target.innerText === 'Felipe Campos') {
		document.getElementById("caixaAdmin").style.visibility = 'visible';
		console.log('admin');
	} else {
		document.getElementById("caixaAdmin").style.visibility = 'hidden';
		console.log('comum');
	}

};

/** ============================================================================ */
function completaFKExtintor(objeto) {

	document.getElementById('fkExtintor').value = objeto.value;

}
/** ============================================================================ */
function pesquisasVistoria() {

	var divPesquisa = document.getElementById("divPesquisa");

	var estaOculto = window.getComputedStyle(document.getElementById("divPesquisa")).visibility === "hidden";


	if (!estaOculto) {
		divPesquisa.style.visibility = 'hidden';
	}
	else { divPesquisa.style.visibility = 'visible'; }

}
/** ============================================================================ */
function editarUsuario() {

	var idUsuario = document.getElementById('matriculaUsuario').value;
	var senhaUsuario = document.getElementById('senhaUsuario').value;
	var nomeUsuario = document.getElementById('nomeUsuario').value;

	if (nomeUsuario === null || nomeUsuario === "" ) {
		document.getElementById('nomeUsuario').value = 'nomeGenerico';
	}
	
	if (senhaUsuario === null || senhaUsuario === "" || senhaUsuario < 0) {
		document.getElementById('senhaUsuario').value = '0';
	}
	
	if (idUsuario === null || idUsuario === "" || idUsuario < 0) {
		document.getElementById('matriculaUsuario').value = '0';
	}

}