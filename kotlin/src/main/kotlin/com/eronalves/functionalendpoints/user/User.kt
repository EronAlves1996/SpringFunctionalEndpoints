package com.eronalves.functionalendpoints.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(@Id val id: String?, var name: String, var age: Int){

    fun updateFrom(user: User){
        this.name = user.name
        this.age = user.age
    }

}

