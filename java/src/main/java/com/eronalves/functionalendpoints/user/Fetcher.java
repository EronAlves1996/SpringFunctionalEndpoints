package com.eronalves.functionalendpoints.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Fetcher {

    @Autowired
    MongoRepo repository;

    public List<User> all(){
        return repository.findAll();
    }

    public User insert(User user) {
        return repository.save(user);
    }

    public User get(String id) {
        return findUser(id);
    }

    public void update(User received, String id) {
        User user = findUser(id);
        if(!received.id().equals(id)) throw new RuntimeException("The ID's are not equal");
        user.updateFrom(received);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    private User findUser(String id){
        Optional<User> possibleUser = repository.findById(id);
        if(possibleUser.isEmpty()) throw new RuntimeException("User not found");
        return possibleUser.get();
    }

}
