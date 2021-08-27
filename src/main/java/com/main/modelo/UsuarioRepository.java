package com.main.modelo;

import org.springframework.data.repository.CrudRepository;
	
public interface UsuarioRepository extends CrudRepository<Usuario, String> {

	Iterable<Usuario> findAll();

	Usuario findOne(String id);

	@SuppressWarnings("unchecked")
	Usuario save(Usuario usuario);

}
