package com.raf.nwpdomaci3.bootstrap;

import com.raf.nwpdomaci3.domain.entities.Role;
import com.raf.nwpdomaci3.domain.entities.RoleType;
import com.raf.nwpdomaci3.domain.entities.User;
import com.raf.nwpdomaci3.repository.RoleRepository;
import com.raf.nwpdomaci3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Bootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Bootstrap(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Role roleCreate = new Role();
        roleCreate.setRole(RoleType.CAN_CREATE);

        Role roleRead = new Role();
        roleRead.setRole(RoleType.CAN_READ);

        Role roleUpdate = new Role();
        roleUpdate.setRole(RoleType.CAN_UPDATE);

        Role roleDelete = new Role();
        roleDelete.setRole(RoleType.CAN_DELETE);

        User admin = new User();
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setEmail("admind@gmail.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(List.of(roleCreate, roleRead, roleDelete, roleUpdate));

        userRepository.save(admin);
        System.out.println("DATA LOADED!");
    }
}
