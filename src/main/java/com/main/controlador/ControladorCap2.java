package com.main.controlador;

import com.main.modelo.EnumTeste;
import com.main.modelo.EnumTeste.Type;
import com.main.modelo.Usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class ControladorCap2 {

	Usuario usuario;

	List<String> list = getList();

	List<EnumTeste> list2 = Arrays.asList(new EnumTeste("FLTO", "Flour Tortilla", Type.AGUA),
			new EnumTeste("COTO", "Corn Tortilla", Type.AGUA), new EnumTeste("GRBF", "Ground Beef", Type.ESPUMA),
			new EnumTeste("CARN", "Carnitas", Type.ESPUMA), new EnumTeste("TMTO", "Diced Tomatoes", Type.PÓ),
			new EnumTeste("LETC", "Lettuce", Type.GÁS_INERTE), new EnumTeste("CHED", "Cheddar", Type.ESPUMA),
			new EnumTeste("JACK", "Monterrey Jack", Type.PÓ), new EnumTeste("SLSA", "Salsa", Type.GÁS_INERTE),
			new EnumTeste("SRCR", "Sour Cream", Type.PÓ));

	Type[] types = EnumTeste.Type.values();

	private List<String> getList() {

		List<String> list = new ArrayList<String>();
		list.add("List A");
		list.add("List B");
		list.add("List C");
		list.add("List D");
		list.add("List 1");
		list.add("List 2");
		list.add("List 3");

		return list;

	}

	@ModelAttribute(name = "usuario")
	public Usuario usuario() {
		return new Usuario();
	}

	@RequestMapping(value = { "/", "/home" }, method = { RequestMethod.GET })
	public String home(Model model) {
		model.addAttribute("usuario", usuario);
		log.info("Home!");
		return "home";
	}

	@RequestMapping(value = { "/design" }, method = { RequestMethod.GET })
	public String showDesignForm(Model model) {
		log.info("UsuarioGET: " + usuario);
		return "design";
	}

	@RequestMapping(value = { "/design" },method = { RequestMethod.POST })
	public String processDesign(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult,
			Model model) {

		log.info("UsuarioPOST: " + usuario);

		return "redirect:/design";
	}

	@RequestMapping(value = { "/current" }, method = { RequestMethod.GET })
	public String showDesignForm2(Model model) {
		log.info("Entendido!");
		return "orders/current";
	}

}
