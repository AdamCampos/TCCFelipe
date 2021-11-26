package com.main.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.main.modelo.Extintor;
import com.main.modelo.ExtintorRepositoryImpl;
import com.main.modelo.Usuario;
import com.main.modelo.UsuarioRepositoryImpl;
import com.main.modelo.Vistoria;
import com.main.modelo.VistoriaRepositoryImpl;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class ControladorVistoria {

	@Autowired
	Vistoria vistoria;

	@Autowired
	VistoriaRepositoryImpl uri;

	@Autowired
	UsuarioRepositoryImpl usuarioImp;

	@Autowired
	ExtintorRepositoryImpl extintorImp;

	@Autowired
	VistoriaRepositoryImpl vistoriaImp;

	private final static ModelAndView MAV = new ModelAndView("vistoria");

	/******************************************************************************************************************/
	// Controla a requisição GET com caminho "vistoria".
	// Esta requisição normalemnte vem de fora da página. Para a página ser exibida,
	// o usuário deve estar logado.
	// Existem dois objetos principais passados da view para o controler: Vistoria e
	// HTTPSession
	@RequestMapping(value = { "/vistoria" }, method = { RequestMethod.GET })
	public ModelAndView controlePrincipalVistoria(@ModelAttribute("vistoria") Vistoria vistoria, HttpSession sessao) {

		// Recebe o usuário atual. O escopo é de sessão.
		Usuario u = (Usuario) sessao.getAttribute("usuario");

		// Se o usuário não estiver logado, retornará à página de login.
		if (u.getNome().contains("Anônimo") || u.getNome().contains("Anonimous")) {
			return new ModelAndView("login");
		} else {
			MAV.addObject("lista", this.carregaListaVistorias());
			this.carregaTodasListas();

			return MAV;
		}
	}

	/******************************************************************************************************************/
	// Trata da pesquisa de vistorias por meio do id do usuário.
	@RequestMapping(value = { "/buscaVistoria" }, method = { RequestMethod.GET }, params = "usuario")
	public ModelAndView buscaUsuario(@ModelAttribute("vistoria") Vistoria vistoria, HttpSession sessao,
			WebRequest requisicao) {

		// Recebe o usuário atual. O escopo é de sessão.
		Usuario u = (Usuario) sessao.getAttribute("usuario");

		// Se o usuário não estiver logado, retornará à página de login.
		if (u.getNome().contains("Anônimo") || u.getNome().contains("Anonimous")) {
			return new ModelAndView("login");
		}

		// Busca vistorias do usuário escolhido. O usuário é obtido do objeto
		// WebRequest.
		// O DAO resolve a pesquisa usando a matrícula do objeto usuário.
		ArrayList<Vistoria> listaVistoriasPorUsuario = (ArrayList<Vistoria>) uri
				.buscaPorUsuario(Integer.valueOf(requisicao.getParameter("usuario")));

		// O parâmetro "lista" é a lista principal mostrada na View.
		MAV.addObject("lista", listaVistoriasPorUsuario);
		this.carregaTodasListas();

		return MAV;
	}

	/******************************************************************************************************************/
	// Trata da pesquisa de vistorias por meio do id do extintor.
	@RequestMapping(value = { "/buscaVistoria" }, method = { RequestMethod.GET }, params = "extintor")
	public ModelAndView buscaExtintor(@ModelAttribute("vistoria") Vistoria vistoria, HttpSession sessao,
			WebRequest requisicao) {

		// Recebe o usuário atual. O escopo é de sessão.
		Usuario u = (Usuario) sessao.getAttribute("usuario");

		// Se o usuário não estiver logado, retornará à página de login.
		if (u.getNome().contains("Anônimo") || u.getNome().contains("Anonimous")) {
			return new ModelAndView("login");
		}

		// Busca vistorias do usuário escolhido
		ArrayList<Vistoria> listaVistoriasPorExtintor = (ArrayList<Vistoria>) uri
				.buscaPorExtintor(Integer.valueOf(requisicao.getParameter("extintor")));

		// O parâmetro "lista" é a lista principal mostrada na View.
		MAV.addObject("lista", listaVistoriasPorExtintor);

		this.carregaTodasListas();

		return MAV;
	}

	/******************************************************************************************************************/
	// Trata da pesquisa de vistorias por meio do id das vistorias.
	@RequestMapping(value = { "/buscaVistoria" }, method = { RequestMethod.GET }, params = "vistoria")
	public ModelAndView buscaVistoria(@ModelAttribute("vistoria") Vistoria vistoria, HttpSession sessao,
			WebRequest requisicao, ModelMap model) {

		// Recebe o usuário atual. O escopo é de sessão.
		Usuario u = (Usuario) sessao.getAttribute("usuario");

		// Se o usuário não estiver logado, retornará à página de login.
		if (u.getNome().contains("Anônimo") || u.getNome().contains("Anonimous")) {
			return new ModelAndView("login");
		}

		// Busca vistorias pelo Id
		ArrayList<Vistoria> listaVistoriasPorVistoria = (ArrayList<Vistoria>) uri
				.buscaPorVistoria(Integer.valueOf(requisicao.getParameter("vistoria")));

		// O parâmetro "lista" é a lista principal mostrada na View.
		MAV.addObject("lista", listaVistoriasPorVistoria);

		this.carregaTodasListas();

		return MAV;
	}

	/******************************************************************************************************************/
	// Deleta uma vistoria dado seu Id.
	@RequestMapping(value = { "/vistoria" }, method = { RequestMethod.POST }, params = "deletar")
	public ModelAndView deletar(@ModelAttribute("vistoria") Vistoria vistoria, HttpSession sessao) {

		// Recebe o usuário atual. O escopo é de sessão.
		Usuario u = (Usuario) sessao.getAttribute("usuario");

		// Se o usuário não estiver logado, retornará à página de login.
		if (u.getNome().contains("Anônimo") || u.getNome().contains("Anonimous")) {
			return new ModelAndView("login");
		}

		uri.delete(vistoria);
		// this.carregaTodasListas();

		return MAV;
	}

	/******************************************************************************************************************/
	// Atualiza uma vistoria dado seu Id.
	@RequestMapping(value = { "/vistoria" }, method = { RequestMethod.POST }, params = "atualizar", consumes = {
			"multipart/form-data" })
	public ModelAndView atualizar(@ModelAttribute("vistoria") Vistoria vistoria, HttpSession sessao,
			@RequestParam("fotoUpload") MultipartFile multiparte, MultipartHttpServletRequest request) {

		// Recebe o usuário atual. O escopo é de sessão.
		Usuario u = (Usuario) sessao.getAttribute("usuario");

		// Se o usuário não estiver logado, retornará à página de login.
		if (u.getNome().contains("Anônimo") || u.getNome().contains("Anonimous")) {
			return new ModelAndView("login");
		}

		Vistoria v = uri.findOne(String.valueOf(vistoria.getId()));
		v.setFkExtintor(vistoria.getFkExtintor());

		// Atualiza apenas os campos digitados.
		if (!vistoria.getLocalizacao().isEmpty() && !vistoria.getLocalizacao().isBlank()) {
			v.setLocalizacao(vistoria.getLocalizacao());
		}
		if (!vistoria.getObs().isEmpty() && !vistoria.getObs().isBlank()) {
			v.setObs(vistoria.getObs());
		}
		if (vistoria.getDataUltima() != null) {
			v.setDataUltima(vistoria.getDataUltima());
		}
		if (multiparte != null) {
			try {

				// Se a nova imagem for nula, continuará a do banco.
				if (multiparte.getSize() > 10) {
					v.setFoto(multiparte.getBytes());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
		}

		// Testa se o usuário é o responsável pela vistoria a ser editada.
		// Matrícula do usuário que criou a vistoria:
		int matriculaAntiga = v.getFkUsuario();
		// Matrícula do usuário atual:
		int matriculaAtual = u.getMatricula();

		if (matriculaAntiga == matriculaAtual || matriculaAtual == 1) {
			uri.atualizar(v);
			this.carregaTodasListas();
		}

		return MAV;
	}

	/******************************************************************************************************************/
	// Gerencia a criação de uma nova Vistoria.
	// Os parâmetros Usuário e Extintor são obrigatórios.
	@RequestMapping(value = { "/vistoria" }, method = { RequestMethod.POST }, params = "criar", consumes = {
			"multipart/form-data" })
	public ModelAndView criar(@ModelAttribute("vistoria") Vistoria vistoria,
			@RequestParam("fotoUpload") MultipartFile multiparte, HttpSession sessao,
			MultipartHttpServletRequest request) {

		// Recebe o usuário atual. O escopo é de sessão.
		Usuario u = (Usuario) sessao.getAttribute("usuario");

		// Se o usuário não estiver logado, retornará à página de login.
		if (u.getNome().contains("Anônimo") || u.getNome().contains("Anonimous")) {
			return new ModelAndView("login");
		}

		try {
			// Passa para a vistoria o Id do usuário (matrícula).
			// O objeto Usuario é obtido do objeto HttpSession
			vistoria.setFkUsuario(((Usuario) sessao.getAttribute("usuario")).getMatricula());
			vistoria.setFoto(multiparte.getBytes());
			log.error("::Passando o upload.");
		} catch (IOException e) {
			log.error("::Erro ao converter o multipart em bytes.");
			e.printStackTrace();
		}

		uri.save(vistoria);
		this.carregaTodasListas();

		return MAV;
	}

	/******************************************************************************************************************/
	private ArrayList<Usuario> carregaListaUsuarios() {
		// Tenta buscar a lista de usuários no banco de dados. Isto irá popular o combo
		// box da busca por usuário. Este método deve ser @PostConstruct
		try {

			ArrayList<Usuario> listaUsuarios = (ArrayList<Usuario>) usuarioImp.findAll();
			listaUsuarios.sort(new Comparator<Usuario>() {

				@Override
				public int compare(Usuario u1, Usuario u2) {

					int compare = u1.getNome().compareTo(u2.getNome());
					return compare;
				}
			});
			return listaUsuarios;
		} catch (Exception e) {
			log.error("::Erro ao tentar buscar lista de agentes.");
			return null;
		}

	}

	/******************************************************************************************************************/
	private ArrayList<Extintor> carregaListaExtintores() {
		try {

			ArrayList<Extintor> listaExtintores = (ArrayList<Extintor>) extintorImp.findAll();
			return listaExtintores;
		} catch (Exception e) {
			log.error("::Erro ao tentar buscar lista de extintores.");
			return null;
		}
	}

	/******************************************************************************************************************/
	private ArrayList<Vistoria> carregaListaVistorias() {
		try {
			ArrayList<Vistoria> listaVistorias = (ArrayList<Vistoria>) vistoriaImp.findAll();
			return listaVistorias;
		} catch (Exception e) {
			log.error("::Erro ao tentar buscar lista de vistorias.");
			return null;
		}
	}

	/******************************************************************************************************************/
	private void carregaTodasListas() {

		// Tenta buscar a lista de usuários no banco de dados. Isto irá popular o
		// combobox da busca por usuário. Este método deve ser @PostConstruct
		MAV.addObject("listaUsuarios", this.carregaListaUsuarios());

		// Tenta buscar a lista de extintores no banco de dados. Isto irá popular o
		// combobox da busca por extintor. Este método deve ser @PostConstruct
		MAV.addObject("listaExtintores", this.carregaListaExtintores());

		// Tenta buscar a lista de vistorias no banco de dados. Isto irá popular o
		// combobox da busca por vistoria. Este método deve ser @PostConstruct
		MAV.addObject("listaVistorias", this.carregaListaVistorias());
	}
}
