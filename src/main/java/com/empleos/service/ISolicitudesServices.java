package com.empleos.service;

import com.empleos.model.Solicitud;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISolicitudesServices {

    void guardar(Solicitud solicitud);
    void eliminar(Integer idSolicitud);
    List<Solicitud> buscarTodas();

    Solicitud buscarPorId(Integer idSolicitud);
    Page<Solicitud> buscarTodas(Pageable page);
}
