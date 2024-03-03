package fr.iut.td01.models;

/**
 * Simple PODS to store a person
 */
public class Person {
    private String name;
    private int age;

    /**
     * Init the person
     * @param name the person's name
     * @param age the person'a age
     */
    public Person(String name, int age){
        this.name=name;
        this.age=age;        
    }

    /**    
     * @return the person's name
     */
    public String getName(){
        return name;
    }

    /**     
     * @return the person's age
     */
    public int getAge(){
        return age;
    }
}
