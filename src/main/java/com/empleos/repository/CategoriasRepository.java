package com.empleos.repository;

import com.empleos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

//public interface ICategoriasRepository extends CrudRepository<Categoria, Integer> { }
public interface CategoriasRepository extends JpaRepository<Categoria, Integer> { }
