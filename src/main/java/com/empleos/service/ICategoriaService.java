package com.empleos.service;

import com.empleos.model.Categoria;
import com.empleos.model.Vacante;

import java.util.List;

public interface ICategoriaService {
    void guardar(Categoria categoria);
    List<Categoria> buscarTodas();
    Categoria buscarPorId(Integer idCategoria);

    void eliminar(Integer idCategoria);
}
