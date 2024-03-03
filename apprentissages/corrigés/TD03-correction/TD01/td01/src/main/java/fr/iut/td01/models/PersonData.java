package fr.iut.td01.models;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * MongoDB class for person
 */
@Document("Person")
@TypeAlias("PersonData")
public class PersonData extends Person
{ 
    @Id
    private ObjectId id;

    public PersonData(){
        super("",0,"");
    }
    /**
     * Create a person
     * @param name the name of the person
     * @param age the age of the person
     * @param photo the photo of the person
     */ 
    public PersonData(String name, int age, String photo)
    {
        super(name,age,photo);
        id = ObjectId.get();
    }

    /**
     * @return the person's id
     */
    public String getId(){return this.id.toHexString();}

}