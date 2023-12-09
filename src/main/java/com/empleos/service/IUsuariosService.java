package com.empleos.service;

import com.empleos.model.Usuario;
import org.hibernate.id.uuid.UuidGenerator;

import java.util.List;

public interface IUsuariosService {
    void guardar(Usuario usuario);
    List<Usuario> buscarTodos();

    Usuario buscarPorId(Integer idUsuario);

    Usuario buscarPorUsername(String username);

    void  eliminar(Integer idUsuario);
}
