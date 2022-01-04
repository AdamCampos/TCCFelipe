package com.main.modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class ExtintorRepositoryImpl implements CrudRepository<Extintor, String> {

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
	public void deleteById(String entity) {

	}

	@Override
	public void delete(Extintor entity) {
		jdbc.update("delete from extintor where id = ?", entity.getId());
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
		return jdbc.query("select * from Extintor", this::mapeiaLinhaExtintor);
	}

	public Extintor findOne(String id) {

		Extintor u = null;
		try {
			log.info("::Pesquisando por id " + id);
			u = jdbc.queryForObject(
					"select id, classe, agente, dataCompra, dataTeste, volume, foto from Extintor where (id = ?) ",
					this::mapeiaLinhaExtintor, id);
		} catch (Exception e) {
			log.info("::Erro ao pesquisar extintor " + e);
		}

		return u;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Extintor save(Extintor extintor) {

		try {
			jdbc.update(
					"insert into Extintor (classe, agente, dataCompra, dataTeste, volume, foto) values (?, ?, ?, ?, ? ,?)",
					extintor.getClasse(), extintor.getAgente(), extintor.getDataCompra(), extintor.getDataTeste(),
					extintor.getVolume(), extintor.getFoto());
		} catch (Exception e) {
			log.debug("::Erro ao inserir extintor " + e);
			try {
				jdbc.update(
						"update Extintor set classe=?, agente=?, dataCompra=?, dataTeste=?, volume=?, foto=?  where id=?",
						extintor.getClasse(), extintor.getAgente(), extintor.getDataCompra(), extintor.getDataTeste(),
						extintor.getVolume(), extintor.getFoto(), extintor.getId());
			} catch (Exception sqlE) {
				log.debug("::Erro ao atualizar extintor " + sqlE);
			}
		}
		return extintor;
	}

	public List<Extintor> buscarId(int id) {

		log.info("Pesquisando id " + id);
		ArrayList<Extintor> lista = new ArrayList<Extintor>();
		try {
			lista = (ArrayList<Extintor>) jdbc.query(
					"select id, classe, agente, dataCompra, c volume, foto from Extintor where id=?",
					this::mapeiaLinhaExtintor, id);

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
					"select id, classe, agente, dataCompra, dataTeste, volume, foto from Extintor where agente like ?",
					this::mapeiaLinhaExtintor, agente);
		} catch (Exception e) {
			log.debug("ERRO 2::" + e);
		}

		return lista;
	}

	public List<Extintor> retornaItensAgentes() {
		List<Extintor> lista = jdbc.query("select agente from extintor group by agente", this::mapeiaLinhaExtintor);

		return lista;
	}

	private Extintor mapeiaLinhaExtintor(ResultSet rs, int rowNum) {

		Extintor u = new Extintor();

		try {
			u = new Extintor(rs.getInt("id"), rs.getString("classe"), rs.getString("agente"),
					rs.getString("dataCompra"), rs.getString("dataTeste"), rs.getString("volume"),
					rs.getString("foto"));
			log.debug("::" + u);
			return u;
		} catch (SQLException e) {
			u = new Extintor();
			try {
				u.setId(0);
				u.setAgente(rs.getString("agente"));
			} catch (SQLException e1) {
				e1.printStackTrace();
				log.debug("::Erro ao instanciar extintor pelo resultSet anônimo " + e);
			}
			log.debug("::Erro ao instanciar extintor pelo resultSet " + e);
			e.printStackTrace();
			return u;
		}
	}
}