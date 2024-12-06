package com.example.tickethub_producer.repository;

import com.example.tickethub_producer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByIdentifierAndPassword(String id, String pw);
    
    Optional<User> findByEmail(String email);
    boolean existsUserByIdentifierOrEmail(String identifier, String email   );
}
