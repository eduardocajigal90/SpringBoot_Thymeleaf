package com.quiox.test.controller;

import com.quiox.test.entity.ExampleEntity;
import com.quiox.test.projection.ExampleEntityProjection;
import com.quiox.test.service.ExampleEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/example")
public class ExampleController {

    @Autowired
    private ExampleEntityService service;

    @GetMapping
    public List<ExampleEntityProjection> list() {
        return service.list();
    }

    @PostMapping
    public ExampleEntity create(@RequestBody ExampleEntity entity) {
         return service.create(entity);
    }

    @GetMapping("{id}")
    public ExampleEntityProjection details(@PathVariable UUID id) {
        return service.details(id);
    }

    @PutMapping("{id}")
    public ExampleEntity update(@PathVariable UUID id, @RequestBody ExampleEntity entity) {
            return  service.update(id, entity);
    }

    @DeleteMapping("{id}")
    public boolean delete(@PathVariable UUID id) {
            return service.delete(id);
    }


}
