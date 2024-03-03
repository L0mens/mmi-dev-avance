package fr.iut.td01.controllers;

import org.springframework.web.bind.annotation.RestController;

import fr.iut.td01.models.Person;
import fr.iut.td01.models.PersonData;
import fr.iut.td01.services.FileService;
import fr.iut.td01.services.PersonService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Rest controller of Persons API
 */
@RestController
public class PersonController {
    private final PersonService personService;
    private final FileService fileService;

    public PersonController(PersonService personService, FileService fileService){
        this.personService = personService;
        this.fileService = fileService;
    }

    /**
     * Post request, add a person
     * @param person person to add (passed in body)
     * @return the id of the person added, in hex-string
     */
   @PostMapping("/person/add")
   public String postMethodName(@RequestBody Person person) {
      PersonData p = personService.add(person.getName(), person.getAge(), person.getPhoto());
      return p.getId();
   }

   /**
    * Get request, list all persons
    * @return list of all persons in database
    */
   @GetMapping("/person/list")
   public List<PersonData> listAll() {
       return personService.listAll();
   }
   
   /**
    * Get request, find a person with its id
    * @param id the hex-string of the id
    * @return the person founded
    */
   @GetMapping("/person/findById")
   public PersonData findById(@RequestParam String id){
        var ret = personService.findById(id);
        return ret.get();
   }

   /**
    * Delete request, remove a person 
    * @param id the hex-string of the id
    */
    @DeleteMapping("/person/delete")
    public void delete(@RequestParam String id) {
        personService.delete(id);
    }

    /**
     * Delete request, remove all persons
     */
    @DeleteMapping("/person/deleteAll")
    public void deleteAll(){
        personService.deleteAll();
    }

    /**
     * Get request, find persons by name
     * @param name the name to find
     * @return list of persons who have that name
     */
    @GetMapping("/person/findByName")
    public List<PersonData> findByName(@RequestParam String name){
        return personService.findByName(name);
    }
    
    /**
     * Get request, find persons by age
     * @param ageMin the min age to find
     * @param ageMax to max age to find
     * @return list of persons with age between min & max
     */
    @GetMapping("/person/findByAge")
    public List<PersonData> findByAge(@RequestParam int ageMin, @RequestParam int ageMax){
        return personService.findByAge(ageMin, ageMax);
    }

    /**
     * Patch request, Add one year to all persons's age 
     */
    @PatchMapping("/person/updateAges")
    public void updateAges() {
        personService.updateAgeAll();
    }
    
    /**
     * Get request, get a photo with an person's id
     * @param id the id of the persons
     * @return the image of the photo, in JPEG
     * @throws FileNotFoundException if file not found
     * @throws IOException if file can not be readen
     */
    @GetMapping(value="/person/photo", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getMethodName(@RequestParam String id) throws FileNotFoundException, IOException {
        var person = personService.findById(id);
        if(!person.isPresent()){
            throw new RuntimeException("no person with this id");
        }
        PersonData p = person.get();        
        return fileService.readFromPath(p.getPhoto());
    }
    
}
