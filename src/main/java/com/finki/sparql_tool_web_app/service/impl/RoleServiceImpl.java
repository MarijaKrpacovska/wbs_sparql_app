package com.finki.sparql_tool_web_app.service.impl;

import java.util.Collection;
import java.util.Optional;

import com.finki.sparql_tool_web_app.model.DTO.UserRegisterDto;
import com.finki.sparql_tool_web_app.model.Role;
import com.finki.sparql_tool_web_app.repository.RoleRepository;
import com.finki.sparql_tool_web_app.service.IService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IService<Role> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Collection<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> saveOrUpdate(Role role) {
        return Optional.of(roleRepository.saveAndFlush(role));
    }

    @Override
    public Optional<Role> register(UserRegisterDto userRegisterDto) {
        return Optional.empty();
    }


    @Override
    public String deleteById(Long id) {
        JSONObject jsonObject = new JSONObject();
        try {
            roleRepository.deleteById(id);
            jsonObject.put("message", "Role deleted successfully");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}