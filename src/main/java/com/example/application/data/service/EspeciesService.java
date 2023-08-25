package com.example.application.data.service;

import com.example.application.data.entity.Especies;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class EspeciesService {

    private final EspeciesRepository repository;

    public EspeciesService(EspeciesRepository repository) {
        this.repository = repository;
    }

    public Optional<Especies> get(Long id) {
        return repository.findById(id);
    }

    public Especies update(Especies entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Especies> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Especies> list(Pageable pageable, Specification<Especies> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
