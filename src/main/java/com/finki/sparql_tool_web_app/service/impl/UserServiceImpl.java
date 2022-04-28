package com.finki.sparql_tool_web_app.service.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.regex.Pattern;

import com.finki.sparql_tool_web_app.model.DTO.UserDto;
import com.finki.sparql_tool_web_app.model.DTO.UserRegisterDto;
import com.finki.sparql_tool_web_app.model.SecureToken;
import com.finki.sparql_tool_web_app.model.User;
import com.finki.sparql_tool_web_app.model.exceptions.InvalidMailAddressException;
import com.finki.sparql_tool_web_app.model.exceptions.PasswordsDontMatchException;
import com.finki.sparql_tool_web_app.repository.RoleRepository;
import com.finki.sparql_tool_web_app.repository.SecureTokenRepository;
import com.finki.sparql_tool_web_app.repository.UserRepository;
import com.finki.sparql_tool_web_app.service.IService;
import com.finki.sparql_tool_web_app.service.SecureTokenService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl implements IService<User> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSenderService senderService;

    @Autowired
    private SecureTokenService secureTokenService;

    @Autowired
    private SecureTokenRepository secureTokenRepository;

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> saveOrUpdate(User user) {
        return Optional.of(userRepository.saveAndFlush(user));
    }

    public boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public void sendRegistrationEmail(User user){
        SecureToken secureToken = secureTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenRepository.save(secureToken);

        String subj = "SPARQL Tool registration validation";

        String body = "Hi "+user.getName()+"!"+ "\r\n"+
                "to validate your registration, please enter the following code: "+"\r\n"+"\r\n"+
                secureToken.getToken()+"\r\n"+"\r\n"+
                "If you didn't request a code, please ignore this email."+"\r\n"+"\r\n"+
                "Thanks,"+"\r\n"+
                "Admin";


        senderService.sendEmail(user.getEmail(),subj,body);
    }

    //todo: invalid mail
    @Override
    public Optional<User> register(UserRegisterDto userDto) {

        if(!userDto.getPassword().equals(userDto.getRepeatPassword()))
            throw new PasswordsDontMatchException();
/*
        if(this.patternMatches(userDto.getEmail(), "^(.+)@(\\S+)$"))
            throw new InvalidMailAddressException();*/

        User user = new User(userDto.getName(),
                userDto.getEmail(),userDto.getMobile(),
                passwordEncoder.encode(userDto.getPassword()),
                roleRepository.getById(userDto.getRoleId()));

        User savedUser = userRepository.save(user);
        this.sendRegistrationEmail(savedUser);

        return Optional.of(savedUser);
    }

    @Override
    public String deleteById(Long id) {
        JSONObject jsonObject = new JSONObject();
        try {
            userRepository.deleteById(id);
            jsonObject.put("message", "User deleted successfully");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}