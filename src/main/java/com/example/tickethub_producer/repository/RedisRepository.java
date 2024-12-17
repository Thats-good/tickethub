package com.example.tickethub_producer.repository;

import com.example.tickethub_producer.entity.RedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<RedisEntity, String> {}
