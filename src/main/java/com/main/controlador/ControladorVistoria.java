package com.main.controlador;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.main.modelo.Usuario;
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
	public String resolveIndex(Model model, HttpSession sessao) {

		Usuario usuario = (Usuario) sessao.getAttribute("usuario");
		log.debug("Debug vistoria: " + usuario.getNome() + " foto: " + usuario.getFoto());

		return "vistoria";
	}
}
