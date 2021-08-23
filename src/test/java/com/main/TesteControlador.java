package com.main;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.main.controlador.Controlador;

@RunWith(SpringRunner.class)
@WebMvcTest(Controlador.class)
public class TesteControlador {

	private static final Logger LOGGER = LogManager.getLogger();

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testHomePage() throws Exception {

		ResultActions ra1 = mockMvc.perform(get("/"));
		ResultActions ra2 = ra1.andExpect(status().isOk());
		ResultActions ra3 = ra2.andExpect(view().name("home"));
		ResultActions ra4 = ra3.andExpect(content().string(containsString("Welcome to...")));

		LOGGER.debug("RA " + ra4.toString());

	}
}