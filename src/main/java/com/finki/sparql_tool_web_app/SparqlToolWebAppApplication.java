package com.finki.sparql_tool_web_app;

import com.finki.sparql_tool_web_app.model.Role;
import com.finki.sparql_tool_web_app.model.User;
import com.finki.sparql_tool_web_app.service.IService;
import com.finki.sparql_tool_web_app.service.impl.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SparqlToolWebAppApplication implements CommandLineRunner {

    @Autowired
    private IService<User> userService;
    @Autowired
    private IService<Role> roleService;
    public static void main(String[] args) {
        SpringApplication.run(SparqlToolWebAppApplication.class, args);
    }

    /*@Autowired
    private EmailSenderService senderService;*/

    @Override
    public void run(String... args) throws Exception {
        roleService.saveOrUpdate(new Role(1L, "admin"));
        roleService.saveOrUpdate(new Role(2L, "user"));

        if(this.userService.findAll().isEmpty()) {
            User user1 = new User();
            user1.setEmail("test@user.com");
            user1.setName("Test User");
            user1.setMobile("9787456545");
            user1.setRole(roleService.findById(2L).get());
            user1.setPassword(new BCryptPasswordEncoder().encode("testuser"));
            userService.saveOrUpdate(user1);

            User user2 = new User();
            user2.setEmail("test@admin.com");
            user2.setName("Test Admin");
            user2.setMobile("9787456545");
            user2.setRole(roleService.findById(1L).get());
            user2.setPassword(new BCryptPasswordEncoder().encode("testadmin"));
            userService.saveOrUpdate(user2);
        }
    }

    /*@EventListener(ApplicationReadyEvent.class)
    public void sendEmail(){
        senderService.sendEmail("sparqltool@gmail.com","test","test");
    }*/
}
