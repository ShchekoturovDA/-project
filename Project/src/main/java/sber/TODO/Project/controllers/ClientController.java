package sber.TODO.Project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/add")
    public String save(@ModelAttribute("client") Client client, Model model) throws URISyntaxException {
        if (!clientService.existByLogin(client)) {
            model.addAttribute("lclient", clientService.save(client));
            return "/todo/main";
        } else {
            return "/reg";
        }
    }

    @PostMapping("/sign_in")
    public String sign(@ModelAttribute("client") Client client, Model model) throws URISyntaxException {
        if (clientService.exist(client)) {
//            model.addAttribute("client", clientService.findByLogin(client.getLogin()));
            return "/todo/main";
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
