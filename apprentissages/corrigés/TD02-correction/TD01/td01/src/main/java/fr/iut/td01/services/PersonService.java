package fr.iut.td01.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import fr.iut.td01.models.PersonData;
import fr.iut.td01.repositories.PersonRepository;

/**
 * Service to access to person's repository
 */
@Service
public class PersonService {
    private PersonRepository repository;

    public PersonService(PersonRepository repository){
        this.repository = repository;
    }

    /**
     * Add a new person in the repository
     * @param name the person's name
     * @param age the person's age
     * @param photo the person's photo
     * @return the person created
     */
    public PersonData add(String name, int age, String photo){
        PersonData pdata = new PersonData(name,age,photo);
        repository.insert(pdata);
        return pdata;
    }

    /**
     * List all the persons of the repository
     * @return list of all persons stored
     */
    public List<PersonData> listAll(){
        return repository.findAll();
    }

    /**
     * FInd a person with its id
     * @param idString the hex-string of the id
     * @return (optional) the person founded
     */
    public Optional<PersonData> findById(String idString){
        ObjectId id = new ObjectId(idString);
        return repository.findById(id);
    }

    /**
     * Remove a person from the repository
     * @param idString the hex-string of the id
     */
    public void delete(String idString){
        repository.deleteById(new ObjectId(idString));
    }

    /**
     * CLear the repository, delete all the persons
     */
    public void deleteAll(){
        repository.deleteAll();
    }
    /**
     * FInd persons with name
     * @param name the name to find
     * @return list of the persons who have that name
     */
    public List<PersonData> findByName(String name){
        var ret = repository.findPersonByName(name);
        return ret;
    }

    /**
     * FInd persons between 2 ages
     * @param ageMin the min age
     * @param ageMax the max age
     * @return all persons between min & max
     */
    public List<PersonData> findByAge(int ageMin, int ageMax){
        return repository.findPersonByAge(ageMin, ageMax);
    }

    /**
     * Update ages of all persons
     */
    public void updateAgeAll(){
        repository.updateAgeAllPersons();
    }
}
