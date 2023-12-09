package com.empleos.controller;

import com.empleos.model.Solicitud;
import com.empleos.model.Usuario;
import com.empleos.model.Vacante;
import com.empleos.service.ISolicitudesServices;
import com.empleos.service.IUsuariosService;
import com.empleos.service.IVacanteService;
import com.empleos.utill.Utileria;
import jakarta.persistence.PostRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {

    @Value("${empleosapp.ruta.cv}")
    private String ruta;

    @Autowired
    private IVacanteService serviceVacantes;
    @Autowired
    private IUsuariosService serviceUsuario;
    @Autowired
    private ISolicitudesServices solicitudesServices;

    @GetMapping("/indexPaginate")
    public String mostrarIndexPaginado(Model model, Pageable page){
        Page<Solicitud> lista = solicitudesServices.buscarTodas(page);
        model.addAttribute("solicitudes", lista);
        return "solicitudes/listSolicitudes";
    }

    @GetMapping("/create/{idVacante}")
    public String crear(Solicitud solicitud, @PathVariable("idVacante") Integer idVacante, Model model){
        Vacante vacante = serviceVacantes.buscarPorId(idVacante);
        model.addAttribute("vacante", vacante);
        System.out.println("idVacante: " + idVacante);
        return "solicitudes/formSolicitud";

    }
    @PostMapping("/save")
    public String guardar(Solicitud solicitud, BindingResult result, @RequestParam("archivoCV") MultipartFile multipar,
                          Authentication authentication, RedirectAttributes attributes){
        String username = authentication.getName();
        if (result.hasErrors()){
            System.out.println("Existen errores");
            return "solicitudes/formSolicitud";
        }

        if (! multipar.isEmpty()){
            String nombreArchivo = Utileria.guardarArchivo(multipar, ruta);
            if (nombreArchivo!=null){
                solicitud.setArchivo(nombreArchivo);
            }
        }

        Usuario usuario = serviceUsuario.buscarPorUsername(username);
        solicitud.setUsuario(usuario);
        solicitudesServices.guardar(solicitud);
        attributes.addFlashAttribute("msg", "Gracias por enviar tu hoja de vida");
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id") int idSolicitud, RedirectAttributes attributes){
        solicitudesServices.eliminar(idSolicitud);
        attributes.addFlashAttribute("msg", "La solicitud fue eliminada");
        return "redirect:/solicitudes/indexPaginate";
    }
}
