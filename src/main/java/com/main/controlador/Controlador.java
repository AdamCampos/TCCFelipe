package com.main.controlador;

import com.main.modelo.Usuario;
import com.main.modelo.UsuarioRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class Controlador {

	@Autowired
	Usuario usuario;

	@Autowired
	UsuarioRepositoryImpl uri;

	@RequestMapping(value = { "/", "/home", "/index" }, method = { RequestMethod.GET })
	public String resolveIndex(Model model) {
		return "index";
	}

	@RequestMapping(value = { "/login" }, method = { RequestMethod.GET })
	public String resolveLogin(Model model) {

		List<Usuario> lista = new ArrayList<Usuario>();

		Iterable<Usuario> iu = uri.findAll();
		for (Usuario u : iu) {
			lista.add(u);
			log.info("Usuario atual: " + u.getMatricula());
		}

		model.addAttribute("atributo1", (lista.get(0)).getNome());
		return "login";
	}

	@RequestMapping(value = { "/main" }, method = { RequestMethod.GET })
	public String resolveMain(Model model, HttpSession session) {

		Usuario usuario = (Usuario) session.getAttribute("usuario");
		log.debug("Session Attrib.: " + usuario.getNome());
		return "main";
	}

	@RequestMapping(value = { "/main" }, method = { RequestMethod.POST })
	public ModelAndView processaMain(@ModelAttribute("usuario") Usuario usuario, HttpSession sessao) {

		boolean usuarioExiste = false;
		ModelAndView mav = new ModelAndView("login");

		log.info("Entrando Usuario: " + usuario);
		log.info("Entrando Matrícula: " + usuario.getMatricula());

		// Tenta buscar no banco de dados o objeto Usuario pela matrícula (PK)
		try {

			Usuario usuarioRetornado = uri.findOne(String.valueOf(usuario.getMatricula()));

			// Se o usuário existe o flag é verdadeiro e a página main é chamada.
			// caso contrário a página de login é recerregada.
			if (usuarioRetornado != null) {
				usuarioExiste = true;

				// Uma vez que o usuário exista, conferir a senha.
				if (usuarioRetornado.getSenha() == usuario.getSenha()) {
					log.info("Usuáro e senha ok!");
					sessao.setAttribute("usuario", usuarioRetornado);
					mav.getModel().put("usuario", usuarioRetornado);
					mav.setViewName("redirect:/main");
					return mav;
				}

			} else {
				usuarioExiste = false;
				mav.setViewName("login");
				return mav;
			}

		} catch (Exception erroPesquisaPorMatricula) {
			usuarioExiste = false;
			log.error("Erro na pesquisa por matrícula: " + erroPesquisaPorMatricula);
		}
		return mav;

	}

	@RequestMapping(value = { "/current" }, method = { RequestMethod.GET })
	public String showDesignForm2(Model model) {
		log.info("Entendido!");
		return "orders/current";
	}

}
