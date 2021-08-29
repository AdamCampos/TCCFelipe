package com.main.modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class UsuarioRepositoryImpl implements UsuarioRepository {

	private JdbcTemplate jdbc;

	@Autowired
	public UsuarioRepositoryImpl(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Iterable<Usuario> findAll() {
		return jdbc.query("select matricula, nome, senha from Usuario", this::mapeiaLinhaUsuario);
	}

	@Override
	public Usuario findOne(String id) {
		return jdbc.queryForObject("select matricula, nome, senha from Usuario where matricula=?",
				this::mapeiaLinhaUsuario, id);
	}

	public List<Usuario> buscar(String nome, int matricula) {

		log.info("Pesquisando nome " + nome + " matrícula " + matricula);

		ArrayList<Usuario> lista = (ArrayList<Usuario>) jdbc.query("select matricula, nome, senha from Usuario",
				this::mapeiaLinhaUsuario);

		ArrayList<Usuario> listaFinal = new ArrayList<Usuario>();
		listaFinal.clear();
		for (Usuario u : lista) {
			if (u.getNome().toLowerCase().trim().contains(nome.toLowerCase()) || u.getMatricula() == matricula) {
				listaFinal.add(u);
			}
		}
		return listaFinal;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario save(Usuario usuario) {

		try {
			jdbc.update("insert into Usuario (matricula, nome, senha) values (?, ?, ?)", usuario.getMatricula(),
					usuario.getNome(), usuario.getSenha());
		} catch (Exception e) {
			jdbc.update("update Usuario set nome=?, senha=? where matricula=?", usuario.getNome(), usuario.getSenha(),
					usuario.getMatricula());
		}
		return usuario;
	}

	private Usuario mapeiaLinhaUsuario(ResultSet rs, int rowNum) {

		Usuario u = new Usuario();

		try {
			u = new Usuario(rs.getInt("matricula"), rs.getString("nome"), rs.getInt("senha"));
			return u;
		} catch (SQLException e) {
			log.error("Erro: " + e);
			e.printStackTrace();
			return u;
		}
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
		jdbc.update("delete from usuario where matricula = ?", entity.getMatricula());
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
