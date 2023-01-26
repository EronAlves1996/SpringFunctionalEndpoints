package com.eronalves.functionalendpoints.user;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRepo extends MongoRepository<User, String> {
}
