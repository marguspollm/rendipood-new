package ee.margus.rendipood.controller;

import ee.margus.rendipood.dto.LoginDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("login")
    public boolean login(@RequestBody LoginDTO loginDTO){
        return true;
    }

    @PostMapping("logout")
    public boolean logout(){
        return false;
    }
}
