
package com.main.modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class VistoriaRepositoryImpl implements VistoriaRepository {

	private JdbcTemplate jdbc;

	@Autowired
	public VistoriaRepositoryImpl(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
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

		List<Vistoria> lista = jdbc.query("select * from vistoria ", this::mapeiaLinhaVistoria);
		return lista;
	}

	@Override
	public Vistoria findOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vistoria save(Vistoria vistoria) {
		// TODO Auto-generated method stub
		return null;
	}

	private Vistoria mapeiaLinhaVistoria(ResultSet rs, int rowNum) {

		Vistoria u = new Vistoria();

		try {
			u = new Vistoria(rs.getInt("id"), rs.getInt("fkExtintor"), rs.getInt("fkUsuario"),
					rs.getString("dataUltima"), rs.getString("dataProxima"), rs.getInt("periodo"),
					rs.getString("dataTeste"), rs.getString("foto"), rs.getString("localizacao"), rs.getString("obs"));
			log.debug("::" + u);
			return u;
		} catch (SQLException e) {
			log.debug("::Erro " + e);
			return u;
		}
	}

}
