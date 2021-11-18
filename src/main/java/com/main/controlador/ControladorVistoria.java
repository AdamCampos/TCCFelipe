package com.main.controlador;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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

	@RequestMapping(value = { "/vistoria" }, method = { RequestMethod.GET })
	public ModelAndView resolveIndex(@ModelAttribute("vistoria") Vistoria vistoria, HttpSession sessao) {

		ModelAndView mav = new ModelAndView("vistoria");

		sessao.removeAttribute("vistoria");
		sessao.setAttribute("vistoria", vistoria);

		ArrayList<Vistoria> listaVistorias = (ArrayList<Vistoria>) uri.findAll();

		log.debug("::lista de vistorias: " + listaVistorias.size());
		mav.addObject("lista", listaVistorias);

		return mav;
	}
}
