package com.main.controlador;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.main.modelo.Extintor;
import com.main.modelo.ExtintorRepositoryImpl;
import com.main.modelo.Usuario;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class ControladorExtintor {

	@Autowired
	Extintor extintor;

	@Autowired
	ExtintorRepositoryImpl uri;

	// A navegação para extintor não tem restrição, pois o QR deve conseguir acessar
	// o banco automaticamente.
	@RequestMapping(value = { "/extintor" }, method = { RequestMethod.GET })
	public ModelAndView getExtintor(@ModelAttribute("extintor") Extintor extintor, HttpSession sessao) {

		ModelAndView mav = new ModelAndView("extintor");
		ModelAndView mavId = new ModelAndView("extintorId");

		Usuario usuario = (Usuario) sessao.getAttribute("usuario");
		log.debug("::Extintor - usuário logado: " + usuario.getNome());

		try {
			if (extintor != null) {

				sessao.removeAttribute("extintor");
				sessao.setAttribute("extintor", extintor);

				if (extintor.getId() <= 0) {
					// No caso de id <= 0, a pesquisa será pelo tipo
					ArrayList<Extintor> lista = (ArrayList<Extintor>) uri
							.buscarAgente("%" + extintor.getAgente() + "%");

					for (Extintor e : lista) {

						log.debug("::Retornado id: " + e.getId() + " tipo " + e.getAgente());

					}

					if (lista.size() <= 0) {
						log.debug("::Não retornado");
					}

					mav.addObject("lista", lista);
					return mav;
				} else {

					log.debug("::Existe Id: " + extintor.getId());

					ArrayList<Extintor> lista = (ArrayList<Extintor>) uri.buscarId(extintor.getId());
					mavId.addObject("lista", lista);
					return mavId;
				}

			}
			log.debug("::Nulo");

		} catch (Exception e) {
			log.error("::Erro ao pesquisar extintor " + e);
			return mav;
		}

		return mav;
	}

}
