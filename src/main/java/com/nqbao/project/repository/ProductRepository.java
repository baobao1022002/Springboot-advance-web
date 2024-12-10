package com.nqbao.project.repository;

import com.nqbao.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select quantity from product where id=:id", nativeQuery = true)
    public int getQuantityByProductId(@Param("id") int id);

    @Query(value = "select category_id from product where id=:id", nativeQuery = true)
    public int getCategoryIdByProductId(@Param("id") int id);

}
