package fr.iut.td01.repositories;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mongodb.client.result.UpdateResult;

import fr.iut.td01.models.PersonData;

/**
 * Implementation of the custom person mongodb repository
 */
@Component
public class PersonCustomRepositoryImpl implements PersonCustomRepository{
    private final MongoTemplate mongoTemplate;

    public PersonCustomRepositoryImpl(MongoTemplate template){
        this.mongoTemplate = template;
    }

    @Override
    public void updateAgeAllPersons() {
        Query query = new Query();
        Update update = new Update();
        update.inc("age",1);
        UpdateResult result = mongoTemplate.updateMulti(query, update, PersonData.class);
        if(result==null)
            throw new RuntimeException("No persons to update");
    }
    
}
