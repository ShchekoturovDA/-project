package sber.TODO.Project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class FrontDoorController {

    @RequestMapping("/")
    public String justGet(){
        return "index";
    }

    @RequestMapping("/reg")
    public String regIn(){
        return "reg";
    }

    @RequestMapping("/sign_in")
    public String signIn(){
        return "sign_in";
    }


}
