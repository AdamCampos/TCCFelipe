
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
		jdbc.update("delete from vistoria where id = ?", entity.getId());
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
		Vistoria v = jdbc.queryForObject("select * from Vistoria where (id = ?) ", this::mapeiaLinhaVistoria, id);
		return v;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vistoria save(Vistoria vistoria) {
		try {

			jdbc.update(
					"insert into Vistoria (fkExtintor, fkUsuario, dataUltima, periodo,  dataTeste, foto, localizacao, obs) values (?, ?, ?, ?, ?, ?, ?, ?)",
					vistoria.getFkExtintor(), vistoria.getFkUsuario(), vistoria.getDataUltima(), vistoria.getPeriodo(),
					vistoria.getDataTeste(), vistoria.getFoto(), vistoria.getLocalizacao(), vistoria.getObs());

			log.debug("::Inserindo vistoria: " + vistoria.getId());

		} catch (Exception e) {
			log.debug("::Erro inserindo vistoria " + e);
		}

		return vistoria;
	}

	public Vistoria atualizar(Vistoria vistoria) {
		try {
			jdbc.update(
					"update  Vistoria set fkExtintor=?, fkUsuario=?, dataUltima=?, periodo=?,  dataTeste=?, foto=?, localizacao=?, obs=? where id=?",
					vistoria.getFkExtintor(), vistoria.getFkUsuario(), vistoria.getDataUltima(), vistoria.getPeriodo(),
					vistoria.getDataTeste(), vistoria.getFoto(), vistoria.getLocalizacao(), vistoria.getObs(),
					vistoria.getId());

		} catch (Exception e) {
			log.debug("::Erro inserindo vistoria " + e);
		}

		return vistoria;
	}

	private Vistoria mapeiaLinhaVistoria(ResultSet rs, int rowNum) {

		Vistoria u = new Vistoria();

		try {

			u = new Vistoria(rs.getInt("id"), rs.getInt("fkExtintor"), rs.getInt("fkUsuario"), rs.getDate("dataUltima"),
					rs.getDate("dataProxima"), rs.getInt("periodo"), rs.getDate("dataTeste"), rs.getBytes("foto"),
					rs.getString("localizacao"), rs.getString("obs"));

			u.setFotoBanco(u.getFotoBanco(u.getFoto()));

			return u;
		} catch (SQLException e) {
			log.debug("::Erro " + e);
			return u;
		}
	}

	public List<Vistoria> buscaPorUsuario(int matricula) {

		List<Vistoria> v = (List<Vistoria>) jdbc.query("select * from Vistoria where (fkUsuario = ?) ",
				this::mapeiaLinhaVistoria, matricula);

		log.debug("::Buscando vistorias pelo usuário de matricula = " + matricula);

		return v;
	}

	public List<Vistoria> buscaPorExtintor(int id) {

		List<Vistoria> v = (List<Vistoria>) jdbc.query("select * from Vistoria where (fkExtintor = ?) ",
				this::mapeiaLinhaVistoria, id);

		log.debug("::Buscando vistorias pelo usuário de matricula = " + id);

		return v;
	}

	public List<Vistoria> buscaPorVistoria(int id) {

		log.debug("::Buscando vistorias pelo usuário de matricula = " + id);

		if (id > 0) {
			List<Vistoria> v = (List<Vistoria>) jdbc.query("select * from Vistoria where (id = ?) ",
					this::mapeiaLinhaVistoria, id);
			return v;
		} else {
			List<Vistoria> v = (List<Vistoria>) jdbc.query("select * from Vistoria ", this::mapeiaLinhaVistoria);
			return v;
		}

	}

}
