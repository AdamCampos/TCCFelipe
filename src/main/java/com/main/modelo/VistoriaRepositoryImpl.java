
package com.main.modelo;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class VistoriaRepositoryImpl implements VistoriaRepository{@Override
	public <S extends Vistoria> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Vistoria> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Vistoria> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Vistoria entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Vistoria> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterable<Vistoria> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vistoria findOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vistoria save(Vistoria vistoria) {
		// TODO Auto-generated method stub
		return null;
	}
	}


