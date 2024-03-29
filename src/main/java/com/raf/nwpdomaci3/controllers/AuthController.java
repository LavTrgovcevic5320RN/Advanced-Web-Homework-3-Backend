package com.raf.nwpdomaci3.controllers;

import com.raf.nwpdomaci3.model.User;
import com.raf.nwpdomaci3.requests.LoginRequest;
import com.raf.nwpdomaci3.responses.LoginResponse;
import com.raf.nwpdomaci3.services.UserService;
import com.raf.nwpdomaci3.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            // UsernamePasswordAUth nije null, puca pri authenticate metodi.
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (Exception   e){
            System.out.println(loginRequest.getEmail() + " " + loginRequest.getPassword());
            e.printStackTrace();
            return ResponseEntity.status(401).body("Pogresno korisnicko ime ili lozinka");
        }

        User u = userService.dohvatiPoEmailu(loginRequest.getEmail());
        return ResponseEntity.ok(new LoginResponse(jwtUtil.generateToken(loginRequest.getEmail(), u.getPermisije())));
    }
}
