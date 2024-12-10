package com.nqbao.project.repository;

import com.nqbao.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findByUsername(String username);

    @Query(value = "select * from user where email=:email ", nativeQuery = true)
    Optional<User> getUserByEmail(@Param("email") String email);
}
