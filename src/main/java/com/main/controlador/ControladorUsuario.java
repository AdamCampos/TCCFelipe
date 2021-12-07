package com.main.controlador;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.main.modelo.Usuario;
import com.main.modelo.UsuarioRepositoryImpl;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class ControladorUsuario {

	private final static ModelAndView MAV = new ModelAndView("login");

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
	public ModelAndView postLogar(@ModelAttribute("usuario") Usuario usuario, HttpSession sessao, WebRequest requisicao,
			@Valid Usuario usuarioValidado) {

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
			MAV.getModel().put("usuario", usuarioRetornado);
		}

		return MAV;

	}

	/******************************************************************************************************************/

	@RequestMapping(value = { "/", "/home", "/index" }, method = { RequestMethod.GET })
	public String getIndex(Model model, HttpSession sessao) {
		return "index";
	}

	/******************************************************************************************************************/
	@RequestMapping(value = { "/main" }, method = { RequestMethod.POST }, params = "criar", consumes = {
			"multipart/form-data" })
	public ModelAndView criar(@ModelAttribute("usuario") Usuario usuario, HttpSession sessao,
			@RequestParam("fotoUpload") MultipartFile multiparte) {

		try {
			usuario.setImg(multiparte.getBytes());
		} catch (IOException e) { //
		}

		uri.save(usuario);

		return MAV;
	}

	/******************************************************************************************************************/
	@RequestMapping(value = { "/main" }, method = { RequestMethod.POST }, params = "atualizar", consumes = {
			"multipart/form-data" })
	public ModelAndView atualizar(@Valid @ModelAttribute("usuario") Usuario usuario, HttpSession sessao,
			@RequestParam("fotoUpload") MultipartFile multiparte, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return MAV;
		}

		Usuario u = uri.findOne(String.valueOf(usuario.getMatricula()));

		if (!usuario.getNome().isEmpty() && !usuario.getNome().isBlank() && !usuario.getNome().contains("Generico")) {
			u.setNome(usuario.getNome());
		}

		if (usuario.getSenha() > 0) {
			u.setSenha(usuario.getSenha());
		}

		if (multiparte.getSize() > 10) {
			try {
				u.setImg(multiparte.getBytes());
			} catch (IOException e) {
			}
		}

		log.debug("::Admin " + usuario.isAdmin());

		uri.atualizar(u);

		return MAV;
	}

	/******************************************************************************************************************/

	@RequestMapping(value = { "/logoff" }, method = { RequestMethod.GET })
	public ModelAndView logOff(HttpSession sessao) {

		Usuario usuarioRetornado = uri.findOne(String.valueOf(999), 0);
		sessao.setAttribute("usuario", usuarioRetornado);
		MAV.getModel().put("usuario", usuarioRetornado);

		return MAV;

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

		return MAV;
	}

	/******************************************************************************************************************/

	@RequestMapping(value = { "/login" }, method = { RequestMethod.GET })
	public String resolveLogin(Model model, HttpSession sessao) {

		Usuario u = (Usuario) sessao.getAttribute("usuario");

		try {
			if (u.getMatricula() == 999) {

				Usuario usuarioRetornado = uri.findOne(String.valueOf(999), 0);
				sessao.setAttribute("usuario", usuarioRetornado);
				MAV.getModel().put("usuario", usuarioRetornado);
			}
		} catch (Exception e) {

			Usuario usuarioRetornado = uri.findOne(String.valueOf(999), 0);
			sessao.setAttribute("usuario", usuarioRetornado);
			MAV.getModel().put("usuario", usuarioRetornado);

		}

		return "login";
	}

}