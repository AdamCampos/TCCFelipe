package com.main.modelo;

import org.springframework.data.repository.CrudRepository;

public interface VistoriaRepository  extends CrudRepository<Vistoria, String> {

	Iterable<Vistoria> findAll();

	Vistoria findOne(String id);

	@SuppressWarnings("unchecked")
	Vistoria save(Vistoria vistoria);

}

