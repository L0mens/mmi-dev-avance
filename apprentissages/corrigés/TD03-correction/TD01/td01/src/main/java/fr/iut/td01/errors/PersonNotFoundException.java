package fr.iut.td01.errors;


public class PersonNotFoundException extends ObjectNotFoundException{
    public PersonNotFoundException(String id){
        super("Person with Id "+id+" not found.");
    }
}
