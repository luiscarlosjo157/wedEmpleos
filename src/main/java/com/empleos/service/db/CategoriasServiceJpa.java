package com.empleos.service.db;

import com.empleos.model.Categoria;
import com.empleos.model.Vacante;
import com.empleos.repository.CategoriasRepository;
import com.empleos.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class CategoriasServiceJpa  implements ICategoriaService {

    @Autowired
    private CategoriasRepository categoriasRepo;
    @Override
    public void guardar(Categoria categoria) {
        categoriasRepo.save(categoria);
    }

    @Override
    public List<Categoria> buscarTodas() {
        return  categoriasRepo.findAll();
    }

    @Override
    public Categoria buscarPorId(Integer idCategoria) {
        Optional<Categoria>  optional = categoriasRepo.findById(idCategoria);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public void eliminar(Integer idCategoria) {
        categoriasRepo.deleteById(idCategoria);
    }
}
