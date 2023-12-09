package com.empleos.controller;

import com.empleos.model.Vacante;
import com.empleos.service.ICategoriaService;
import com.empleos.service.IVacanteService;
import com.empleos.utill.Utileria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {

    @Value("${empleosapp.ruta.imagenes}")
    private String ruta;

    @Autowired
    private IVacanteService serviceVacantes;

    @Autowired
    private ICategoriaService serviceCategoria;

    @GetMapping("/listVacantes")
    public String listaVacantes(Model model){
        List<Vacante> lista = serviceVacantes.buscarTodas();
        model.addAttribute("vacantes", lista);
        return "vacantes/listVacantes";
    }

    @GetMapping(value = "/indexPaginate")
    public String mostrarPaginado(Model model, Pageable page){
        Page<Vacante> lista = serviceVacantes.buscarTodas(page);
        model.addAttribute("vacantes", lista);
        return "vacantes/listVacantes";
    }

    @GetMapping("/create")
    public String crear(Vacante vacante, Model model) {

        return "vacantes/formVacante";
    }

    @PostMapping("/save")
    public String guardar(Vacante vacante, BindingResult result, RedirectAttributes attributes,
                          @RequestParam("archivoImagen") MultipartFile multiPart) {

        if (result.hasErrors()) {
            for (ObjectError error: result.getAllErrors()){
                System.out.println("Ocurrio un error: "+ error.getDefaultMessage());
            }
            return "vacantes/formVacante";
        }

        if (!multiPart.isEmpty()) {
            //String ruta = "/empleos/img-vacantes/"; // Linux/MAC
            //String ruta = "c:/empleos/img-vacantes/"; // Windows
            String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
            if (nombreImagen != null){ // La imagen si se subio
                // Procesamos la variable nombreImagen
                vacante.setImagen(nombreImagen);
            }
        }

        serviceVacantes.guardar(vacante);
        attributes.addFlashAttribute("msg", "Registro Guardado");
        System.out.println("Vacante: " + vacante);
        return "redirect:/vacantes/listVacantes";
    }
    /*
     @PostMapping("/save")
    public String guardar(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion,
                          @RequestParam("estatus") String estatus, @RequestParam("fecha") String fecha,
                          @RequestParam("destacado") int destacado, @RequestParam("salario") double salario,
                          @RequestParam("detalles") String detalles) {
        System.out.println("Nombre Vacante: "+ nombre);
        System.out.println("Descripcion: "+ descripcion);
        System.out.println("estatus: "+ estatus);
        System.out.println("Fecla puclicacion: "+ fecha);
        System.out.println("Destacado: "+ destacado);
        System.out.println("Salario Ofrecido: "+ salario);
        System.out.println("Detalles: "+ detalles);
        return "vacantes/listVacantes";
    }
     */

    @GetMapping("edit/{id}")
    public String editar(@PathVariable("id") int idVacante, Model model){
        Vacante vacante = serviceVacantes.buscarPorId(idVacante);
        model.addAttribute("vacante", vacante);
        return "vacantes/formVacante";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id") int idVacante, Model model, RedirectAttributes attributes) {
        System.out.println("Borrando vacante con id: " + idVacante);
        attributes.addFlashAttribute("msg", "La vacante fue eliminada");
        serviceVacantes.eliminar(idVacante);

        return "redirect:/vacantes/listVacantes";
    }

    @GetMapping("/view/{id}")
    public String verDetalle(@PathVariable("id") int idVacante, Model model) {
        Vacante vacante = serviceVacantes.buscarPorId(idVacante);
        System.out.println("vacante: " + vacante);
        model.addAttribute("vacante", vacante);
        return "detalle";
    }

    @ModelAttribute
    public void setGenericos(Model model){

        model.addAttribute("categoria", serviceCategoria.buscarTodas());
    }

    @InitBinder
    public void  initBinder(WebDataBinder webDataBinder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

}
