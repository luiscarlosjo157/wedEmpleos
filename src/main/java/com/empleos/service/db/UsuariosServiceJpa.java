package com.empleos.service.db;

import com.empleos.model.Usuario;
import com.empleos.model.Vacante;
import com.empleos.repository.UsuariosRepository;
import com.empleos.service.IUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosServiceJpa implements IUsuariosService {
    @Autowired
    UsuariosRepository usuariosRepo;
    @Override
    public void guardar(Usuario usuario) {
        usuariosRepo.save(usuario);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return usuariosRepo.findAll();
    }

    @Override
    public Usuario buscarPorId(Integer idUsuario) {
        Optional<Usuario> optional = usuariosRepo.findById(idUsuario);
        if (optional.isPresent()){
            return  optional.get();
        }
        return null;
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return usuariosRepo.findByUsername(username);
    }

    @Override
    public void eliminar(Integer idUsuario) {

        usuariosRepo.deleteById(idUsuario);

    }
}
