package com.main.modelo;

import org.springframework.data.repository.CrudRepository;

public interface ExtintorRepository  extends CrudRepository<Extintor, String> {

	Iterable<Extintor> findAll();

	Extintor findOne(String id);

	@SuppressWarnings("unchecked")
	Extintor save(Extintor extintor);

}

