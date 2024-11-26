package com.example.tickethub_producer.repository;

import com.example.tickethub_producer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUserIdAndPassword(Long id, String pw);

    boolean existsUserByUserIdAndPassword(Long id, String pw);
}
