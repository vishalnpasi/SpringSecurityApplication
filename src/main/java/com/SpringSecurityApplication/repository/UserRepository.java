package com.SpringSecurityApplication.repository;

import com.SpringSecurityApplication.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserModel,String> {
    @Query("{username:?0}")
    Optional<UserModel> findByName(String username);
}
