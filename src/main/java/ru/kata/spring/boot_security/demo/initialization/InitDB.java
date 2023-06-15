package ru.kata.spring.boot_security.demo.initialization;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class InitDB {

    private final RoleService roleService;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public InitDB(RoleService roleService, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleService = roleService;
        this.userService = userService;

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @PostConstruct
    @Transactional
    public void fillDb() {

        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        User admin = new User("admin", "Антон", "Шорин", bCryptPasswordEncoder.encode("1"));
        admin.addRole(roleService.add(roleAdmin));
        userService.add(admin);

        User user = new User("user", "Ольга", "Шорина", bCryptPasswordEncoder.encode("2"));
        user.addRole(roleService.add(roleUser));
        userService.add(user);

        User userAdmin = new User("superadmin", "Кирилл", "Шорин", bCryptPasswordEncoder.encode("3"));
        userAdmin.addRole(roleService.add(roleUser));
        userAdmin.addRole(roleService.add(roleAdmin));
        userService.add(userAdmin);
    }
}
