package com.main.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.main.modelo.Extintor;
import com.main.modelo.ExtintorRepositoryImpl;

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

		List<Extintor> listaTiposAgentes;

		// Retorna a lista só com os tipos agentes
		try {
			listaTiposAgentes = uri.retornaItensAgentes();
			mav.addObject("listaAgentes", listaTiposAgentes);
		} catch (Exception e) {
			log.error("::Erro ao tentar buscar lista de agentes.");
		}

		try {
			if (extintor != null) {

				sessao.removeAttribute("extintor");
				sessao.setAttribute("extintor", extintor);

				// No caso de id <= 0, a pesquisa será pelo tipo
				if (extintor.getId() <= 0) {
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

	/******************************************************************************************************************/
	@RequestMapping(value = { "/extintor" }, method = { RequestMethod.POST }, params = "criar")
	public ModelAndView criar(@ModelAttribute("extintor") Extintor extintor, HttpSession sessao) {

		ModelAndView mav = new ModelAndView("extintor");
		uri.save(extintor);

		return mav;
	}

	/******************************************************************************************************************/
	@RequestMapping(value = { "/extintor" }, method = { RequestMethod.POST }, params = "atualizar")
	public ModelAndView atualizar(@ModelAttribute("extintor") Extintor extintor, HttpSession sessao) {

		ModelAndView mav = new ModelAndView("extintor");

		int id = extintor.getId();
		String classe = extintor.getClasse();
		String agente = extintor.getAgente();
		String dataCompra = extintor.getDataCompra();
		String volume = extintor.getVolume();
		String foto = extintor.getFoto();

		Extintor u = uri.findOne(String.valueOf(id));
		u.setClasse(classe);
		u.setAgente(agente);
		u.setDataCompra(dataCompra);
		u.setVolume(volume);
		u.setFoto(foto);

		uri.save(u);

		return mav;
	}

	/******************************************************************************************************************/

	@RequestMapping(value = { "/extintor" }, method = { RequestMethod.POST }, params = "deletar")
	public ModelAndView deletar(@ModelAttribute("extintor") Extintor extintor, HttpSession sessao) {

		ModelAndView mav = new ModelAndView("extintor");

		uri.delete(extintor);

		return mav;
	}
}
