package springsecurity.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerLogin {
	
	@GetMapping("/login")
    public String showLogin() {
        return "login";
    }
	
	@GetMapping("/accesRefuse")
    public String showAccesRefuse() {
        return "acces-refuse";
    }

}
