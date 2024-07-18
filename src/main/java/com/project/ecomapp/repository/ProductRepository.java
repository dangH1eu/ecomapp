package com.project.ecomapp.repository;

import com.project.ecomapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    Page<Product> findAll(Pageable pageable);


}
