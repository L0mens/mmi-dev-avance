package fr.iut.td01.models;

/**
 * Simple PODS to store a person
 */
public class Person  {
    private String name;
    private int age; 
    private String photo;

    /**
     * Init the person
     * @param name the person's name
     * @param age the person's age
     * @param photo the person's photo filename
     */
    public Person(String name, int age, String photo){
        this.name=name;
        this.age=age;        
        this.photo = photo;
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

    /**
     * 
     * @return the person's photo (name of the file)
     */
    public String getPhoto(){
        return photo;
    }
}
