package com.raf.nwpdomaci3.controllers;

import com.raf.nwpdomaci3.model.Permisija;
import com.raf.nwpdomaci3.model.User;
import com.raf.nwpdomaci3.services.UserService;
import com.raf.nwpdomaci3.utils.JwtUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers(){
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permisija.can_read_users)){
            return ResponseEntity.ok(userService.findAll());
        }else{
            return ResponseEntity.status(403).body("Nemate ovlascenja za citanje korisnika");
        }
    }
    @GetMapping(value = "/single/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@PathVariable String email){
        try{
            return ResponseEntity.ok(userService.dohvatiPoEmailu(email));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Nema takvog korisnika");
        }
    }
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user){
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permisija.can_create_users)){
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
            return ResponseEntity.ok(userService.save(user));
        }else{
            return ResponseEntity.status(403).body("Nemate ovlascenja za pravljenje korisnika");
        }

    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody User user){
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permisija.can_update_users)){
            return ResponseEntity.ok(userService.save(user));
        }else{
            return ResponseEntity.status(403).body("Nemate ovlascenja za promenu korisnika");
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permisija.can_delete_users)){
            Optional<User> optionalUser = userService.findById(id);
            if(optionalUser.isPresent()) {

                userService.deleteById(id);

                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.status(403).body("Nemate ovlascenja za brisanje korisnika");
        }
    }

    @GetMapping(value = "/permisije")
    public List<Permisija> permisije(){
        return (List<Permisija>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

}
