package sber.TODO.Project.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sber.TODO.Project.entities.Client;
import sber.TODO.Project.services.ClientService;;

@RequestMapping("/clients")
@Controller
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/add")
    public String save(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            if (!clientService.existByLogin(client)) {
                client.setPassword(passwordEncoder.encode(client.getPassword()));
                clientService.save(client);
                return "redirect:/tasks/main";
            } else {
                return "/reg";
            }
        } else {
            return "/for_all/reg";
        }
    }

    @PostMapping("/sign_in")
    public String sign(@ModelAttribute("client") Client client) {
        if (clientService.existByLoginAndPasswordAndEmail(client)) {
            clientService.loadUserByUsername(client.getLogin());
            return "redirect:/tasks/main";
        } else {
            return "/sign_in";
        }
    }
}
