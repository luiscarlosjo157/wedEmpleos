package com.empleos.repository;

import com.empleos.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer> {
}
