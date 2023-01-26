package com.eronalves.functionalendpoints.user

import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepo: MongoRepository<User, String> {

}
