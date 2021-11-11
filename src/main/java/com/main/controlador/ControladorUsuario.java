package com.main.controlador;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.main.modelo.Usuario;
import com.main.modelo.UsuarioRepositoryImpl;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class ControladorUsuario {

	@Autowired
	Usuario usuario;

	@Autowired
	UsuarioRepositoryImpl uri;

	/******************************************************************************************************************/

	// Trata a requisição POST com direcionamento "main" e filtra pelo parêmetro de
	// ação "logar", dado pelo botão submit do form
	@RequestMapping(value = { "/main" }, method = { RequestMethod.POST }, params = "logar")
	// O método logar devolve uma página modificada e recebe alguns parâmetros
	// objeto Usuario, dados de sessão e dados de requisição.
	@CacheEvict
	public ModelAndView postLogar(@ModelAttribute("usuario") Usuario usuario, HttpSession sessao,
			WebRequest requisicao) {

		// Cria um objeto para ser retornado em forma de página web
		ModelAndView mav = new ModelAndView("login");

		log.debug("::Iniciando logar");
		log.debug(":: " + requisicao.getParameter("matriculaOuNome"));

		// Cria um objeto para receber dados da pesquisa
		Usuario usuarioRetornado = null;

		// O form possui um campo que pode receber nome ou matrícula. Neste trecho o
		// valor é interpretado e passado para a pesquisa.
		String nomeMatricula = requisicao.getParameter("matriculaOuNome");
		try {
			int iNomeMatricula = Integer.valueOf(nomeMatricula);
			usuarioRetornado = uri.findOne(String.valueOf(iNomeMatricula), usuario.getSenha());
			log.debug(":: Convertido para int" + iNomeMatricula);
		} catch (Exception e) {
			log.debug(":: Direto para String " + nomeMatricula);
			usuarioRetornado = uri.findOne("%" + nomeMatricula.trim() + "%", usuario.getSenha());
		}

		// Testa se alguma pesquisa retornou algum objeto válido
		if (usuarioRetornado != null) {
			log.debug("::Retonou " + usuarioRetornado.getMatricula());
			sessao.setAttribute("usuario", usuarioRetornado);
			mav.getModel().put("usuario", usuarioRetornado);
		}

		return mav;

	}

	/******************************************************************************************************************/

	@RequestMapping(value = { "/", "/home", "/index" }, method = { RequestMethod.GET })
	public String getIndex(Model model, HttpSession sessao) {
		return "index";
	}

	/******************************************************************************************************************/
	@RequestMapping(value = { "/", "/home", "/index", "/main", "/login" }, method = {
			RequestMethod.GET }, params = "logoff")
	public String logoff(Model model, HttpSession sessao) {

		usuario = new Usuario(0, "Anônimo", 0, null);
		sessao.removeAttribute("usuario");
		sessao.setAttribute("usuario", usuario);

		log.info("Logoff");
		return "index";
	}

	@RequestMapping(value = { "/login" }, method = { RequestMethod.GET })
	public String resolveLogin(Model model, HttpSession sessao) {

		try {
			Usuario usuario = (Usuario) sessao.getAttribute("usuario");
			if (usuario == null) {
				usuario = new Usuario(0, "Anônimo", 0, null);
				sessao.setAttribute("usuario", usuario);
			}
			log.info("Usuario inicio do login " + usuario);
		} catch (Exception e) {
			log.error("Erro usuario " + e);
		}

		return "login";
	}

	@RequestMapping(value = { "/main" }, method = { RequestMethod.GET })
	public String resolveMain(Model model, HttpSession sessao) {

		Usuario usuario = (Usuario) sessao.getAttribute("usuario");

		if (usuario.getNome().equals("Anônimo")) {
			return "login";
		} else {
			return "main";
		}
	}

	@RequestMapping(value = { "/main" }, method = { RequestMethod.POST }, params = "atualizar")
	public ModelAndView atualizar(@ModelAttribute("usuario") Usuario usuario, HttpSession sessao) {

		ModelAndView mav = new ModelAndView("login");

		int matricula = usuario.getMatricula();
		String nome = usuario.getNome();
		int senha = usuario.getSenha();

		Usuario u = uri.findOne(String.valueOf(matricula));
		u.setNome(nome);
		u.setSenha(senha);

		uri.save(u);
		log.info("Alterando nome de " + u.getNome() + " para " + nome);

		return mav;
	}

	@RequestMapping(value = { "/main" }, method = { RequestMethod.POST }, params = "buscar")
	public ModelAndView buscar(@ModelAttribute("usuario") Usuario usuario, HttpSession sessao) {

		ModelAndView mav = new ModelAndView("login");
		log.info("Buscando " + usuario);

		int matricula = usuario.getMatricula();
		String nome = usuario.getNome();

		ArrayList<Usuario> lista = (ArrayList<Usuario>) uri.buscar(nome, matricula);
		mav.addObject("lista", lista);

		return mav;
	}

	@RequestMapping(value = { "/main" }, method = { RequestMethod.POST }, params = "criar")
	public ModelAndView criar(@ModelAttribute("usuario") Usuario usuario, HttpSession sessao) {

		ModelAndView mav = new ModelAndView("login");
		uri.save(usuario);

		return mav;
	}

	@RequestMapping(value = { "/main" }, method = { RequestMethod.POST }, params = "deletar")
	public ModelAndView deletar(@ModelAttribute("usuario") Usuario usuario, HttpSession sessao) {

		ModelAndView mav = new ModelAndView("login");
		uri.delete(uri.findOne(String.valueOf(usuario.getMatricula())));

		return mav;
	}

	@RequestMapping(value = { "/main" }, method = { RequestMethod.POST }, params = "logoff")
	public ModelAndView logOff(@ModelAttribute("usuario") Usuario usuario, HttpSession sessao) {

		ModelAndView mav = new ModelAndView("login");

		log.debug("::Iniciando logoff");

//		if (usuario == null || usuario.getNome().equals("Anônimo")) {
//			mav.setViewName("login");
//			return mav;
//		}
//
//		// Tenta buscar no banco de dados o objeto Usuario pela matrícula (PK)
//		try {
//
//			Usuario usuarioRetornado = uri.findOne(String.valueOf(usuario.getMatricula()));
//
//			// Se o usuário existe o flag é verdadeiro e a página main é chamada.
//			// caso contrário a página de login é recerregada.
//			if (usuarioRetornado != null) {
//				// Uma vez que o usuário exista, conferir a senha.
//				if (usuarioRetornado.getSenha() == usuario.getSenha()) {
//					log.info("Usuáro e senha ok!");
//					sessao.setAttribute("usuario", usuarioRetornado);
//					mav.getModel().put("usuario", usuarioRetornado);
//					mav.setViewName("redirect:/main");
//					return mav;
//				}
//
//			} else {
//				mav.setViewName("login");
//				return mav;
//			}
//
//		} catch (Exception erroPesquisaPorMatricula) {
//			log.error("Erro na pesquisa por matrícula: " + erroPesquisaPorMatricula);
//		}
		return mav;

	}

	@RequestMapping(value = { "/current" }, method = { RequestMethod.GET })
	public String showDesignForm2(Model model) {
		log.info("Entendido!");
		return "orders/current";
	}

}
