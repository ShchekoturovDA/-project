package sber.TODO.Project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sber.TODO.Project.entities.Client;

@Controller
@RequestMapping("/")
public class FrontDoorController {

    @RequestMapping("/")
    public String justGet(){
        return "/for_all/index";
    }

    @RequestMapping("/reg")
    public String regIn(Model model){
        model.addAttribute("client", new Client());
        return "/for_all/reg";
    }

    @RequestMapping("/sign_in")
    public String signIn(Model model){
        model.addAttribute("client", new Client());
        return "/for_all/sign_in";
    }


}
