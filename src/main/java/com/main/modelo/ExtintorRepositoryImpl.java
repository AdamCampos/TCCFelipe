package com.main.modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class ExtintorRepositoryImpl implements ExtintorRepository {

	private JdbcTemplate jdbc;

	@Autowired
	public ExtintorRepositoryImpl(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public <S extends Extintor> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Extintor> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Extintor> findAllById(Iterable<String> ids) {
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
	public void delete(Extintor entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends Extintor> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterable<Extintor> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Extintor findOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Extintor save(Extintor extintor) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Extintor> buscarId(int id) {

		log.info("Pesquisando id " + id);
		ArrayList<Extintor> lista = new ArrayList<Extintor>();
		try {
			lista = (ArrayList<Extintor>) jdbc.query(
					"select id, classe, agente, dataCompra, volume, foto from Extintor where id=?", this::mapeiaLinhaExtintor, id);
		} catch (Exception e) {
			log.debug("ERRO 1::" + e);
		}

		return lista;
	}

	public List<Extintor> buscarAgente(String agente) {

		log.info("Pesquisando classe " + agente);
		ArrayList<Extintor> lista = new ArrayList<Extintor>();
		try {
			lista = (ArrayList<Extintor>) jdbc.query(
					"select id, classe, agente, dataCompra, volume, foto from Extintor where agente like ?",
					this::mapeiaLinhaExtintor, agente);
		} catch (Exception e) {
			log.debug("ERRO 2::" + e);
		}

		return lista;
	}

	private Extintor mapeiaLinhaExtintor(ResultSet rs, int rowNum) {

		Extintor u = new Extintor();

		try {
			u = new Extintor(rs.getInt("id"), rs.getString("classe"), rs.getString("agente"),
					rs.getString("dataCompra"), rs.getDouble("volume"), rs.getString("foto"));
			log.debug("::" + u);
			return u;
		} catch (SQLException e) {
			log.debug("Erro:: " + e);
			e.printStackTrace();
			return u;
		}
	}
}
