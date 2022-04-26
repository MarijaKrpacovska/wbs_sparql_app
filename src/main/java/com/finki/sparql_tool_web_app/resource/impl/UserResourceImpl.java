package com.finki.sparql_tool_web_app.resource.impl;

import com.finki.sparql_tool_web_app.config.JwtTokenProvider;
import com.finki.sparql_tool_web_app.model.DTO.UserDto;
import com.finki.sparql_tool_web_app.model.DTO.UserRegisterDto;
import com.finki.sparql_tool_web_app.model.Endpoint;
import com.finki.sparql_tool_web_app.model.User;
import com.finki.sparql_tool_web_app.repository.UserRepository;
import com.finki.sparql_tool_web_app.service.IService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserResourceImpl {

    private static Logger log = LoggerFactory.getLogger(UserResourceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    private final IService<User> userIService;

    public UserResourceImpl(IService<User> userIService) {
        this.userIService = userIService;
    }

    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> authenticate(@RequestBody User user) {
        log.info("UserResourceImpl : authenticate");
        Authentication authentication=authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        JSONObject jsonObject = new JSONObject();
        try {
            if(authentication.isAuthenticated()){
                String email = user.getEmail();
                jsonObject.put("name",authentication.getName());
                jsonObject.put("authorities",authentication.getAuthorities());
                jsonObject.put("token", tokenProvider.createToken(email, userRepository.findByEmail(email).getRole()));
                return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
            }
        } catch (JSONException e) {
            try {
                jsonObject.put("exception", e.getMessage());
            }catch (JSONException e1) {
                e.printStackTrace();
            }
            return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.UNAUTHORIZED);
        }
        return null;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserRegisterDto userDto){
        this.userIService.register(userDto);

        return ResponseEntity.ok().build();
    }
}