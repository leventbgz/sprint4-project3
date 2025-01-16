package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {
    private Map<Integer, Kangaroo> kangaroos;

    @PostConstruct
    public void init(){
        kangaroos = new HashMap<>();
    }

    @GetMapping
    public List<Kangaroo> getKangaroos(){
        return kangaroos.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Kangaroo getKangarooById(@PathVariable("id") int id){
        if (kangaroos.containsKey(id)){
            return kangaroos.get(id);
        } else {
            throw new ZooException(HttpStatus.NOT_FOUND, "Kangaroo with given id could not been found");
        }
    }

    @PostMapping
    public Kangaroo addKangaroo(@RequestBody Kangaroo kangaroo) {
        if (kangaroo == null) {
            throw new ZooException(HttpStatus.BAD_REQUEST, "Kangaroo object cannot be null.");
        }
        if (kangaroo.getId() == null || kangaroos.containsKey(kangaroo.getId())) {
            throw new ZooException(HttpStatus.BAD_REQUEST, "Kangaroo with this ID already exists or ID is missing.");
        }

        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;
    }


    @PutMapping("/{id}")
    public Kangaroo updateKangaroo(@PathVariable("id") int id, @RequestBody Kangaroo kangaroo){
        if (!kangaroos.containsKey(id)) {
            throw new ZooException(HttpStatus.NOT_FOUND, "Kangaroo with id " + id + " not found");
        }

        kangaroo.setId(id);
        kangaroos.put(id, kangaroo);

        return kangaroo;
    }

    @DeleteMapping("/{id}")
    public Kangaroo deleteKangaroo(@PathVariable("id") int id){
        if (!kangaroos.containsKey(id)) {
            throw new ZooException(HttpStatus.NOT_FOUND, "Kangaroo with id " + id + " not found");
        }
        Kangaroo removedKangaroo = kangaroos.get(id);
        kangaroos.remove(id);


        return removedKangaroo;
    }

}
