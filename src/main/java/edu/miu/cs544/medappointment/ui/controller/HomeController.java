package edu.miu.cs544.medappointment.ui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String welcome(){
        return "Welcome to CS544 Final Project!";
    }
}
