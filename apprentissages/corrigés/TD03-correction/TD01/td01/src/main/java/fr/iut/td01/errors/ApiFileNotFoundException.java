package fr.iut.td01.errors;

public class ApiFileNotFoundException extends ObjectNotFoundException{
    public ApiFileNotFoundException(String file){
        super("File "+file+" not found or not readable.");
    }
}
