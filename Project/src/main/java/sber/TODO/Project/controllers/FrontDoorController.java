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
        return "index";
    }

    @RequestMapping("/reg")
    public String regIn(Model model){
        return "reg";
    }

    @RequestMapping("/sign_in")
    public String signIn(Model model){
        return "sign_in";
    }


}
