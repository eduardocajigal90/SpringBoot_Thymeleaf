package com.quiox.test.service;

import com.quiox.test.entity.ExampleEntity;
import com.quiox.test.projection.ExampleEntityProjection;
import com.quiox.test.repository.ExampleEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExampleEntityService {

    @Autowired
    private ExampleEntityRepository repository;

    public List<ExampleEntityProjection> list() {
        return this.repository.findByActiveIsTrue();
    }

    public ExampleEntityProjection details(UUID id) {
        return this.repository.findDetailsById(id).orElse(null);
    }

    public ExampleEntity create(ExampleEntity entity) {
        return this.repository.save(entity);
    }

    public ExampleEntity update(UUID id, ExampleEntity entity) {
        entity.setId(id);
        return this.repository.save(entity);
    }

    public boolean delete(UUID id) {
        try {
            this.repository.deleteById(id);
        }catch (Exception  e) {
            return false;
        }
        return true;
    }



}
