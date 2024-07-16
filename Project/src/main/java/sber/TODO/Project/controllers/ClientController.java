package sber.TODO.Project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sber.TODO.Project.entities.Client;
import sber.TODO.Project.services.ClientService;

import java.net.URI;
import java.net.URISyntaxException;

@RequestMapping("/clients")
@Controller
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/add")
    public String save(@ModelAttribute("client") Client client, Model model) {
        if (!clientService.existByLogin(client)) {
            client.setPassword(passwordEncoder.encode(client.getPassword()));
            clientService.save(client);
            return "redirect:/tasks/main";
        } else {
            return "/reg";
        }
    }

    @PostMapping("/sign_in")
    public String sign(@ModelAttribute("client") Client client){
        if (clientService.existByLoginAndPasswordAndEmail(client)) {
            clientService.loadUserByUsername(client.getLogin());
            return "redirect:/tasks/main";
        } else {
            return "/sign_in";
        }
    }

/*
    @GetMapping("/{id}")
    public ResponseEntity<Client> get(@PathVariable long id){
        return ResponseEntity.of(clientService.findById(id));
    }*/
}
