package com.empleos.service;

import com.empleos.model.Vacante;
import com.empleos.repository.VacantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Service
public class VacantesServiceImpl implements IVacanteService{
    @Autowired
    private VacantesRepository vacantesRepo;
    private List<Vacante> lista = null;
    public VacantesServiceImpl() {
        SimpleDateFormat sdf =new  SimpleDateFormat("dd-MM-yyyy");
        lista = new LinkedList<Vacante>();
        try {
            Vacante vacante1 = new Vacante();
            vacante1.setId(1);
            vacante1.setNombre("Ingeniero Civil");
            vacante1.setFecha(sdf.parse("20-06-2023"));
            vacante1.setSalario(2500000.0);
            vacante1.setDescripcion("Debe de tener experiensia de un año en vias");
            vacante1.setDestacado(1);
            vacante1.setImagen("empresa1.jpg");

            Vacante vacante2 = new Vacante();
            vacante2.setId(2);
            vacante2.setNombre("Contador Publico");
            vacante2.setFecha(sdf.parse("20-06-2023"));
            vacante2.setSalario(2500000.0);
            vacante2.setDescripcion("Debe de tener experiensia en empresas Publicas");
            vacante2.setDestacado(0);
            vacante2.setImagen("empresa2.jpg");

            Vacante vacante3 = new Vacante();
            vacante3.setId(3);
            vacante3.setNombre("Ingeniero Sistemas");
            vacante3.setFecha(sdf.parse("20-06-2023"));
            vacante3.setSalario(2500000.0);
            vacante3.setDescripcion("Debe de tener experiens en telecomunicaciones");
            vacante3.setDestacado(1);

            Vacante vacante4 = new Vacante();
            vacante4.setId(4);
            vacante4.setNombre("Ingeniero Ambiental");
            vacante4.setFecha(sdf.parse("20-06-2023"));
            vacante4.setSalario(2500000.0);
            vacante4.setDescripcion("Debe de tener experiensia en alcantarillados");
            vacante4.setDestacado(0);
            vacante4.setImagen("empresa3.jpg");

            lista.add(vacante1);
            lista.add(vacante2);
            lista.add(vacante3);
            lista.add(vacante4);
        } catch (ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @Override
    public List<Vacante> buscarTodas() {
        return lista;
    }

    @Override
    public Vacante buscarPorId(Integer idVacante) {
        for (Vacante v: lista){
            if (v.getId() == idVacante){
                return v;
            }
        }
        return null;
    }

    @Override
    public void guardar(Vacante vacante) {
        lista.add(vacante);
    }

    @Override
    public List<Vacante> buscarDestacadas() {
        return null;
    }

    @Override
    public void eliminar(Integer idVacante) {

    }

    @Override
    public List<Vacante> buscarByExample(Example<Vacante> example) {
        return vacantesRepo.findAll(example);
    }

    @Override
    public Page<Vacante> buscarTodas(Pageable page) {
        return null;
    }


}
