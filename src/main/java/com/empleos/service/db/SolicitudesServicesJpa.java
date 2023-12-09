package com.empleos.service.db;

import com.empleos.model.Solicitud;
import com.empleos.repository.SolicitudesRepository;
import com.empleos.service.ISolicitudesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitudesServicesJpa implements ISolicitudesServices {

    @Autowired
    private SolicitudesRepository solicitudesRepo;
    @Override
    public void guardar(Solicitud solicitud) {
        solicitudesRepo.save(solicitud);
    }

    @Override
    public void eliminar(Integer idSolicitud) {
        solicitudesRepo.deleteById(idSolicitud);
    }

    @Override
    public List<Solicitud> buscarTodas() {
        return solicitudesRepo.findAll();
    }

    @Override
    public Solicitud buscarPorId(Integer idSolicitud) {
        Optional<Solicitud> optional = solicitudesRepo.findById(idSolicitud);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public Page<Solicitud> buscarTodas(Pageable page) {
        return solicitudesRepo.findAll(page);
    }
}
