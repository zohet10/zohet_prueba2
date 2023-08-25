package com.example.application.data.service;

import com.example.application.data.entity.Especies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EspeciesRepository extends JpaRepository<Especies, Long>, JpaSpecificationExecutor<Especies> {

}
