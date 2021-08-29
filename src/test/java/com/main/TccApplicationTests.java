package com.main;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.main.modelo.Usuario;
import com.main.modelo.UsuarioRepositoryImpl;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
@RunWith(SpringRunner.class)
class TccApplicationTests {

	@Autowired
	UsuarioRepositoryImpl usuarioImp;
	@Autowired
	Usuario usuario;

	@Test
	void contextLoads() {

		try {
			usuario.setNome("Usuario_Netbeans");
			Iterable<Usuario> iu = usuarioImp.findAll();
			for (Usuario u : iu) {
				log.info("Usuario atual: " + u.getMatricula());
			}
			usuario.setMatricula(90);
			log.debug("Tentando salvar " + usuario);
			usuarioImp.save(usuario);
		} catch (Exception e) {
			log.error("Erro " + e);
		}

		log.debug("Usaurio salvo: " + usuario.getNome());

	}

}
