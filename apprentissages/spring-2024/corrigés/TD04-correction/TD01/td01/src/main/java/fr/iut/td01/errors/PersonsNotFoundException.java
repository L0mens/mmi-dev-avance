package fr.iut.td01.errors;

public class PersonsNotFoundException extends ObjectNotFoundException{
    
    public PersonsNotFoundException()
    {
        super("No persons in database");
    }
}
