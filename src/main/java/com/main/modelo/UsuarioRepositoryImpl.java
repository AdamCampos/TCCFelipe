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
public class UsuarioRepositoryImpl implements CrudRepository<Usuario, String> {

	private JdbcTemplate jdbc;

	@Autowired
	public UsuarioRepositoryImpl(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Iterable<Usuario> findAll() {
		return jdbc.query("select * from Usuario", this::mapeiaLinhaUsuario);
	}

	public Usuario findOne(String id) {

		Usuario u = null;
		try {
			log.info("::Pesquisando por matrícula " + id);
			u = jdbc.queryForObject("select * from Usuario where (matricula = ?) ", this::mapeiaLinhaUsuario, id);
		} catch (Exception e) {
			log.info("::Pesquisando por nome " + id);
			u = jdbc.queryForObject("select * from Usuario where (nome like ?) ", this::mapeiaLinhaUsuario, id);
		}

		return u;
	}

	public Usuario findOne(String nomeOuSenha, int senha) {

		Usuario u = null;
		try {
			log.info("::Pesquisando por matrícula " + nomeOuSenha);
			u = jdbc.queryForObject("select * from Usuario where (matricula = ? and senha = ?) ",
					this::mapeiaLinhaUsuario, nomeOuSenha, senha);
		} catch (Exception e) {
			log.info("::Pesquisando por nome " + nomeOuSenha);
			u = jdbc.queryForObject("select * from Usuario where (nome like ? and senha = ?) ",
					this::mapeiaLinhaUsuario, nomeOuSenha, senha);
		}

		return u;
	}

	public List<Usuario> buscar(String nome, int matricula) {

		log.info("::Pesquisando nome " + nome + " matrícula " + matricula);

		ArrayList<Usuario> lista = (ArrayList<Usuario>) jdbc.query("select * from Usuario", this::mapeiaLinhaUsuario);

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

		jdbc.update("insert into Usuario (nome, senha, foto, img) values (?, ?, ?, ?)", usuario.getNome(),
				usuario.getSenha(), usuario.getFoto(), usuario.getImg());

		return usuario;
	}

	public Usuario atualizar(Usuario usuario) {

		jdbc.update("update Usuario set nome=?, senha=?, foto=?, img=?  where matricula=?", usuario.getNome(),
				usuario.getSenha(), usuario.getFoto(), usuario.getImg(), usuario.getMatricula());

		return usuario;
	}

	private Usuario mapeiaLinhaUsuario(ResultSet rs, int rowNum) {

		Usuario u = new Usuario();

		try {
			u = new Usuario(rs.getInt("matricula"), rs.getString("nome"), rs.getInt("senha"), rs.getString("foto"),
					rs.getBytes("img"));

			u.setFotoBanco(u.getFotoBanco(u.getImg()));

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
