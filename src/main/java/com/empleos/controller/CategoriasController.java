package com.empleos.controller;

import com.empleos.model.Categoria;
import com.empleos.model.Vacante;
import com.empleos.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.PortUnreachableException;
import java.util.List;

@Controller
@RequestMapping(value = "/categorias")
public class CategoriasController {
    @Autowired
    private ICategoriaService categoriaService;



    @RequestMapping(value="/index", method = RequestMethod.GET)
    public String mostrarIndex(Model model){
        List<Categoria> lista = categoriaService.buscarTodas();
        model.addAttribute("categorias", lista);
        return "categorias/listCategorias";
    }


    @RequestMapping(value="/create", method = RequestMethod.GET)
    public String crear(Categoria categoria){
        return "categorias/formCategoria";
    }
    @RequestMapping(value="/save", method = RequestMethod.POST)
    public String guardar(Categoria categoria, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return "categorias/formCategoria";
        }
        categoriaService.guardar(categoria);
        redirectAttributes.addFlashAttribute("mensaje", "Categoria guardad con exito");

        return "redirect:/categorias/index";
    }


    @GetMapping("edit/{id}")
    public String editar(@PathVariable("id") int idCategoria, Model model){
        Categoria categoria = categoriaService.buscarPorId(idCategoria);
        model.addAttribute("categoria", categoria);
        return "categorias/formCategoria";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id") int idCategoria, Model model, RedirectAttributes attributes){
        System.out.println("Borrando vacante con id: " + idCategoria);
        attributes.addFlashAttribute("msg", "La vacante fue eliminada");
        categoriaService.eliminar(idCategoria);

        return "redirect:/categorias/index";

    }
}
