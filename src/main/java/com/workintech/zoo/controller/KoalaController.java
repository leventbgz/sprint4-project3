package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {
    private Map<Integer, Koala> koalas;

    @PostConstruct
    public void init(){
        koalas = new HashMap<>();
    }

    @GetMapping
    public List<Koala> getAllKoalas(){
        return koalas.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Koala getKoalaById(@PathVariable("id") int id){
        if (koalas.containsKey(id)){
            return koalas.get(id);
        } else {
            throw new ZooException(HttpStatus.NOT_FOUND, "Koala with given id could not been found");
        }
    }

    @PostMapping
    public Koala addKoala(@RequestBody Koala koala) {
        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping("/{id}")
    public Koala updateKoala(@PathVariable("id") int id, @RequestBody Koala koala){
        if (!koalas.containsKey(id)) {
            throw new ZooException(HttpStatus.NOT_FOUND, "Koala with id " + id + " not found");
        }

        koala.setId(id);
        koalas.put(id, koala);

        return koala;
    }

    @DeleteMapping("/{id}")
    public Koala deleteKoala(@PathVariable("id") int id){
        if (!koalas.containsKey(id)) {
            throw new ZooException(HttpStatus.NOT_FOUND, "Koala with id " + id + " not found");
        }
        Koala removedKoala = koalas.get(id);
        koalas.remove(id);


        return removedKoala;
    }
}
