package com.empleos.service;

import com.empleos.model.Vacante;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IVacanteService {
    List<Vacante> buscarTodas();

    Vacante buscarPorId(Integer idVacante);

    void  guardar(Vacante vacante);

    List<Vacante> buscarDestacadas();

    void eliminar(Integer idVacante);

    List<Vacante> buscarByExample(Example<Vacante> example);

    Page<Vacante> buscarTodas(Pageable page);

}
