package edu.miu.cs544.medappointment.ui.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping
    public String welcome(){
        return "redirect:/swagger-ui.html";
    }
}
