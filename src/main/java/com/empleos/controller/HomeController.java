package com.empleos.controller;
import com.empleos.model.Perfil;
import com.empleos.model.Usuario;
import com.empleos.model.Vacante;
import com.empleos.service.ICategoriaService;
import com.empleos.service.IUsuariosService;
import com.empleos.service.IVacanteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private IVacanteService serviceVacantes;

    @Autowired
    private IUsuariosService serviceUsuarios;

    @Autowired
    private ICategoriaService serviceCategorias;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/")
    public String mostrarHome(Model model){
        return "home";
    }

    @GetMapping("/index")
    public String mostrarIndex(Authentication auth, HttpSession session){
        String username = auth.getName();
        System.out.println("Nombre del usuario: " + username);

        for (GrantedAuthority rol: auth.getAuthorities()){
            System.out.println("ROL: " + rol.getAuthority());
        }
        if (session.getAttribute("usuario") == null) {
            Usuario usuario = serviceUsuarios.buscarPorUsername(username);
            usuario.setPassword(null);
            System.out.println("Usuario: " + usuario);
            session.setAttribute("usuario", usuario);
        }
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String registrarse(Usuario usuario) {
        return "formRegistro";
    }

    @PostMapping("/signup")
    public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {
        String pwdPlano = usuario.getPassword();
        String pwdEncritado = passwordEncoder.encode(pwdPlano);
        usuario.setPassword(pwdEncritado);

        usuario.setEstatus(1); // Activado por defecto
        usuario.setFechaRegistro(new Date()); // Fecha de Registro, la fecha actual del servidor

        // Creamos el Perfil que le asignaremos al usuario nuevo
        Perfil perfil = new Perfil();
        perfil.setId(5); // Perfil USUARIO
        usuario.agregar(perfil);

        /**
         * Guardamos el usuario en la base de datos. El Perfil se guarda automaticamente
         */
        serviceUsuarios.guardar(usuario);
        attributes.addFlashAttribute("msg", "El registro fue guardado correctamente!");

        return "redirect:/";
    }

    @GetMapping("/bcrypt/{texto}")
    @ResponseBody
    public String encriptar(@PathVariable("texto") String texto){
        return texto + "Encriptado en Bcrypt: " + passwordEncoder.encode(texto);
    }


    @GetMapping("/search")
    public String buscar(@ModelAttribute("search") Vacante vacante, Model model) {
        System.out.println("Buscando por : " + vacante);

        ExampleMatcher matcher = ExampleMatcher.
                // where descripcion like '%?%'
          matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<Vacante> example = Example.of(vacante,matcher);
        List<Vacante> lista = serviceVacantes.buscarByExample(example);
        model.addAttribute("vacantes", lista);
        return "home";
    }

    @GetMapping("/login")
    public  String mostrarLogin(){
        return "formLogin";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        SecurityContextLogoutHandler logoutHandler =
                new SecurityContextLogoutHandler();

        logoutHandler.logout(request, null, null);
        return "redirect:/login";
    }

    @InitBinder
    public  void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    @ModelAttribute
    public void setGenericos(Model model){
        Vacante vacanteSearch = new Vacante();
        vacanteSearch.reset();
        model.addAttribute("vacantes", serviceVacantes.buscarDestacadas());
        model.addAttribute("categorias",  serviceCategorias.buscarTodas());
        model.addAttribute("search",vacanteSearch);
    }



}
