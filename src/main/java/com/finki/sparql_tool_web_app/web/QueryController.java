package com.finki.sparql_tool_web_app.web;

import com.finki.sparql_tool_web_app.model.DTO.QueryDto;
import com.finki.sparql_tool_web_app.model.Query;
import com.finki.sparql_tool_web_app.service.QueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/queries")
public class QueryController {
    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    public List<Query> findAll(){
        return this.queryService.findAll();
    }

    @GetMapping("/myQueries/{id}")
    public List<Query> findAllQueriesByUser(@PathVariable Long id){
        return this.queryService.findAllForUser(id);
    }

    @GetMapping("/myQueries")
    public List<Query> findAllQueriesByUserEmail(@RequestParam String userEmail){
        return this.queryService.findAllForUser(userEmail);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Query> findQueryById(@PathVariable Long id){
        return this.queryService.findById(id)
                .map(query -> ResponseEntity.ok().body(query))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

   /* @PostMapping("/add")
    public ResponseEntity<Query> save(@RequestBody QueryDto queryDto){
        return this.queryService.save(queryDto)
                .map(query -> ResponseEntity.ok().body(query))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }*/
   @PostMapping("/add")
   public List<String> save(@RequestBody QueryDto queryDto){
       return this.queryService.save(queryDto);
   }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Query> save(@PathVariable Long id, @RequestBody QueryDto queryDto){
        return this.queryService.edit(id,queryDto)
                .map(query -> ResponseEntity.ok().body(query))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        this.queryService.deleteById(id);
        if(this.queryService.findById(id).isEmpty())
            return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }
}
