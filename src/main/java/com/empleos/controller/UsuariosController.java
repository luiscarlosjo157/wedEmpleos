package com.empleos.controller;

import com.empleos.model.Usuario;
import com.empleos.service.db.UsuariosServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosServiceJpa usuariosRepo;
    @GetMapping("/listUsuarios")
    private String listar(Model model){

        List<Usuario>  lista = usuariosRepo.buscarTodos();
        model.addAttribute("usuarios", lista);

        return "usuarios/listUsuarios";
    }



}
