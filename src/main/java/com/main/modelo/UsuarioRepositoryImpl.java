package com.main.modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

	@Autowired
	public UsuarioRepositoryImpl(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	private SimpleJdbcInsert orderInserter;
	private SimpleJdbcInsert orderTacoInserter;
	private ObjectMapper objectMapper;

	private JdbcTemplate jdbc;

//	Trecho abaixo é o mesmo método mas sem usar a função lambda
//	@Override
//	public Ingredient findOne(String id) {
//	 return jdbc.queryForObject(
//	 "select id, name, type from Ingredient where id=?",
//	 new RowMapper<Ingredient>() {
//	 public Ingredient mapRow(ResultSet rs, int rowNum)
//	 throws SQLException {
//	 return new Ingredient(
//	 rs.getString("id"),
//	 rs.getString("name"),
//	 Ingredient.Type.valueOf(rs.getString("type")));
//	 };
//	 }, id);
//	}

	@Override
	public Iterable<Usuario> findAll() {
		return jdbc.query("select matricula, nome from Usuario", this::mapeiaLinhaUsuario);
	}

	@Override
	public Usuario findOne(String id) {
		return jdbc.queryForObject("select matricula, nome from Usuario where matricula=?", this::mapeiaLinhaUsuario, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario save(Usuario usuario) {
		jdbc.update("insert into Usuario (matricula, nome) values (?, ?, ?)", usuario.getMatricula(),
				usuario.getNome());
		return usuario;
	}

	private Usuario mapeiaLinhaUsuario(ResultSet rs, int rowNum) throws SQLException {
		return new Usuario(rs.getInt("matricula"), rs.getString("nome"));
	}

	@Override
	public <S extends Usuario> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Usuario> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Usuario> findAllById(Iterable<String> ids) {
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
	public void delete(Usuario entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends Usuario> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

}
