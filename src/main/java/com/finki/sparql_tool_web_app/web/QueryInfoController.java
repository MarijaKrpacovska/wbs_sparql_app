package com.finki.sparql_tool_web_app.web;

import com.finki.sparql_tool_web_app.model.DTO.QueryDto;
import com.finki.sparql_tool_web_app.model.QueryInfo;
import com.finki.sparql_tool_web_app.service.QueryInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/queries")
public class QueryInfoController {
    private final QueryInfoService queryInfoService;

    public QueryInfoController(QueryInfoService queryInfoService) {
        this.queryInfoService = queryInfoService;
    }

    @GetMapping
    public List<QueryInfo> findAll(){
        return this.queryInfoService.findAll();
    }

    @GetMapping("/myQueries/{id}")
    public List<QueryInfo> findAllQueriesByUser(@PathVariable Long id){
        return this.queryInfoService.findAllForUser(id);
    }

    @GetMapping("/myQueries")
    public List<QueryInfo> findAllQueriesByUserEmail(@RequestParam String userEmail){
        return this.queryInfoService.findAllForUser(userEmail);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<QueryInfo> findQueryById(@PathVariable Long id){
        return this.queryInfoService.findById(id)
                .map(query -> ResponseEntity.ok().body(query))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

   /* @PostMapping("/add")
    public ResponseEntity<Query> save(@RequestBody QueryDto queryDto){
        return this.queryService.save(queryDto)
                .map(query -> ResponseEntity.ok().body(query))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }*/
   @PostMapping("/add-old")
   public List<String> saveOld(@RequestBody QueryDto queryDto){
       return this.queryInfoService.saveOld(queryDto);
   }

    @PostMapping("/add")
    public ResponseEntity<QueryInfo> save(@RequestBody QueryDto queryDto){
        return this.queryInfoService.save(queryDto)
                .map(query -> ResponseEntity.ok().body(query))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<QueryInfo> save(@PathVariable Long id, @RequestBody QueryDto queryDto){
        return this.queryInfoService.edit(id,queryDto)
                .map(query -> ResponseEntity.ok().body(query))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        this.queryInfoService.deleteById(id);
        if(this.queryInfoService.findById(id).isEmpty())
            return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }
}
