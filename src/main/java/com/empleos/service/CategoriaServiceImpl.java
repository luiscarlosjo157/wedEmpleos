package com.empleos.service;

import com.empleos.model.Categoria;
import com.empleos.model.Vacante;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CategoriaServiceImpl implements ICategoriaService{
    private List<Categoria> lista = null;

    public CategoriaServiceImpl() {
        lista = new LinkedList<Categoria>();
        Categoria categoria1 = new Categoria();
        categoria1.setId(1);
        categoria1.setNombre("ventas");
        categoria1.setDescripcion("personal de vetas");

        Categoria categoria2 = new Categoria();
        categoria2.setId(2);
        categoria2.setNombre("construcion");
        categoria2.setDescripcion("personal de costruccion");

        Categoria categoria3 = new Categoria();
        categoria3.setId(3);
        categoria3.setNombre("informatica");
        categoria3.setDescripcion("personal de informatica");

        lista.add(categoria1);
        lista.add(categoria2);
        lista.add(categoria3);
    }
    @Override
    public void guardar(Categoria categoria) {
        lista.add(categoria);
    }

    @Override
    public List<Categoria> buscarTodas() {
        return lista;
    }

    @Override
    public Categoria buscarPorId(Integer idCategoria) {
        for (Categoria cat: lista){
            if (cat.getId() == idCategoria){
                return cat;
            }
        }
        return null;
    }

    @Override
    public void eliminar(Integer idCategoria) {

    }
}
