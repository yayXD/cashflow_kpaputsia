package world.ucode.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import world.ucode.domain.Registration;
//import world.ucode.repos.RegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import world.ucode.service.RegistrationService;


@Controller
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/registration")
    public String main() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(Registration registration, Model model) {
        if (registration.getUsername() != null && registration.getPassword() != null) {
            if (registrationService.addUser(registration)) {
                model.addAttribute("message", "Вы зарегистрированы");
            } else
                model.addAttribute("message", "Этот логин уже существует");
        } else
            model.addAttribute("message", "Заполните все поля формы");
        return "registration";
    }
}
