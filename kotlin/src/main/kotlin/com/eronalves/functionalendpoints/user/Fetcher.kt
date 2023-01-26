package com.eronalves.functionalendpoints.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class Fetcher(private val repository: UserRepo) {
    fun all(): List<User> {
        return repository.findAll()
    }

    fun insert(user: User): User {
        return repository.save(user)
    }

    fun getById(id: String): User {
        return findUser(id)
    }

    @Transactional
    fun update(user: User, id: String) {
        if(user.id != id) throw RuntimeException("The ID's are not equal")
        val userFinded = findUser(id)
        userFinded.updateFrom(user)
    }

    fun delete(id: String) {
        repository.deleteById(id)
    }

    private fun findUser(id: String): User {
        val possibleUser = repository.findById(id)
        if (possibleUser.isEmpty) throw RuntimeException("User not found")
        return possibleUser.get()
    }

}
