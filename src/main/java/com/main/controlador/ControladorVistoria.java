package com.main.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
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

	/******************************************************************************************************************/
	@RequestMapping(value = { "/vistoria" }, method = { RequestMethod.GET })
	public ModelAndView resolveIndex(@ModelAttribute("vistoria") Vistoria vistoria, HttpSession sessao) {

		log.debug("::Teste vistoria ");

		ModelAndView mav = new ModelAndView("vistoria");

		Usuario u = (Usuario) sessao.getAttribute("usuario");

		if (u.getNome().contains("Anônimo") || u.getNome().contains("Anonimous")) {
			mav = new ModelAndView("login");
		} else {

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

				mav.addObject("listaUsuarios", listaUsuarios);
			} catch (Exception e) {
				log.error("::Erro ao tentar buscar lista de agentes.");
			}

			// Tenta buscar a lista de extintores no banco de dados. Isto irá popular o
			// combobox da busca por extintor. Este método deve ser @PostConstruct

			try {

				ArrayList<Extintor> listaExtintores = (ArrayList<Extintor>) extintorImp.findAll();
				mav.addObject("listaExtintores", listaExtintores);

			} catch (Exception e) {
				log.error("::Erro ao tentar buscar lista de extintores.");
			}

			// Tenta buscar a lista de vistorias no banco de dados. Isto irá popular o
			// combobox da busca por vistoria. Este método deve ser @PostConstruct

			try {
				ArrayList<Vistoria> listaVistorias = (ArrayList<Vistoria>) vistoriaImp.findAll();
				mav.addObject("listaVistorias", listaVistorias);

				Vistoria v = new Vistoria();
				String fotoString = Base64.encodeBase64String(listaVistorias.get(listaVistorias.size() - 1).getFoto());
				v.setFoto(listaVistorias.get(listaVistorias.size() - 1).getFoto());
				log.debug("::Foto em v. " + v.getFoto());
				v.setId(500);
				v.setFkUsuario(5);

				sessao.setAttribute("v", v);
				sessao.setAttribute("fotoString", fotoString);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return mav;
	}

	/******************************************************************************************************************/
	@RequestMapping(value = { "/buscaVistoria" }, method = { RequestMethod.GET }, params = "usuario")
	public ModelAndView buscaUsuario(@ModelAttribute("vistoria") Vistoria vistoria, HttpSession sessao,
			WebRequest requisicao) {

		ModelAndView mav = new ModelAndView("vistoria");

		String matricula = requisicao.getParameter("usuario");
		log.debug("::buscando pelo usuário " + matricula);

		// Busca vistorias do usuário escolhido

		sessao.removeAttribute("lista");
		ArrayList<Vistoria> listaVistoriasPorUsuario = (ArrayList<Vistoria>) uri
				.buscaPorUsuario(Integer.valueOf(matricula));
		sessao.setAttribute("vistoria", vistoria);

		log.debug("::lista de vistorias: " + listaVistoriasPorUsuario.size());
		mav.addObject("lista", listaVistoriasPorUsuario);

		try {

			ArrayList<Usuario> listaUsuarios = (ArrayList<Usuario>) usuarioImp.findAll();

			listaUsuarios.sort(new Comparator<Usuario>() {

				@Override
				public int compare(Usuario u1, Usuario u2) {

					int compare = u1.getNome().compareTo(u2.getNome());
					return compare;
				}
			});

			mav.addObject("listaUsuarios", listaUsuarios);

		} catch (Exception e) {
			log.error("::Erro ao tentar buscar lista de agentes.");
		}

		// Tenta buscar a lista de extintores no banco de dados. Isto irá popular o
		// combobox da busca por extintor. Este método deve ser @PostConstruct

		try {

			ArrayList<Extintor> listaExtintores = (ArrayList<Extintor>) extintorImp.findAll();
			mav.addObject("listaExtintores", listaExtintores);

		} catch (Exception e) {
			log.error("::Erro ao tentar buscar lista de extintores.");
		}

		// Tenta buscar a lista de vistorias no banco de dados. Isto irá popular o
		// combobox da busca por vistoria. Este método deve ser @PostConstruct

		try {
			ArrayList<Vistoria> listaVistorias = (ArrayList<Vistoria>) vistoriaImp.findAll();
			mav.addObject("listaVistorias", listaVistorias);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	/******************************************************************************************************************/
	@RequestMapping(value = { "/buscaVistoria" }, method = { RequestMethod.GET }, params = "extintor")
	public ModelAndView buscaExtintor(@ModelAttribute("vistoria") Vistoria vistoria, HttpSession sessao,
			WebRequest requisicao) {

		ModelAndView mav = new ModelAndView("vistoria");

		String id = requisicao.getParameter("extintor");
		log.debug("::buscando pelo extintor " + id);

		// Busca vistorias do usuário escolhido

		sessao.removeAttribute("lista");
		ArrayList<Vistoria> listaVistorias = (ArrayList<Vistoria>) uri.buscaPorExtintor(Integer.valueOf(id));
		sessao.setAttribute("vistoria", vistoria);

		log.debug("::lista de vistorias: " + listaVistorias.size());
		mav.addObject("lista", listaVistorias);

		try {

			ArrayList<Usuario> listaUsuarios = (ArrayList<Usuario>) usuarioImp.findAll();

			listaUsuarios.sort(new Comparator<Usuario>() {

				@Override
				public int compare(Usuario u1, Usuario u2) {

					int compare = u1.getNome().compareTo(u2.getNome());
					return compare;
				}
			});

			mav.addObject("listaUsuarios", listaUsuarios);

		} catch (Exception e) {
			log.error("::Erro ao tentar buscar lista de agentes.");
		}

		// Tenta buscar a lista de extintores no banco de dados. Isto irá popular o
		// combo
		// box da busca por extintor. Este método deve ser @PostConstruct

		try {

			ArrayList<Extintor> listaExtintores = (ArrayList<Extintor>) extintorImp.findAll();
			mav.addObject("listaExtintores", listaExtintores);

		} catch (Exception e) {
			log.error("::Erro ao tentar buscar lista de extintores.");
		}

		return mav;
	}

	/******************************************************************************************************************/
	@RequestMapping(value = { "/buscaVistoria" }, method = { RequestMethod.GET }, params = "vistoria")
	public ModelAndView buscaVistoria(@ModelAttribute("vistoria") Vistoria vistoria, HttpSession sessao,
			WebRequest requisicao, ModelMap model) {

		ModelAndView mav = new ModelAndView("vistoria");

		String id = requisicao.getParameter("vistoria");
		log.debug("::buscando pela vistoria " + id);

		// Busca vistorias pelo Id

		sessao.removeAttribute("lista");
		ArrayList<Vistoria> listaVistorias = (ArrayList<Vistoria>) uri.buscaPorVistoria(Integer.valueOf(id));
		sessao.setAttribute("vistoria", vistoria);

		log.debug("::lista de vistorias: " + listaVistorias.size());
		mav.addObject("listaVistorias", listaVistorias);

		try {

			ArrayList<Usuario> listaUsuarios = (ArrayList<Usuario>) usuarioImp.findAll();

			listaUsuarios.sort(new Comparator<Usuario>() {

				@Override
				public int compare(Usuario u1, Usuario u2) {

					int compare = u1.getNome().compareTo(u2.getNome());
					return compare;
				}
			});

			mav.addObject("listaUsuarios", listaUsuarios);

		} catch (Exception e) {
			log.error("::Erro ao tentar buscar lista de agentes.");
		}

		// Tenta buscar a lista de extintores no banco de dados. Isto irá popular o
		// combobox da busca por extintor. Este método deve ser @PostConstruct

		try {

			ArrayList<Extintor> listaExtintores = (ArrayList<Extintor>) extintorImp.findAll();
			mav.addObject("listaExtintores", listaExtintores);

		} catch (Exception e) {
			log.error("::Erro ao tentar buscar lista de extintores.");
		}

		// model.addAttribute("pic",
		// Base64.getEncoder().encodeToString(vistoria.getFoto()));

		return mav;
	}

	/******************************************************************************************************************/

	@RequestMapping(value = { "/vistoria" }, method = { RequestMethod.POST }, params = "deletar")
	public ModelAndView deletar(@ModelAttribute("vistoria") Vistoria vistoria, HttpSession sessao) {

		ModelAndView mav = new ModelAndView("vistoria");
		log.debug("::deletando vistoria: " + vistoria.getId());
		uri.delete(vistoria);

		return mav;
	}

	/******************************************************************************************************************/
	@RequestMapping(value = { "/vistoria" }, method = { RequestMethod.POST }, params = "criar", consumes = {
			"multipart/form-data" })
	public ModelAndView criar(@ModelAttribute("vistoria") Vistoria vistoria,
			@RequestParam("fotoUpload") MultipartFile multiparte, HttpSession sessao,
			MultipartHttpServletRequest request) {

		// Toda vistoria deve ter um Usuario e pelo menos um extintor.

		// Recupera o usuário logado
		Usuario u = (Usuario) sessao.getAttribute("usuario");
		// Passa para a vistoria o Id do usuário (matrícula)
		vistoria.setFkUsuario(u.getMatricula());
		try {
			vistoria.setFoto(multiparte.getBytes());
			log.error("::Passando o upload.");
		} catch (IOException e) {
			log.error("::Erro qao converter o multipart em bytes.");
			e.printStackTrace();
		}

		ModelAndView mav = new ModelAndView("vistoria");
		uri.save(vistoria);

		return mav;
	}
}
