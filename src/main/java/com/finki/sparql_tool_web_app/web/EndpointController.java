package com.finki.sparql_tool_web_app.web;

import com.finki.sparql_tool_web_app.model.DTO.EndpointDto;
import com.finki.sparql_tool_web_app.model.Endpoint;
import com.finki.sparql_tool_web_app.service.EndpointService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/endpoints")
public class EndpointController {
    private final EndpointService endpointService;

    public EndpointController(EndpointService endpointService) {
        this.endpointService = endpointService;
    }

    @GetMapping
    public List<Endpoint> findAll(){
        return this.endpointService.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Endpoint> save(@RequestBody EndpointDto endpointDto){
        return this.endpointService.save(endpointDto)
                .map(endpoint -> ResponseEntity.ok().body(endpoint))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Endpoint> save(@PathVariable Long id, @RequestBody EndpointDto endpointDto){
        return this.endpointService.edit(id,endpointDto)
                .map(endpoint -> ResponseEntity.ok().body(endpoint))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        this.endpointService.deleteById(id);
        if(this.endpointService.findById(id).isEmpty())
            return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }
}
