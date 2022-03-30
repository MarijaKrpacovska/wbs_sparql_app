package com.finki.sparql_tool_web_app.web;

import com.finki.sparql_tool_web_app.model.DTO.ResultDto;
import com.finki.sparql_tool_web_app.model.Query;
import com.finki.sparql_tool_web_app.model.Result;
import com.finki.sparql_tool_web_app.service.ResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/results")
public class ResultController {
    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    public List<Result> findAll(){
        return this.resultService.findAll();
    }

    @GetMapping("/result-for-query/{id}")
    public ResponseEntity<Result> findResultByQueryId(@PathVariable Long id){
        return this.resultService.findByQueryId(id)
                .map(query -> ResponseEntity.ok().body(query))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Result> findById(@PathVariable Long id){
        return this.resultService.findById(id)
                .map(query -> ResponseEntity.ok().body(query))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /*@PostMapping("/add")
    public ResponseEntity<Result> save(@RequestBody ResultDto resultDto){
        return this.resultService.save(resultDto)
                .map(result -> ResponseEntity.ok().body(result))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }*/

    /*@PostMapping("/edit/{id}")
    public ResponseEntity<Result> save(@PathVariable Long id, @RequestBody ResultDto resultDto){
        return this.resultService.edit(id,resultDto)
                .map(result -> ResponseEntity.ok().body(result))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }*/

    /*@DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        this.resultService.deleteById(id);
        if(this.resultService.findById(id).isEmpty())
            return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }*/
}