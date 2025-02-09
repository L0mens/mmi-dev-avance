package fr.iut.td01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import fr.iut.td01.errors.PersonNotFoundException;
import fr.iut.td01.models.PersonData;
import fr.iut.td01.repositories.PersonRepository;
import fr.iut.td01.services.PersonService;

public class TestPersonService {
    @Test
    public void testAddPerson(){
        // create a mock repository, then the service
        PersonRepository repository = Mockito.mock(PersonRepository.class);
        PersonService service = new PersonService(repository);

        // test add a person
        PersonData result = service.add("toto",15,null);
        assertEquals("toto", result.getName());
        assertEquals(15, result.getAge());
        assertNull(result.getPhoto());
        ObjectId id = new ObjectId(result.getId());
        assertEquals(id.toHexString(), result.getId());

        // test if repository method insert has been called with the right argument
        ArgumentCaptor<PersonData> argumentCaptor = ArgumentCaptor.forClass(PersonData.class);
        Mockito.verify(repository, Mockito.times(1)).insert(argumentCaptor.capture());
        PersonData resultPersonData = argumentCaptor.getValue();
        assertSame(resultPersonData, result);
    }

    @Test
    public void testFindById(){
        // create a mock repository, then the service
        PersonRepository repository = Mockito.mock(PersonRepository.class);
        PersonService service = new PersonService(repository);

        // create some mock data
        PersonData personData1 = Mockito.mock(PersonData.class);
        PersonData personData2 = Mockito.mock(PersonData.class);

        ObjectId id1 = ObjectId.get();
        ObjectId id2 = ObjectId.get();
        Mockito.when(personData1.getId()).thenReturn(id1.toHexString());
        Mockito.when(personData2.getId()).thenReturn(id2.toHexString());

        // tell the mock repository what to do if operation is called 
        Mockito.when(repository.findById(any())).thenAnswer(invocation->{
            Optional<PersonData> result = Optional.empty();
            ObjectId id = (ObjectId)invocation.getArguments()[0];
            if(id.equals(id1))
                result = Optional.of(personData1);
            else if(id.equals(id2))
                result = Optional.of(personData2);                        
            return result;
        });

        // test if an existing person is founded
        PersonData test1 = service.findById(id1.toHexString());
        assertEquals(personData1, test1);

        // test if a non-existing person is not found & exception thrown.
        ObjectId id3 = new ObjectId();
        assertThrows(PersonNotFoundException.class, ()->{
            service.findById(id3.toHexString());
        });
    }

    @Test
    public void testFindByName(){

        // create a mock repository, then a service
        PersonRepository repository = Mockito.mock(PersonRepository.class);
        PersonService service = new PersonService(repository);

        // create mocks datas
        PersonData personData1 = Mockito.mock(PersonData.class);
        PersonData personData2 = Mockito.mock(PersonData.class); 
        PersonData personData3 = Mockito.mock(PersonData.class);               
        Mockito.when(personData1.getName()).thenReturn("p1");
        Mockito.when(personData2.getName()).thenReturn("p2");
        Mockito.when(personData3.getName()).thenReturn("p1");

        // tells what to do when findPersonByName is called 
        Mockito.when(repository.findPersonByName(any())).thenAnswer(invocation->{
            List<PersonData> result = new ArrayList<PersonData>();
            String name = (String)invocation.getArguments()[0];
            if(name.equals("p1"))
            {
                result.add(personData1);
                result.add(personData3);
            }
            else if(name.equals("p2"))
                result.add(personData2);
            return result;
        });

        // test 1 : with one person
        List<PersonData> test = service.findByName("p2");
        assertEquals(1, test.size());
        assertEquals(personData2, test.get(0));

        // test 2 : with many persons
        test = service.findByName(("p1"));
        assertEquals(2, test.size());        
        assertEquals(personData1, test.get(0));
        assertEquals(personData3, test.get(1));

        // test 3: with no person
        test = service.findByName("toto");
        assertEquals(0, test.size());
    }

    @Test
    public void testListAll(){
        // create mock repository and service
        PersonRepository repository = Mockito.mock(PersonRepository.class);
        PersonService service = new PersonService(repository);

        // create some mock datas
        PersonData personData1 = Mockito.mock(PersonData.class);
        PersonData personData2 = Mockito.mock(PersonData.class); 
        PersonData personData3 = Mockito.mock(PersonData.class);               
        Mockito.when(personData1.getName()).thenReturn("p1");
        Mockito.when(personData2.getName()).thenReturn("p2");
        Mockito.when(personData3.getName()).thenReturn("p1");
        List<PersonData> result = new ArrayList<>();
        result.add(personData1);
        result.add(personData2);
        result.add(personData3);

        // mock the findAll function
        Mockito.when(repository.findAll()).thenReturn(result);

        List<PersonData> test = service.listAll();
        assertEquals(result, test);
    }

    @Test
    public void testDelete(){
        PersonRepository repository = Mockito.mock(PersonRepository.class);
        PersonService service = new PersonService(repository);
        ObjectId id = ObjectId.get();

        service.delete(id.toHexString());

        ArgumentCaptor<ObjectId> captor = ArgumentCaptor.forClass(ObjectId.class);
        Mockito.verify(repository, Mockito.times(1)).deleteById(captor.capture());
        ObjectId test = captor.getValue();

        assertEquals(id, test);
    }

    @Test
    public void testDeleteAll(){
        PersonRepository repository = Mockito.mock(PersonRepository.class);
        PersonService service = new PersonService(repository);
        
        service.deleteAll();
        
        Mockito.verify(repository, Mockito.times(1)).deleteAll();        
    }

    @Test
    public void findByAge(){
        PersonRepository repository = Mockito.mock(PersonRepository.class);
        PersonService service = new PersonService(repository);

        PersonData personData1 = Mockito.mock(PersonData.class);
        PersonData personData2 = Mockito.mock(PersonData.class); 
        PersonData personData3 = Mockito.mock(PersonData.class); 

        Mockito.when(personData1.getAge()).thenReturn(10);
        Mockito.when(personData2.getAge()).thenReturn(15);
        Mockito.when(personData3.getAge()).thenReturn(20);



        
        Mockito.when(repository.findPersonByAge(anyInt(),anyInt())).thenAnswer(invocation->{
            int min = (int)invocation.getArguments()[0];
            int max = (int)invocation.getArguments()[1];
            List<PersonData> result = new ArrayList<>();
            if(min<=max && min<=10 && max>=20){
                result.add(personData1);
                result.add(personData2);
                result.add(personData3);
            }
            else if(min>15 && min<=max && min<=20){
                result.add(personData3);
            }
            
            return result;
        });

        // test 1 : no persons too young
        List<PersonData> test = service.findByAge(0, 9);
        assertEquals(0, test.size());

        // test 2 : all persons
        test = service.findByAge(0, 100);
        assertEquals(3, test.size());

        // test 3 : one person only
        test = service.findByAge(16, 30);
        assertEquals(1, test.size());
        assertEquals(personData3, test.get(0));
    }

    @Test
    public void testUpdateAgeAll(){
        PersonRepository repository = Mockito.mock(PersonRepository.class);
        PersonService service = new PersonService(repository);
        
        service.updateAgeAll();
        
        Mockito.verify(repository, Mockito.times(1)).updateAgeAllPersons();;    
    }
}
