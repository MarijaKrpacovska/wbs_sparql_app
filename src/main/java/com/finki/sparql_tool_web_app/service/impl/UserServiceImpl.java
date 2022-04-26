package com.finki.sparql_tool_web_app.service.impl;

import java.util.Collection;
import java.util.Optional;

import com.finki.sparql_tool_web_app.model.DTO.UserDto;
import com.finki.sparql_tool_web_app.model.DTO.UserRegisterDto;
import com.finki.sparql_tool_web_app.model.User;
import com.finki.sparql_tool_web_app.repository.RoleRepository;
import com.finki.sparql_tool_web_app.repository.UserRepository;
import com.finki.sparql_tool_web_app.service.IService;
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


    //todo: passwords don't match
    //todo: invalid mail
    @Override
    public Optional<User> register(UserRegisterDto userDto) {
        User user = new User(userDto.getName(),
                userDto.getEmail(),userDto.getMobile(),
                passwordEncoder.encode(userDto.getPassword()),
                roleRepository.getById(userDto.getRoleId()));

        return Optional.of(userRepository.save(user));
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