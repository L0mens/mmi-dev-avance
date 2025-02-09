package fr.iut.td01.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import fr.iut.td01.models.PersonData;

/**
 * Repository of persons
 */
@Repository
public interface PersonRepository extends MongoRepository<PersonData,ObjectId>, PersonCustomRepository  {
    /**
     * Custom query to find persons by name
     * @param name the name of the person
     * @return the list of MongoDB readed in repository
     */
    @Query("{name:'?0'}")
    List<PersonData> findPersonByName(String name);

    /**
     * CUstom query to find persons by age
     * @param ageMin the min age 
     * @param ageMax the max age
     * @return all persons who's age between min & max
     */
    @Query("{age : {$gt:?0, $lt:?1}}")
    List<PersonData> findPersonByAge(int ageMin, int ageMax);
}
