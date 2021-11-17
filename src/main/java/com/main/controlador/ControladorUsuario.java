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

		// Cria um objeto para receber dados da pesquisa
		Usuario usuarioRetornado = null;

		// O form possui um campo que pode receber nome ou matrícula. Neste trecho o
		// valor é interpretado e passado para a pesquisa.
		String nomeMatricula = requisicao.getParameter("matriculaOuNome");
		try {
			int iNomeMatricula = Integer.valueOf(nomeMatricula);
			usuarioRetornado = uri.findOne(String.valueOf(iNomeMatricula), usuario.getSenha());
		} catch (Exception e) {
			usuarioRetornado = uri.findOne("%" + nomeMatricula.trim() + "%", usuario.getSenha());
		}

		// Testa se alguma pesquisa retornou algum objeto válido
		if (usuarioRetornado != null) {
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
	@RequestMapping(value = { "/main" }, method = { RequestMethod.POST }, params = "criar")
	public ModelAndView criar(@ModelAttribute("usuario") Usuario usuario, HttpSession sessao) {

		ModelAndView mav = new ModelAndView("login");
		uri.save(usuario);

		return mav;
	}

	/******************************************************************************************************************/
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

	/******************************************************************************************************************/

	@RequestMapping(value = { "/logoff" }, method = { RequestMethod.GET })
	public ModelAndView logOff(HttpSession sessao) {

		ModelAndView mav = new ModelAndView("login");
		usuario = new Usuario(0, "Anônimo", 0, null);
		sessao.removeAttribute("usuario");
		sessao.setAttribute("usuario", usuario);
		log.debug("::Iniciando logoff");

		return mav;

	}

	/******************************************************************************************************************/

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

	/******************************************************************************************************************/

	@RequestMapping(value = { "/main" }, method = { RequestMethod.POST }, params = "deletar")
	public ModelAndView deletar(WebRequest requisicao, HttpSession sessao) {

		ModelAndView mav = new ModelAndView("login");
		String nome = requisicao.getParameter("nome");
		int matricula = Integer.valueOf(requisicao.getParameter("matricula"));

		log.debug("::Nome " + nome);
		log.debug("::Matricula " + matricula);

		String identificacaoUsuario = null;

		if (nome.isEmpty() || matricula > 0) {
			identificacaoUsuario = String.valueOf(matricula);
		} else if (!nome.isEmpty()) {
			identificacaoUsuario = nome;
		}

		uri.delete(uri.findOne(identificacaoUsuario));

		return mav;
	}

	/******************************************************************************************************************/

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

}