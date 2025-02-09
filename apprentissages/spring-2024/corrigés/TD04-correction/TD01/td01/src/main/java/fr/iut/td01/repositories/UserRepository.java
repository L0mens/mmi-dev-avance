package fr.iut.td01.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import fr.iut.td01.models.UserData;

public interface UserRepository extends MongoRepository<UserData,String>{
    
}
